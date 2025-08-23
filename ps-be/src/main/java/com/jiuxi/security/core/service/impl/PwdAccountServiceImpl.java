package com.jiuxi.security.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.jiuxi.common.util.CommonDateUtil;
import com.jiuxi.common.util.SmUtils;
import com.jiuxi.core.bean.TopinfoRuntimeException;
import com.jiuxi.security.autoconfig.SecurityConfigurationProperties;
import com.jiuxi.security.core.entity.vo.AccountExinfoVO;
import com.jiuxi.security.core.entity.vo.AccountVO;
import com.jiuxi.security.core.event.LoginApplicationEvent;
import com.jiuxi.security.core.listener.LoginApplicationEventCollection;
import com.jiuxi.security.core.listener.LoginApplicationService;
import com.jiuxi.security.core.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Description: 账号/密码登陆实现类
 * @ClassName: PersonServiceImpl
 * @Author: pand
 * @Date: 2020-08-28 13:46
 * @Copyright: 2020 www.tuxun.net Inc. All rights reserved.
 */
public class PwdAccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PwdAccountServiceImpl.class);

    @Autowired(required = false)
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected SecurityConfigurationProperties properties;

    @Autowired
    private LoginApplicationEventCollection loginApplicationEventCollection;

    @Autowired
    private LoginApplicationService accountExinfoDBService;

    @PostConstruct
    public void init() {
        loginApplicationEventCollection.addEvent(accountExinfoDBService);
    }

    /**
     * 上下文对象
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 是否冻结， 1 冻结，0 未冻结
     */
    protected final String LOCKED = "1";

    private static final String loginSql = "SELECT account_id, tenant_id, person_id, userpwd, locked, EXTEND01 as restPwd, expired_time FROM tp_account WHERE username = ?  AND enabled = '1' AND actived = '1' LIMIT 1";

    private static final String personSql = "select CATEGORY from tp_person_basicinfo where PERSON_ID = ?";

    /**
     * 查询账号最后登陆时间和登陆错误次数
     */
    private static final String accountExinfoSql = "select account_id, err_count, last_err_time, last_login_time from tp_account_exinfo where account_id = ?";


    /**
     * 用户名密码登陆，根据用户名查询用户/账号是否存在，存在继续校验账号是否可用
     *
     * @param vo: PersonVO的实体对象
     * @return com.jiuxi.security.core.entity.vo.SmsPersonVO
     * @author pand
     * @date 2020-08-27 14:48
     */
    @Override
    public AccountVO queryAccount(AccountVO vo) {

        String userName = vo.getUserName();
        String passWord = vo.getUserpwd();
        if (jdbcTemplate == null) {
            throw new RuntimeException("在执行认证时，jdbcTemplate为null，请先在项目中引入了连接池的配置...");
        }

        // BeanPropertyRowMapper 字段转换时，注意数据库字段与实体属性的对应
        List<AccountVO> list = jdbcTemplate.query(loginSql, new Object[]{userName}, new BeanPropertyRowMapper<>(AccountVO.class));
        if (list != null && list.size() != 1) {
            LOGGER.error("登录失败，请检查用户名是否正确或用户是否有效，当前登录用户名:{}", userName);
            throw new TopinfoRuntimeException(-1, "登录失败，用户名或密码错误");
        }

        AccountVO accountVO = list.get(0);
        // 校验账号是否过期
        this.checkExpiredTime(accountVO);

        // 校验是否冻结
        if (LOCKED.equals(accountVO.getLocked())) {
            AccountExinfoVO accountExinfoVO = jdbcTemplate.queryForObject(accountExinfoSql, new BeanPropertyRowMapper<>(AccountExinfoVO.class), accountVO.getAccountId());
            if (null == accountExinfoVO) {
                LOGGER.error("登录失败，用户被冻结，当前登录用户名:{}", userName);
                throw new TopinfoRuntimeException(-1, "登录失败，用户被冻结");
            }

            // 1。半个小时以内再次登陆，直接抛账号被冻结异常
            int errCount = accountExinfoVO.getErrCount();
            String lastErrTimeStr = accountExinfoVO.getLastErrTime();
            LocalDateTime lastErrTime = LocalDateTime.parse(lastErrTimeStr, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            // 1.1 解锁时间 = 最后一次登陆错误时间 + 30分钟
            LocalDateTime delockingTime = lastErrTime.plusMinutes(properties.getAuthentication().getDeblocking());
            LocalDateTime now = LocalDateTime.now();
            if (delockingTime.isAfter(now)) {
                LOGGER.error("账号被冻结 {} 分钟内登录失败，当前登录用户名:{}", properties.getAuthentication().getDeblocking(), userName);
                Duration between = Duration.between(now, delockingTime);
                String timeStr = between.toMinutes() > 0 ? between.toMinutes() + "分钟" : between.toMillis() / 1000 + "秒";
                throw new TopinfoRuntimeException(-1, "登录信息错误次数超限，请" + timeStr + "后再试！");
            }

            // 2。半个小时外但是登陆错误次数大于30次，抛账号被冻结异常
            if (errCount > properties.getAuthentication().getMaxErrCount()) {
                LOGGER.error("尝试登陆错误次数超过 {} 次登录失败，当前登录用户名:{}", properties.getAuthentication().getMaxErrCount(), userName);
                throw new TopinfoRuntimeException(-1, "登录信息错误次数超限，该账号已被冻结，请联系管理员解冻！");
            }

            // 3。半个小时外且登陆错误次数小于30次，再次登陆
        }

        if (!SmUtils.match(passWord, accountVO.getUserpwd())) {
            // 密码不对，需要记录错误次数，达到五次，账号锁定
            if (null != loginApplicationEventCollection) {
                // 通过上下文对象发布监听
                applicationContext.publishEvent(new LoginApplicationEvent("登陆失败监听", loginApplicationEventCollection, false, accountVO.getAccountId()));
            }
            LOGGER.error("登录失败，密码错误，当前登录用户名:{}", userName);
            throw new TopinfoRuntimeException(-1, "登录失败，用户名或密码错误");
        }

        // 查询人员是政府人员还是企业人员的标示
        Integer category = jdbcTemplate.queryForObject(personSql, new Object[]{accountVO.getPersonId()}, Integer.class);
        if (null == category) {
            LOGGER.error("登录失败，用户信息有问题，当前登录用户名:{}", userName);
            throw new TopinfoRuntimeException(-1, "登录失败，用户信息查询失败");
        }
        accountVO.setCategory(category);

        if (null != loginApplicationEventCollection) {
            // 认证通过，清零失败次数
            applicationContext.publishEvent(new LoginApplicationEvent("登陆成功监听", loginApplicationEventCollection, true, accountVO.getAccountId()));
        }

        return accountVO;
    }

    /**
     * 校验账号是否过期, 过期则抛出异常
     *
     * @param accountVO
     * @return void
     * @author 杨占锐
     * @date 2025/2/13 11:18
     */
    private void checkExpiredTime(AccountVO accountVO) {
        String expiredTime = accountVO.getExpiredTime();
        if (StrUtil.isBlank(expiredTime)) {
            return;
        }
        String date = CommonDateUtil.date();
        if (date.compareTo(expiredTime) > 0) {
            LOGGER.error("登录失败，用户已过期:accountVO：{}", JSONObject.toJSONString(accountVO));
            throw new TopinfoRuntimeException(-1, "登录失败，用户信息已过期");
        }
    }
}

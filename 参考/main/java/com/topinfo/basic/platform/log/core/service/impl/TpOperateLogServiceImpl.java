package com.topinfo.basic.platform.log.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topinfo.basic.platform.captcha.core.service.CaptchaService;
import com.topinfo.basic.platform.common.exception.ExceptionUtils;
import com.topinfo.basic.platform.common.util.JwtTokenUtils;
import com.topinfo.basic.platform.common.util.SnowflakeIdUtil;
import com.topinfo.basic.platform.core.bean.TopinfoRuntimeException;
import com.topinfo.basic.platform.log.autoconfig.LoggerConfigurationProperties;
import com.topinfo.basic.platform.log.core.bean.entity.TpOperateLog;
import com.topinfo.basic.platform.log.core.bean.query.TpOperateLogQuery;
import com.topinfo.basic.platform.log.core.bean.vo.TpOperateLogVO;
import com.topinfo.basic.platform.log.core.constant.LoggerConstant;
import com.topinfo.basic.platform.log.core.dao.TpOperateLogDao;
import com.topinfo.basic.platform.log.core.service.TpOperateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: TpOperateLogServiceImpl
 * @Description: 操作日志表
 * @Author 杨攀
 * @Date 2022-09-21 14:00:19
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public class TpOperateLogServiceImpl implements TpOperateLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TpOperateLogServiceImpl.class);

    @Autowired
    private LoggerConfigurationProperties properties;

    @Autowired
    private TpOperateLogDao tpOperateLogDao;

    @Autowired
    private CaptchaService captchaService;

    
    private final String moduleLogin = "login";

    @Override
    public IPage<TpOperateLogVO> queryPage(TpOperateLogQuery query) {

        try {
            Integer pageNum = Optional.ofNullable(query.getCurrent()).orElse(1);
            Integer pageSize = Optional.ofNullable(query.getSize()).orElse(10);

            Page<TpOperateLogVO> page = new Page<>(pageNum, pageSize);
            IPage<TpOperateLogVO> iPage = tpOperateLogDao.getPage(page, query);

            for(TpOperateLogVO item : iPage.getRecords()){
                String mcode = properties.customizeModuleMap.get(item.getModuleCode());
                item.setModuleCode(mcode);
            }

            return iPage;
        } catch (Exception e) {
            LOGGER.error("列表信息查询失败！{}", ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "列表信息查询失败！");
        }
    }

    @Override
    public String add(TpOperateLogVO vo, String jwtpid) {

        // 生成主键
        String id = SnowflakeIdUtil.nextIdStr();
        String now = LocalDateTime.now().format(LoggerConstant.DateFormatter.yyyyMMddHHmmss);

        TpOperateLog bean = new TpOperateLog();
        // 转换成数据库对象
        BeanUtil.copyProperties(vo, bean);
        // 设置主键id
        bean.setLogId(id);
        // 创建人
        bean.setCreator(jwtpid);
        // 创建时间
        bean.setCreateTime(now);
        // 修改人
        bean.setUpdator(jwtpid);
        // 修改时间
        bean.setUpdateTime(now);
        // 是否有效
        bean.setActived(LoggerConstant.YES);
        // TODO

        try {
                tpOperateLogDao.add(bean);
            return id;
        } catch (Exception e) {
            LOGGER.error("新增失败！{}", ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "新增失败！");
        }
    }

    @Override
    public int update(TpOperateLogVO vo, String jwtpid) {

        TpOperateLog bean = new TpOperateLog();
        // 转换成数据库对象
        BeanUtil.copyProperties(vo, bean);
        bean.setUpdator(jwtpid);
        bean.setUpdateTime(LocalDateTime.now().format(LoggerConstant.DateFormatter.yyyyMMddHHmmss));
        // TODO

        try {
            int count = 0; //tpOperateLogDao.update(bean);
            return count;
        } catch (Exception e) {
            LOGGER.error("修改失败！{}", ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "修改失败！");
        }
    }

    @Override
    public TpOperateLogVO view(String id) {

        try {
                TpOperateLogVO vo = tpOperateLogDao.view(id);
            return vo;
        } catch (Exception e) {
            LOGGER.error("查看失败！{}", ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "查看失败！");
        }
    }

    @Override
    // @Transactional(rollbackFor = TopinfoRuntimeException.class)
    public int deleteByIds(List<String> ids, String jwtpid) {
        String now = LocalDateTime.now().format(LoggerConstant.DateFormatter.yyyyMMddHHmmss);

        try {
            if (null == ids || ids.isEmpty()) {
                throw new TopinfoRuntimeException(-1, "删除数据id不能为空！");
            }
            // TODO 删除也可以使用sql批量操作
            ids.forEach(id -> {
                    // tpOperateLogDao.delete(id, now, jwtpid);
            });
            return ids.size();
        } catch (Exception e) {
            LOGGER.error("删除失败！{}", ExceptionUtils.getStackTrace(e));
            throw new TopinfoRuntimeException(-1, "删除失败！");
        }
    }




    /**
     * 登录日志采集
     * @author 杨攀
     * @date 2022/9/21 15:48
     * @param ticket 滑动验证票据
     * @param username 用户名
     * @param category 用户类别
     * @param token
     * @param ip
     * @param userAgent
     * @param extend01
     * @param extend02
     * @param extend03
     * @return void
     */
    @Override
    public void login(String ticket, String username, String category, String token, String ip, String userAgent, String extend01, String extend02, String extend03) {

        // 校验滑动窗口，防止恶意刷登录次数。
        boolean bool = captchaService.checkTicket(ticket);

        if(!bool){
            return;
        }

        // 生成主键
        String id = SnowflakeIdUtil.nextIdStr();
        String now = LocalDateTime.now().format(LoggerConstant.DateFormatter.yyyyMMddHHmmss);

        String pid = JwtTokenUtils.getPersonId(token);
        String ascnId = JwtTokenUtils.getAscnId(token);
        String cityCode = JwtTokenUtils.getCityCode(token);

        TpOperateLog bean = new TpOperateLog();
        // 设置主键id
        bean.setLogId(id);
        // 操作人员id
        bean.setPersonId(pid);
        // 模块code
        bean.setModuleCode(moduleLogin);
        // 操作类型
        bean.setOperterType(moduleLogin);
        // 操作时间
        bean.setOperterTime(now);
        // 操作记录ID、修改、删除时，记录ID
        bean.setOperterRid("");
        // 操作人IP
        bean.setOperterIp(ip);
        // 单位ID
        bean.setAscnId(ascnId);
        // 操作人浏览器
        bean.setOperterBrowser(userAgent);
        // 人员类别
        bean.setCategory(Integer.parseInt(category));
        // 账号
        bean.setUsername(username);
        // 行政区划
        bean.setCityCode(cityCode);
        // 创建人
        bean.setCreator(pid);
        // 创建时间
        bean.setCreateTime(now);
        // 修改人
        bean.setUpdator(pid);
        // 修改时间
        bean.setUpdateTime(now);
        // 是否有效
        bean.setActived(LoggerConstant.YES);
        bean.setExtend01(extend01);
        bean.setExtend02(extend02);
        bean.setExtend03(extend03);

        // 新增
        tpOperateLogDao.add(bean);
    }

    /**
     * 操作日志采集
     * @author 杨攀
     * @date 2022/9/21 17:24
     * @param moduleCode 模块code
     * @param operterType 操作类型
     * @param operterRid 操作记录ID、修改、删除时，记录ID
     * @param username 账号
     * @param category 人员类别
     * @param token
     * @param extend01
     * @param extend02
     * @param extend03
     * @return void
     */
    @Override
    public void collection(String moduleCode, String operterType, String operterRid, String username, String category, String token, String ip, String userAgent, String extend01, String extend02, String extend03) {

        // 判断是否在配置的模块中， 避免别人攻击的垃圾数据干扰
        if(!properties.customizeModuleMap.containsKey(moduleCode)){
            return;
        }

        // 生成主键
        String id = SnowflakeIdUtil.nextIdStr();
        String now = LocalDateTime.now().format(LoggerConstant.DateFormatter.yyyyMMddHHmmss);

        String pid = JwtTokenUtils.getPersonId(token);
        String ascnId = JwtTokenUtils.getAscnId(token);
        String cityCode = JwtTokenUtils.getCityCode(token);

        TpOperateLog bean = new TpOperateLog();
        // 设置主键id
        bean.setLogId(id);
        // 操作人员id
        bean.setPersonId(pid);
        // 模块code
        bean.setModuleCode(moduleCode);
        // 操作类型
        bean.setOperterType(operterType);
        // 操作时间
        bean.setOperterTime(now);
        // 操作记录ID、修改、删除时，记录ID
        bean.setOperterRid(operterRid);
        // 操作人IP
        bean.setOperterIp(ip);
        // 单位ID
        bean.setAscnId(ascnId);
        // 操作人浏览器
        bean.setOperterBrowser(userAgent);
        // 人员类别
        bean.setCategory(Integer.parseInt(category));
        // 账号
        bean.setUsername(username);
        // 行政区划
        bean.setCityCode(cityCode);
        // 创建人
        bean.setCreator(pid);
        // 创建时间
        bean.setCreateTime(now);
        // 修改人
        bean.setUpdator(pid);
        // 修改时间
        bean.setUpdateTime(now);
        // 是否有效
        bean.setActived(LoggerConstant.YES);
        bean.setExtend01(extend01);
        bean.setExtend02(extend02);
        bean.setExtend03(extend03);
        // 新增
        tpOperateLogDao.add(bean);
    }

}
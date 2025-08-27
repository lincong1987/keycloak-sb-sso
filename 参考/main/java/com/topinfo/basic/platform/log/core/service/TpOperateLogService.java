package com.topinfo.basic.platform.log.core.service;


import com.topinfo.basic.platform.log.core.bean.query.TpOperateLogQuery;
import com.topinfo.basic.platform.log.core.bean.entity.TpOperateLog;
import com.topinfo.basic.platform.log.core.bean.vo.TpOperateLogVO;

import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @ClassName: TpOperateLogService
 * @Description: 操作日志表
 * @Author 杨攀
 * @Date 2022-09-21 14:00:19
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public interface TpOperateLogService {

    IPage<TpOperateLogVO>  queryPage(TpOperateLogQuery query);

    String add(TpOperateLogVO vo, String jwtpid);

    int update(TpOperateLogVO vo, String jwtpid);

    TpOperateLogVO view(String id);

    int deleteByIds(List<String> ids, String jwtpid);

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
    void login(String ticket, String username, String category, String token, String ip, String userAgent,String extend01, String extend02, String extend03);

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
    void collection(String moduleCode, String operterType, String operterRid, String username, String category, String token, String ip, String userAgent,String extend01, String extend02, String extend03);
}


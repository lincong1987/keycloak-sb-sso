package com.topinfo.basic.platform.plugin.monitor.server.core.service;


import com.topinfo.basic.platform.plugin.monitor.server.core.bean.vo.TpMonitorConfigVO;

/**
 * @ClassName: TpMonitorConfigService
 * @Description: 监控配置
 * @Author yangzr
 * @Date 2024-11-18 16:30:42
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public interface TpMonitorConfigService {

    /**
     * 修改
     *
     * @Author yangzr
     * @Date 2024-11-18 16:30:42
     */
    int update(TpMonitorConfigVO vo, String jwtpid);

    /**
     * 查询唯一一条的配置信息
     *
     * @return com.topinfo.basic.platform.plugin.monitor.server.core.bean.vo.TpMonitorConfigVO
     * @author 杨占锐
     * @date 2024/11/19 10:29
     */
    TpMonitorConfigVO getOne();


}


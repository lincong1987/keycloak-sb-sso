package com.topinfo.basic.platform.plugin.monitor.server.core.schedule;

import com.topinfo.basic.platform.common.exception.ExceptionUtils;
import com.topinfo.basic.platform.common.util.CommonDateUtil;
import com.topinfo.basic.platform.plugin.monitor.server.bean.ClientHeartbeatInfo;
import com.topinfo.basic.platform.plugin.monitor.server.constant.MonitorServerConstant;
import com.topinfo.basic.platform.plugin.monitor.server.core.bean.vo.TpMonitorClientVO;
import com.topinfo.basic.platform.plugin.monitor.server.core.bean.vo.TpMonitorConfigVO;
import com.topinfo.basic.platform.plugin.monitor.server.core.service.MonitorCacheService;
import com.topinfo.basic.platform.plugin.monitor.server.core.service.MonitorServerService;
import com.topinfo.basic.platform.plugin.monitor.server.core.service.TpMonitorClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName: MonitorServerSchedule
 * @Description:
 * @Author 杨占锐
 * @Date 2024/11/19 14:38
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@Component
public class MonitorServerSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorServerSchedule.class);

    @Autowired
    private MonitorCacheService monitorCacheService;
    @Autowired
    private TpMonitorClientService tpMonitorClientService;
    @Autowired
    private MonitorServerService monitorServerService;

    /**
     * 定时发送系统离线消息
     *
     * <pre>
     *     1. 查询阈值配置，为空则结束
     *     2. 查询所有客户端
     *     3. 计算是否离线，离线则发送邮件
     * </pre>
     * @return void
     * @author 杨占锐
     * @date 2024/11/19 14:39
     */
    @Scheduled(initialDelay = 5 * 60 * 1000, fixedDelay = 5 * 60 * 1000)
    public void offline() {
        try {

            // 1. 查询阈值配置，为空则结束
            TpMonitorConfigVO configVO = monitorCacheService.getConfig();
            if (null == configVO){
                LOGGER.info("未配置阈值信息，定时任务不再处理");
                return;
            }

            LOGGER.info("定时发送系统离线消息开始扫描... 频率5分钟一次");

            // 2. 查询所有客户端
            List<TpMonitorClientVO> clientVOS = tpMonitorClientService.listAll();
            for (TpMonitorClientVO clientVO: clientVOS) {
                // 3. 计算是否离线，离线则发送邮件
                this.computeOnOffLine(clientVO, configVO);
            }

        } catch (Exception e) {
            LOGGER.error("定时发送系统离线消息失败，错误：{}", ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 计算是否离线
     *
     * <per>
     *     1. 查询缓存中心跳信息
     *     2. 心跳信息与服务器时间相差5分钟则判定为离线
     *     3. 离线则发送邮件
     * </per>
     * @param clientVO
     * @param configVO
     * @return void
     * @author 杨占锐
     * @date 2024/11/19 14:57
     */
    private void computeOnOffLine(TpMonitorClientVO clientVO, TpMonitorConfigVO configVO) {
        try {

            // 1. 查询缓存中心跳信息
            ClientHeartbeatInfo heartbeatInfo = monitorCacheService.getHeartbeat(clientVO.getClientId());
            if (null == heartbeatInfo) {
                // 为空时，不再处理，因为缓存时间是30天，为空可能是手工删除了缓存
                return;
            }

            // 2. 心跳信息与服务器时间相差5分钟则判定为离线
            LocalDateTime lastUpdateTime = CommonDateUtil.parse(heartbeatInfo.getLastUpdateTime());
            if (LocalDateTime.now().isAfter(lastUpdateTime.plusMinutes(MonitorServerConstant.OFFLINE_MINUTE))) {
                // 3. 离线则发送邮件
                monitorServerService.offlineAlarm(clientVO, configVO);
            }

        } catch (Exception e) {
            LOGGER.error("定时发送系统离线消息失败，错误：{}", ExceptionUtils.getStackTrace(e));
        }

    }
}

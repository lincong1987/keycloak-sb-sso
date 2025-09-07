package com.topinfo.basic.platform.plugin.monitor.client.core.service.impl.health;

import com.topinfo.basic.platform.common.exception.ExceptionUtils;
import com.topinfo.basic.platform.plugin.monitor.client.constant.MonitorClientConstant;
import com.topinfo.basic.platform.plugin.monitor.client.core.service.MonitorHealthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

/**
 * @ClassName: MqttHealthImpl
 * @Description: mqtt 健康检查
 * @Author 杨占锐
 * @Date 2024/11/20 17:11
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public class MqttHealthImpl implements MonitorHealthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttHealthImpl.class);

    @Autowired(required = false)
    private MqttPahoMessageDrivenChannelAdapter mqttPahoMessageDrivenChannelAdapter;

    @Override
    public String getServerName() {
        return MonitorClientConstant.MQTT;
    }

    /**
     * 判断redis是否健康
     *
     * @return boolean
     * @author 杨占锐
     * @date 2024/11/20 17:12
     */
    @Override
    public boolean isHealth() {

        if (null != mqttPahoMessageDrivenChannelAdapter) {
            try {
                mqttPahoMessageDrivenChannelAdapter.removeTopic(MonitorClientConstant.TOPINFO_HEALTH_MONITOR);
                return true;
            } catch (Exception e) {
                LOGGER.error("mqtt测试失败，错误：{}", ExceptionUtils.getStackTrace(e));
            }
        }

        return false;
    }
}

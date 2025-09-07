package com.topinfo.basic.platform.plugin.monitor.client.core.service.impl.health;

import com.topinfo.basic.platform.common.exception.ExceptionUtils;
import com.topinfo.basic.platform.common.util.CommonDateUtil;
import com.topinfo.basic.platform.plugin.monitor.client.constant.MonitorClientConstant;
import com.topinfo.basic.platform.plugin.monitor.client.core.service.MonitorHealthService;
import com.topinfo.basic.platform.plugin.mq.producer.TopinfoMQProducer;
import com.topinfo.basic.platform.plugin.mq.producer.bean.MqSendCallback;
import com.topinfo.basic.platform.plugin.mq.producer.bean.MqSendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName: MqHealthV2Impl
 * @Description: mq 健康检查 兼容第2版
 * @Author 杨占锐
 * @Date 2024/11/20 17:11
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public class MqHealthV2Impl implements MonitorHealthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqHealthV2Impl.class);

    @Autowired(required = false)
    private TopinfoMQProducer rocketMQProducer;

    @Override
    public String getServerName() {
        return MonitorClientConstant.MQ;
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
        // 默认false
        AtomicBoolean bool = new AtomicBoolean(false);
        if (null != rocketMQProducer) {
            CountDownLatch latch = new CountDownLatch(1);
            try {

                rocketMQProducer.sendAsyncMsg(MonitorClientConstant.TOPINFO_HEALTH_MONITOR, CommonDateUtil.now(), new MqSendCallback() {
                    @Override
                    public void onSuccess(MqSendResult mqSendResult) {
                        bool.set(true);
                        latch.countDown();
                    }

                    @Override
                    public void onException(Throwable e) {
                        latch.countDown();
                        LOGGER.error("mq V2测试发送数据失败，错误：{}", ExceptionUtils.getStackTrace(e));
                    }
                });

            } catch (Exception e) {
                latch.countDown();
                LOGGER.error("mq V2测试失败，错误：{}", ExceptionUtils.getStackTrace(e));
            }

            try {
                latch.await(MonitorClientConstant.WAIT_SECONDS, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOGGER.error("mq V2测试超时，错误：{}", ExceptionUtils.getStackTrace(e));
            }
        }

        return bool.get();
    }
}

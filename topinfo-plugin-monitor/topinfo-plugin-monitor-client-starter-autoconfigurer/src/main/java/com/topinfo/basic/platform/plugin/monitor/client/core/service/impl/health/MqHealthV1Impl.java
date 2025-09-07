package com.topinfo.basic.platform.plugin.monitor.client.core.service.impl.health;

import com.topinfo.basic.platform.common.exception.ExceptionUtils;
import com.topinfo.basic.platform.common.util.CommonDateUtil;
import com.topinfo.basic.platform.plugin.monitor.client.constant.MonitorClientConstant;
import com.topinfo.basic.platform.plugin.monitor.client.core.service.MonitorHealthService;
import com.topinfo.basic.platform.recketmq.core.mq.RocketMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ClassName: MqHealthV1Impl
 * @Description: mq 健康检查 兼容第1版
 * @Author 杨占锐
 * @Date 2024/11/20 17:11
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public class MqHealthV1Impl implements MonitorHealthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqHealthV1Impl.class);

    @Autowired(required = false)
    private RocketMQProducer rocketMQProducer;

    @Override
    public String getServerName() {
        return MonitorClientConstant.MQ;
    }

    /**
     * 判断 mq 是否健康
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

                rocketMQProducer.sendAsyncMsg(MonitorClientConstant.TOPINFO_HEALTH_MONITOR, CommonDateUtil.now(), new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        bool.set(true);
                        latch.countDown();
                    }

                    @Override
                    public void onException(Throwable e) {
                        latch.countDown();
                        LOGGER.error("mq V1测试发送数据失败，错误：{}", ExceptionUtils.getStackTrace(e));
                    }
                });

            } catch (Exception e) {
                latch.countDown();
                LOGGER.error("mq V1测试失败，错误：{}", ExceptionUtils.getStackTrace(e));
            }

            try {
                latch.await(MonitorClientConstant.WAIT_SECONDS, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOGGER.error("mq V1测试超时，错误：{}", ExceptionUtils.getStackTrace(e));
            }
        }

        return bool.get();
    }
}

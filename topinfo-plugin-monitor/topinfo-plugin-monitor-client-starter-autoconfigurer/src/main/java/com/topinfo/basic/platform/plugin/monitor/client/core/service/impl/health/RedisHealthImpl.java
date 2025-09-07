package com.topinfo.basic.platform.plugin.monitor.client.core.service.impl.health;

import com.topinfo.basic.platform.common.exception.ExceptionUtils;
import com.topinfo.basic.platform.plugin.monitor.client.constant.MonitorClientConstant;
import com.topinfo.basic.platform.plugin.monitor.client.core.service.MonitorHealthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @ClassName: RedisHealthImpl
 * @Description: redis 健康检查
 * @Author 杨占锐
 * @Date 2024/11/20 17:11
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public class RedisHealthImpl implements MonitorHealthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisHealthImpl.class);

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    @Override
    public String getServerName() {
        return MonitorClientConstant.REDIS;
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

        if (null != redisTemplate) {
            try {
                redisTemplate.hasKey(MonitorClientConstant.TEST_REDIS_KEY);
                return true;
            } catch (Exception e) {
                LOGGER.error("redis测试失败，错误：{}", ExceptionUtils.getStackTrace(e));
            }
        }
        return false;
    }
}

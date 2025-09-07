package com.topinfo.basic.platform.plugin.monitor.client.autoconfig;


import com.topinfo.basic.platform.plugin.monitor.client.core.service.impl.health.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName: MonitorClientAutoConfiguration
 * @Description: 监控客户端配置
 * @Author 杨占锐
 * @Date 2024/11/5 18:12
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@Configuration
@ComponentScan({"com.topinfo.basic.platform.plugin.monitor.client.core"})
@EnableConfigurationProperties({MonitorClientAutoConfigurationProperties.class})
@ConditionalOnProperty(prefix = "topinfo.platform.plugin.monitor", name = "server-url")
@EnableScheduling
public class MonitorClientAutoConfiguration {


    @ConditionalOnProperty(prefix = "topinfo.mybatis.datasourceConfig", name = "url")
    @Bean
    public DruidDataSourceHealthImpl druidDataSourceHealth(){
        return new DruidDataSourceHealthImpl();
    }

    @ConditionalOnProperty(prefix = "topinfo.mybatis.dynamic", name = "default-source")
    @Bean
    public DynamicDataSourceHealthImpl dynamicDataSourceHealth(){
        return new DynamicDataSourceHealthImpl();
    }

    @ConditionalOnClass(name = {"com.topinfo.basic.platform.recketmq.core.mq.RocketMQProducer"})
    @Bean
    public MqHealthV1Impl rocketMqHealthV1(){
        return new MqHealthV1Impl();
    }

    @ConditionalOnClass(name = {"com.topinfo.basic.platform.plugin.mq.producer.TopinfoMQProducer"})
    @Bean
    public MqHealthV2Impl rocketMqHealthV2(){
        return new MqHealthV2Impl();
    }

    @ConditionalOnProperty(prefix = "spring.redis.redisson", name = "config")
    @Bean
    public RedisHealthImpl redisHealth(){
        return new RedisHealthImpl();
    }

    @ConditionalOnProperty(prefix = "topinfo.mqtt", name = "host-url")
    @Bean
    public MqttHealthImpl mqttHealth(){
        return new MqttHealthImpl();
    }
}

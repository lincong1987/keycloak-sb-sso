package com.topinfo.basic.platform.plugin.monitor.server.autoconfig;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@ComponentScan(basePackages = {"com.topinfo.basic.platform.plugin.monitor.server.core"})
@MapperScan({"com.topinfo.basic.platform.plugin.monitor.server.core.mapper"})
@EnableConfigurationProperties({MonitorServerAutoConfigurationProperties.class})
@EnableScheduling
public class MonitorServerAutoConfiguration {


}

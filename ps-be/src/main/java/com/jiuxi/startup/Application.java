package com.jiuxi.startup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @ClassName: Application
 * @Description: PS BMP Backend Application 主启动类
 * @Author: System
 * @Date: 2025-08-18
 * @Copyright: 2025 www.tuxun.net Inc. All rights reserved.
 */
@SpringBootApplication(scanBasePackages = {
    "com.jiuxi",
    "com.jiuxi.module.user",
    "com.jiuxi.app.org",
    "com.jiuxi.domain.org",
    "com.jiuxi.infra.org",
    "com.jiuxi.intf.org",
    "com.jiuxi.app.auth",
    "com.jiuxi.domain.auth",
    "com.jiuxi.infra.auth",
    "com.jiuxi.intf.auth",
    "com.jiuxi.module.sys",
    "com.jiuxi.admin",
    "com.jiuxi.infra.user",
    "com.jiuxi.intf.file"  // 添加这一行以扫描新的文件控制器
})
@MapperScan(basePackages = {
    "com.jiuxi.admin.core.mapper",
    "com.jiuxi.module.user.infra.persistence.mapper",
    "com.jiuxi.infra.org.persistence.mapper",
    "com.jiuxi.infra.auth.persistence.mapper",
    "com.jiuxi.infra.sys.persistence.mapper",
    "com.jiuxi.infra.user.persistence.mapper"
})
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
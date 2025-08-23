package com.jiuxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

/**
 * @ClassName: Application
 * @Description: PS BMP Backend Application 主启动类
 * @Author: System
 * @Date: 2025-08-18
 * @Copyright: 2025 www.tuxun.net Inc. All rights reserved.
 */
@SpringBootApplication(scanBasePackages = {"com.jiuxi", "com.jiuxi.security.sso"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

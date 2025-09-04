package com.topinfo.basic.platform.plugin.data.permission.autoconfig;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: DataPermissionAutoConfiguration
 * @Description: 数据权限配置
 * @Author 杨占锐
 * @Date 2024/12/3 17:24
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
@Configuration
@ComponentScan(basePackages = {"com.topinfo.basic.platform.plugin.data.permission.core"})
@MapperScan({"com.topinfo.basic.platform.plugin.data.permission.core.mapper"})
public class DataPermissionAutoConfiguration {


}

package com.jiuxi.datapermission.autoconfig;

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
@ComponentScan(basePackages = {"com.jiuxi.datapermission.core"})
@MapperScan({"com.jiuxi.datapermission.core.mapper"})
public class DataPermissionAutoConfiguration {


}
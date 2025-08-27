package com.topinfo.basic.platform.log.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: LogConfigurationProperties
 * @Description: 日志的配置属性
 * @Author: 杨攀
 * @Date: 2021/8/10 18:24
 * @Copyright: 2021 www.tuxun.net Inc. All rights reserved.
 */
@ConfigurationProperties(prefix = "topinfo.logger")
public class LoggerConfigurationProperties {

    /**
     * 数据库类型： Mysql/TD
     */
    public  String  dbtype = "Mysql";


    /**
     * 开发人员自定义的允许记录的日志模块
     */
    public Map<String, String> customizeModuleMap = new HashMap<String, String>();

    /**
     * 默认模块类型
     */
    private Map<String, String> defaultModuleMap = new HashMap<String, String>(){
        {
            put("login", "登录");
        }
    };


    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public Map getCustomizeModuleMap() {
        return customizeModuleMap;
    }

    public void setCustomizeModuleMap(Map customizeModuleMap) {
        customizeModuleMap.putAll(defaultModuleMap);
        this.customizeModuleMap = customizeModuleMap;
    }
}

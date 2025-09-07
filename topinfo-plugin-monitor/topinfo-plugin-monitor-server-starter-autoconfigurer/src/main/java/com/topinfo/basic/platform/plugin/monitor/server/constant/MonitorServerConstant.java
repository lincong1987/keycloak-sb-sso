package com.topinfo.basic.platform.plugin.monitor.server.constant;

import com.topinfo.basic.platform.plugin.monitor.common.constant.MonitorCommonConstant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MonitorClientConstant
 * @Description:
 * @Author 杨占锐
 * @Date 2024/11/15 13:37
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public class MonitorServerConstant extends MonitorCommonConstant {

    /**
     * 基本信息缓存key
     */
    public static final String KEY_PREFIX = "topinfo:platform:plugin:monitor:";

    /**
     * 基本信息缓存key
     */
    public static final String BASEINFO_KEY = KEY_PREFIX + "client:baseinfo:";

    /**
     * 配置信息缓存key
     */
    public static final String ALARM_CONFIG_KEY = KEY_PREFIX + "alarm:config";

    /**
     * 发送邮件缓存key
     */
    public static final String SEND_MAIL_KEY = KEY_PREFIX + "client:send_mail:";

    /**
     * 心跳信息缓存key
     */
    public static final String HEARTBEAT_KEY = KEY_PREFIX + "heartbeat:";

    /**
     * 心跳报警信息缓存key
     */
    public static final String HEARTBEAT_ALARM_KEY = KEY_PREFIX + "heartbeat_alarm:";

    /**
     * 缓存天数
     */
    public static final int CACHE_DAYS = 30;

    /**
     * 发送邮件缓存时间（小时）
     */
    public static final int CACHE_HOURS = 12;

    /**
     * 离线判定的时间
     */
    public static final int OFFLINE_MINUTE = 5;

    /**
     * 服务器资源超阈值，持续时间
     */
    public static final int CONTINUE_MINUTE = 5;

    /**
     * cpu
     */
    public static final String CPU = "cpu";

    /**
     * 内存
     */
    public static final String MEMORY = "memory";

    /**
     * 硬盘
     */
    public static final String DISK = "disk";

    /**
     * 离线
     */
    public static final String OFF_LINE = "off_line";


    /**
     * 资源信息名称
     */
    public static final Map<String, String> SOURCE_MAP = new HashMap<String, String>(){{
        put(OFF_LINE, "系统离线");
        put(CPU, "cpu使用率过高");
        put(MEMORY, "内存使用率过高");
        put(DISK, "硬盘使用率过高");
        put(SINGLE_DATA_SOURCE, "单数据源连接失败");
        put(DYNAMIC_DATA_SOURCE, "动态数据源连接失败");
        put(REDIS, "Redis连接失败");
        put(MQ, "MQ连接失败");
        put(MQTT, "MQTT连接失败");
    }};

    /**
     * 需要计算后产生报警的资源信息
     */
    public static final List<String> SOURCE_LIST = Arrays.asList(CPU, MEMORY, DISK, SINGLE_DATA_SOURCE, DYNAMIC_DATA_SOURCE, REDIS, MQ, MQTT);
}

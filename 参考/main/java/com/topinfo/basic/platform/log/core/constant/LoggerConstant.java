package com.topinfo.basic.platform.log.core.constant;

import cn.hutool.json.JSONObject;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 常量定义
 * @ClassName: Constant
 * @Author: pand
 * @Date: 2021-08-02 15:20
 * @Copyright: 2021 www.tuxun.net Inc. All rights reserved.
 */
public class LoggerConstant {




    /**
     * 是
     *
     * @author 杨攀
     * @date 2020/11/30 17:50
     * @return
     */
    public static final int YES = 1;


    /**
     * 否
     *
     * @author 杨攀
     * @date 2020/11/30 17:50
     * @return
     */
    public static final int NO = 0;

    /**
     * 日期格式化
     *
     * @author 杨攀
     * @date 2020/11/30 17:45
     * @return
     */
    public static final class DateFormatter {

        public static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("uuuuMMdd");

        public static final DateTimeFormatter yyyyMMddHHmmss = DateTimeFormatter.ofPattern("uuuuMMddHHmmss");

    }
}
package com.jiuxi.common.bean;

import java.io.Serializable;

/**
 * @ClassName: ResponseCode
 * @Description: 错误码code
 * @Author: 杨攀
 * @Date: 2020/1/10 11:30
 * @Copyright: 2020 www.tuxun.net Inc. All rights reserved.
 */
public enum ErrorCode implements Serializable {

    SUCCESS(1, "成功"),

    ERROR(-1, "失败"),

    PARAM_ERROR(-3, "参数错误"),

    TOKEN_ERROR(-44, "token过期，凭证验签失败！"),

    AUTHHORIZONTAL_ERROR(-45, "水平越权验证失败！"),

    SYTEM_LOGIN_NO_ROLE_ERROR(-98, "未分配角色，请联系管理员！"),

    UNSELECTDEPT_ERROR(-99, "未选择部门登陆"),

    SYTEM_ERROR(-100, "系统错误，请联系管理员！");

    private int code;

    private String msg;


    private ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }
}

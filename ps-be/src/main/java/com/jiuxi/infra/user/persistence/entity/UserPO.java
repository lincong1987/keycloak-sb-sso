package com.jiuxi.infra.user.persistence.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户持久化对象
 * 对应数据库中的tp_person表
 * 
 * @author DDD Refactor
 * @date 2025-09-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tp_person")
public class UserPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 人员ID
     */
    @TableId(value = "PERSON_ID", type = IdType.ASSIGN_ID)
    private String personId;

    /**
     * 人员编号
     */
    @TableField("PERSON_NO")
    private String personNo;

    /**
     * 人员名称
     */
    @TableField("PERSON_NAME")
    private String personName;

    /**
     * 性别
     */
    @TableField("SEX")
    private String sex;

    /**
     * 身份证类型
     */
    @TableField("IDTYPE")
    private String idtype;

    /**
     * 身份证号码
     */
    @TableField("IDCARD")
    private String idcard;

    /**
     * 籍贯
     */
    @TableField("NATIVE_PLACE")
    private String nativePlace;

    /**
     * 民族
     */
    @TableField("SAFEPRIN_NATION")
    private String safeprinNation;

    /**
     * 简历
     */
    @TableField("RESUME")
    private String resume;

    /**
     * 出生日期
     */
    @TableField("BIRTHDAY")
    private String birthday;

    /**
     * 职务
     */
    @TableField("OFFICE")
    private String office;

    /**
     * 所属单位ID
     */
    @TableField("ASCN_ID")
    private String ascnId;

    /**
     * 人员类别
     */
    @TableField("CATEGORY")
    private String category;

    /**
     * 头像
     */
    @TableField("PROFILE_PHOTO")
    private String profilePhoto;

    /**
     * 是否激活
     */
    @TableField("ACTIVED")
    private Integer actived;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private String tenantId;

    /**
     * 创建人
     */
    @TableField("CREATOR")
    private String creator;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField("UPDATOR")
    private String updator;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 手机号
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 电话
     */
    @TableField("TEL")
    private String tel;

    /**
     * 扩展字段1
     */
    @TableField("EXTEND01")
    private String extend01;

    /**
     * 扩展字段2
     */
    @TableField("EXTEND02")
    private String extend02;

    /**
     * 扩展字段3
     */
    @TableField("EXTEND03")
    private String extend03;
}
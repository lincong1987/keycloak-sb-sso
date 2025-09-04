package com.jiuxi.datapermission.core.bean.dto;

import java.util.List;

/**
 * @ClassName: TpDataPermissionDTO
 * @Description: 数据权限返回数据
 * @Author 杨占锐
 * @Date 2024/12/3 20:32
 * @Copyright: www.tuxun.net Inc. All rights reserved.
 */
public class TpDataPermissionDTO {

    /**
     * 数据权限范围
     */
    private String dataScope;

    /**
     * 数据权限部门id （dataScope in 'SYS4801' 时使用，等值查询, 注意：dataScope = SYS4803时，如果只选择了一个部门，也使用等值查询）
     */
    private String deptId;

    /**
     * 数据权限部门编码 （dataScope in 'SYS4802, SYS4804' 时使用。查询本级及下级）
     */
    private String deptLevelcode;

    /**
     * 数据权限部门id （dataScope in 'SYS4803' 时不为空，in 查询； 注意：dataScope = SYS4803时，如果只选择了一个部门，也使用等值查询）
     */
    private List<String> deptList;

    /**
     * 数据权限最终sql，不为空
     */
    private String permSql;


    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public String getDeptLevelcode() {
        return deptLevelcode;
    }

    public void setDeptLevelcode(String deptLevelcode) {
        this.deptLevelcode = deptLevelcode;
    }

    public List<String> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<String> deptList) {
        this.deptList = deptList;
    }

    public String getPermSql() {
        return permSql;
    }

    public void setPermSql(String permSql) {
        this.permSql = permSql;
    }
}
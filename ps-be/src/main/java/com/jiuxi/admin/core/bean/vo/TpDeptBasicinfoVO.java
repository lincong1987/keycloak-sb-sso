package com.jiuxi.admin.core.bean.vo;

import com.jiuxi.core.core.validator.group.AddGroup;
import com.jiuxi.core.core.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 单位/部门/网格/其他基本信息表
 *
 * @author pand
 * @email 2521143047@qq.com
 * @date 2020-11-18 11:05:17
 */
public class TpDeptBasicinfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @NotBlank(message = "部门id不能为空", groups = UpdateGroup.class)
    private String deptId;
    /**
     * 1：主部门   2：兼职部门
     */
    private Integer defaultDept;
    /**
     * 部门父节点
     */
    private String pdeptId;
    /**
     * 部门层级编码 单位部门层级code以三位数字递增，自动生成。可以根据此字段统计本级及下级
     */
    private String deptLevelcode;
    /**
     * 部门编号
     */
    private String deptNo;
    /**
     * 部门全称
     */
    @NotBlank(message = "部门全称不能为空", groups = AddGroup.class)
    private String deptFullName;
    /**
     * 部门简称
     */
    private String deptSimpleName;

    /**
     * 部门类型 单位or部门or网格or其他，其他是为了满足公众等没有部门的用户分类使用，归为政府编制下
     */
    @NotBlank(message = "部门类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String deptType;
    private String deptTypeName;

    /**
     * 部门简介
     */
    private String deptDesc;
    /**
     * 部门排序
     */
    private Double orderIndex;
    /**
     * 部门类别 政府or企业 0政府 1企业 2其他
     */
    private Integer category;

    /**
     * 行政区划code
     */
    private String cityId;
    private String cityCode;
    private String cityFullName;
    /**
     * 负责人姓名
     */
    private String principalName;
    /**
     * 负责人电话
     */
    private String principalTel;
    /**
     * 所属机构（单位）id 政府存机构ID，企业存单位id，存在分公司的，存所在分公司单位id
     */
    private String ascnId;
    /**
     * 是否启用
     */
    private Integer enabled;

    /**
     * 是否有效
     */
    private Integer actived;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改人
     */
    private String updator;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 扩展字段01
     */
    private String extend01;
    /**
     * 扩展字段02
     */
    private String extend02;
    /**
     * 扩展字段03
     */
    private String extend03;
    /**
     * 扩展字段04
     */
    private String extend04;
    /**
     * 扩展字段05
     */
    private String extend05;
    /**
     * 是否展开
     */
    private Boolean expand;
    /**
     * 是否选中
     */
    private Boolean checked;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Integer getDefaultDept() {
        return defaultDept;
    }

    public void setDefaultDept(Integer defaultDept) {
        this.defaultDept = defaultDept;
    }

    public String getPdeptId() {
        return pdeptId;
    }

    public void setPdeptId(String pdeptId) {
        this.pdeptId = pdeptId;
    }

    public String getDeptLevelcode() {
        return deptLevelcode;
    }

    public void setDeptLevelcode(String deptLevelcode) {
        this.deptLevelcode = deptLevelcode;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptFullName() {
        return deptFullName;
    }

    public void setDeptFullName(String deptFullName) {
        this.deptFullName = deptFullName;
    }

    public String getDeptSimpleName() {
        return deptSimpleName;
    }

    public void setDeptSimpleName(String deptSimpleName) {
        this.deptSimpleName = deptSimpleName;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public String getDeptTypeName() {
        return deptTypeName;
    }

    public void setDeptTypeName(String deptTypeName) {
        this.deptTypeName = deptTypeName;
    }

    public Integer getActived() {
        return actived;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public Double getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Double orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityFullName() {
        return cityFullName;
    }

    public void setCityFullName(String cityFullName) {
        this.cityFullName = cityFullName;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalTel() {
        return principalTel;
    }

    public void setPrincipalTel(String principalTel) {
        this.principalTel = principalTel;
    }

    public String getAscnId() {
        return ascnId;
    }

    public void setAscnId(String ascnId) {
        this.ascnId = ascnId;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public void setActived(Integer actived) {
        this.actived = actived;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getExtend01() {
        return extend01;
    }

    public void setExtend01(String extend01) {
        this.extend01 = extend01;
    }

    public String getExtend02() {
        return extend02;
    }

    public void setExtend02(String extend02) {
        this.extend02 = extend02;
    }

    public String getExtend03() {
        return extend03;
    }

    public void setExtend03(String extend03) {
        this.extend03 = extend03;
    }

    public String getExtend04() {
        return extend04;
    }

    public void setExtend04(String extend04) {
        this.extend04 = extend04;
    }

    public String getExtend05() {
        return extend05;
    }

    public void setExtend05(String extend05) {
        this.extend05 = extend05;
    }

    public Boolean getExpand() {
        return expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}

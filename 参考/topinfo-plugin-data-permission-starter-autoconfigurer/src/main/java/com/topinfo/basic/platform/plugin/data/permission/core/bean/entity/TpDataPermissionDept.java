package com.topinfo.basic.platform.plugin.data.permission.core.bean.entity;

import java.io.Serializable;

/**
 * 数据权限部门表
 * 
 * @author yangzr
 * @email 373416873@qq.com
 * @date 2024-12-03 17:33:54
 */
public class TpDataPermissionDept implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 物理主键
	 */
	private String id;

	/**
	 * 数据权限id
	 */
	private String permId;

	/**
	 * 指定的部门id
	 */
	private String deptId;

	/**
	 * 租户id
	 */
	private String tenantId;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}


	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}



}

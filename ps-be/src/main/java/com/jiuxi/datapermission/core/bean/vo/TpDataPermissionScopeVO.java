package com.jiuxi.datapermission.core.bean.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 数据权限范围表
 * 
 * @author yangzr
 * @email 373416873@qq.com
 * @date 2024-12-03 17:33:54
 */
public class TpDataPermissionScopeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 物理主键
	 */
	private String permId;

	/**
	 * 人员id
	 */
	private String personId;

	/**
	 * 授权时部门id
	 */
	private String deptId;

	/**
	 * 数据范围
	 */
	private String dataScope;

	/**
	 * 指定部门ids
	 */
	private String deptIds;

	/**
	 * 指定部门集合
	 */
	private List<String> deptList;

	public List<String> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<String> deptList) {
		this.deptList = deptList;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

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

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}
}
package com.topinfo.basic.platform.log.core.bean.entity;

import java.io.Serializable;

/**
 * 操作日志表
 * 
 * @author pand
 * @email 
 * @date 2022-09-21 14:00:19
 */
public class TpOperateLog implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 主键
	 */
	private String logId;

	/**
	 * 操作人员id
	 */
	private String personId;

	/**
	 * 模块code
	 */
	private String moduleCode;

	/**
	 * 操作时间
	 */
	private String operterTime;

	/**
	 * 操作类型
	 */
	private String operterType;

	/**
	 * 操作记录ID、修改、删除时，记录ID
	 */
	private String operterRid;

	/**
	 * 操作人IP
	 */
	private String operterIp;

	/**
	 * 操作人浏览器
	 */
	private String operterBrowser;

	/**
	 * 账号
	 */
	private String username;

	/**
	 * 单位ID
	 */
	private String ascnId;

	/**
	 * 人员类别：政府人员or企业人员or其他
	 */
	private Integer category;

	/**
	 * 行政区划代码
	 */
	private String cityCode;

	/**
	 * 有效标记
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

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}


	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}


	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}


	public String getOperterTime() {
		return operterTime;
	}

	public void setOperterTime(String operterTime) {
		this.operterTime = operterTime;
	}


	public String getOperterType() {
		return operterType;
	}

	public void setOperterType(String operterType) {
		this.operterType = operterType;
	}


	public String getOperterRid() {
		return operterRid;
	}

	public void setOperterRid(String operterRid) {
		this.operterRid = operterRid;
	}


	public String getOperterIp() {
		return operterIp;
	}

	public void setOperterIp(String operterIp) {
		this.operterIp = operterIp;
	}


	public String getOperterBrowser() {
		return operterBrowser;
	}

	public void setOperterBrowser(String operterBrowser) {
		this.operterBrowser = operterBrowser;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getAscnId() {
		return ascnId;
	}

	public void setAscnId(String ascnId) {
		this.ascnId = ascnId;
	}


	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}


	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}


	public Integer getActived() {
		return actived;
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
}

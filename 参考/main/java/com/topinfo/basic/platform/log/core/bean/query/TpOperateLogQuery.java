package com.topinfo.basic.platform.log.core.bean.query;


import java.io.Serializable;


/**
 * 操作日志表
 *
 * @author pand
 * @email 
 * @date 2022-09-21 14:00:19
 */
public class TpOperateLogQuery implements Serializable {
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
    private String operterTimeStart;

	private String operterTimeEnd;
    
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
     * 当前页
     */
    private Integer current;

    /**
     * 每页记录数
     */
    private Integer size;


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

	public String getOperterTimeStart() {
		return operterTimeStart;
	}

	public void setOperterTimeStart(String operterTimeStart) {
		this.operterTimeStart = operterTimeStart;
	}

	public String getOperterTimeEnd() {
		return operterTimeEnd;
	}

	public void setOperterTimeEnd(String operterTimeEnd) {
		this.operterTimeEnd = operterTimeEnd;
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


    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}

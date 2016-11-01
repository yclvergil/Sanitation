package com.songming.sanitation.manage.bean;

import java.io.Serializable;

/**
 * (ORGANIZATION)
 * 
 * @author bianj
 * @version 1.0.0 2016-01-20
 */
public class OrganizationDto implements Serializable {
	/** 版本号 */
	private static final long serialVersionUID = -29774815140513448L;

	/**  */
	private Integer id;

	/**  */
	private String orgName;

	/**  */
	private Integer pid;

	/**  */
	private String orgType;

	/**  */
	private Integer orgSort;

	/**  */
	private Integer orgLevel;

	/**  */
	private String orgCode;

	/**  */
	private Integer areaId;

	private String state; 
	
	private String summaryText; 

	/**
	 * 签到情况统计
	 * @return
	 */
	public String getSummaryText() {
		return summaryText;
	}

	public void setSummaryText(String summaryText) {
		this.summaryText = summaryText;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * 设置
	 * 
	 * @param id
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getOrgName() {
		return this.orgName;
	}

	/**
	 * 设置
	 * 
	 * @param orgName
	 * 
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public Integer getPid() {
		return this.pid;
	}

	/**
	 * 设置
	 * 
	 * @param pid
	 * 
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getOrgType() {
		return this.orgType;
	}

	/**
	 * 设置
	 * 
	 * @param orgType
	 * 
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public Integer getOrgSort() {
		return this.orgSort;
	}

	/**
	 * 设置
	 * 
	 * @param orgSort
	 * 
	 */
	public void setOrgSort(Integer orgSort) {
		this.orgSort = orgSort;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public Integer getOrgLevel() {
		return this.orgLevel;
	}

	/**
	 * 设置
	 * 
	 * @param orgLevel
	 * 
	 */
	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getOrgCode() {
		return this.orgCode;
	}

	/**
	 * 设置
	 * 
	 * @param orgCode
	 * 
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public Integer getAreaId() {
		return this.areaId;
	}

	/**
	 * 设置
	 * 
	 * @param areaId
	 * 
	 */
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
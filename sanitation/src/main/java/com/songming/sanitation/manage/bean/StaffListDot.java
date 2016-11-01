package com.songming.sanitation.manage.bean;

import java.util.List;

public class StaffListDot {

	private Integer id;
	private String orgName;
	private Integer pid;
	private String orgType;
	private Integer orgSort;
	private String orgLevel;
	private String orgCode;
	private String areaId;
	private String state;
	private List<StaffDto> staffs;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public Integer getOrgSort() {
		return orgSort;
	}
	public void setOrgSort(Integer orgSort) {
		this.orgSort = orgSort;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<StaffDto> getStaffs() {
		return staffs;
	}
	public void setStaffs(List<StaffDto> staffs) {
		this.staffs = staffs;
	}

}

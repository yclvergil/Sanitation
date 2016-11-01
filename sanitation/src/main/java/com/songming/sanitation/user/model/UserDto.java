package com.songming.sanitation.user.model;

import java.io.Serializable;

import com.songming.sanitation.R.integer;

/**
 * (USER)
 * 
 * @author bianj
 * @version 1.0.0 2015-12-25
 */
public class UserDto implements Serializable {

	/** 版本号 */
	private static final long serialVersionUID = 3015855332743267304L;

	/**  */
	private Long id;

	/**  */
	private String loginName;

	/**  */
	private String password;

	/**  */
	private Integer loginCount;

	/**  */
	private String previousVisit;

	/**  */
	private String lastVisit;

	/**  */
	private String description;

	/**  */
	private String createDate;

	/** 创建人 */
	private String createBy;

	/** 修改人 */
	private String lastUpdateBy;

	/** 修改时间 */
	private String lastUpdateDate;

	/**  */
	private Integer deleteFlag;

	/** 姓名 */
	private String name;

	/** 年龄 */
	private Integer age;

	/** 籍贯 */
	private String origin;

	/** 入职时间 */
	private String entryTime;

	/** 身份证号码 */
	private String identityCard;

	/** 电话 */
	private String phone;

	private String oldPassword;

	private String newPassword;

	/** 消息推送必须的唯一标示 */
	private String channelId;

	/** 用户手机设备3：android，4：ios */
	private Integer deviceType;

	private Long staffId;

	private Integer orgId;

	private Long userId;

	private String licenseNumbe;

	private String licenseExpiryTime;

	private String quasiDrivingType;

	private Integer gender;

	private Long stationId;

	private integer isSign;

	private String searchParam;

	private String orgName;

	private String stationName;
	
	private String photoUrl;

	private String sortLetters; // 显示数据拼音的首字母

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getSearchParam() {
		return searchParam;
	}

	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public integer getIsSign() {
		return isSign;
	}

	public void setIsSign(integer isSign) {
		this.isSign = isSign;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * 设置
	 * 
	 * @param id
	 * 
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getLoginName() {
		return this.loginName;
	}

	/**
	 * 设置
	 * 
	 * @param loginName
	 * 
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * 设置
	 * 
	 * @param password
	 * 
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getCreateDate() {
		return this.createDate;
	}

	/**
	 * 设置
	 * 
	 * @param createDate
	 * 
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 设置
	 * 
	 * @param description
	 * 
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public Integer getLoginCount() {
		return this.loginCount;
	}

	/**
	 * 设置
	 * 
	 * @param loginCount
	 * 
	 */
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getPreviousVisit() {
		return this.previousVisit;
	}

	/**
	 * 设置
	 * 
	 * @param previousVisit
	 * 
	 */
	public void setPreviousVisit(String previousVisit) {
		this.previousVisit = previousVisit;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public String getLastVisit() {
		return this.lastVisit;
	}

	/**
	 * 设置
	 * 
	 * @param lastVisit
	 * 
	 */
	public void setLastVisit(String lastVisit) {
		this.lastVisit = lastVisit;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLicenseNumbe() {
		return licenseNumbe;
	}

	public void setLicenseNumbe(String licenseNumbe) {
		this.licenseNumbe = licenseNumbe;
	}

	public String getLicenseExpiryTime() {
		return licenseExpiryTime;
	}

	public void setLicenseExpiryTime(String licenseExpiryTime) {
		this.licenseExpiryTime = licenseExpiryTime;
	}

	public String getQuasiDrivingType() {
		return quasiDrivingType;
	}

	public void setQuasiDrivingType(String quasiDrivingType) {
		this.quasiDrivingType = quasiDrivingType;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	
}
package com.songming.sanitation.manage.bean;

import java.util.Date;

/**
 * (T_VERSION)
 * 
 * @author bianj
 * @version 1.0.0 2015-12-09
 */
public class VersionDTO implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = -1570633600795713390L;

	/** UUID，主键 */
	private String id;

	/** 版本号 */
	private String versionId;

	/** 版本名称 */
	private String versionName;

	/** 版本描述 */
	private String versionDescription;

	/** 项目类别(1:医人行,2:健康芯,3:湘女家政) */
	private String projectCategory;

	/** 更新时间 */
	private Date releaseTime;

	/** 下载地址 */
	private String downloadUrl;

	/** 更新人 */
	private String updateUser;

	/** 版本类型；1:Android，2:IOS */
	private Integer versionType;

	/** 下载次数 */
	private Integer downloadNumber;

	private String iosDownloadUrl;

	/**
	 * 获取UUID，主键
	 * 
	 * @return UUID
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置UUID，主键
	 * 
	 * @param id
	 *            UUID，主键
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取版本号
	 * 
	 * @return 版本号
	 */
	public String getVersionId() {
		return this.versionId;
	}

	/**
	 * 设置版本号
	 * 
	 * @param versionId
	 *            版本号
	 */
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	/**
	 * 获取版本名称
	 * 
	 * @return 版本名称
	 */
	public String getVersionName() {
		return this.versionName;
	}

	/**
	 * 设置版本名称
	 * 
	 * @param versionName
	 *            版本名称
	 */
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	/**
	 * 获取版本描述
	 * 
	 * @return 版本描述
	 */
	public String getVersionDescription() {
		return this.versionDescription;
	}

	/**
	 * 设置版本描述
	 * 
	 * @param versionDescription
	 *            版本描述
	 */
	public void setVersionDescription(String versionDescription) {
		this.versionDescription = versionDescription;
	}

	/**
	 * 获取项目类别(1:医人行,2:健康芯)
	 * 
	 * @return 项目类别(1
	 */
	public String getProjectCategory() {
		return this.projectCategory;
	}

	/**
	 * 设置项目类别(1:医人行,2:健康芯)
	 * 
	 * @param projectCategory
	 *            项目类别(1:医人行,2:健康芯)
	 */
	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	/**
	 * 获取更新时间
	 * 
	 * @return 更新时间
	 */
	public Date getReleaseTime() {
		return this.releaseTime;
	}

	/**
	 * 设置更新时间
	 * 
	 * @param releaseTime
	 *            更新时间
	 */
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	/**
	 * 获取下载地址
	 * 
	 * @return 下载地址
	 */
	public String getDownloadUrl() {
		return this.downloadUrl;
	}

	/**
	 * 设置下载地址
	 * 
	 * @param downloadUrl
	 *            下载地址
	 */
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	/**
	 * 获取更新人
	 * 
	 * @return 更新人
	 */
	public String getUpdateUser() {
		return this.updateUser;
	}

	/**
	 * 设置更新人
	 * 
	 * @param updateUser
	 *            更新人
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 获取版本类型；1:Android，2:IOS
	 * 
	 * @return 版本类型；1
	 */
	public Integer getVersionType() {
		return this.versionType;
	}

	/**
	 * 设置版本类型；1:Android，2:IOS
	 * 
	 * @param versionType
	 *            版本类型；1:Android，2:IOS
	 */
	public void setVersionType(Integer versionType) {
		this.versionType = versionType;
	}

	/**
	 * 获取下载次数
	 * 
	 * @return 下载次数
	 */
	public Integer getDownloadNumber() {
		return this.downloadNumber;
	}

	/**
	 * 设置下载次数
	 * 
	 * @param downloadNumber
	 *            下载次数
	 */
	public void setDownloadNumber(Integer downloadNumber) {
		this.downloadNumber = downloadNumber;
	}

	public String getIosDownloadUrl() {
		return iosDownloadUrl;
	}

	public void setIosDownloadUrl(String iosDownloadUrl) {
		this.iosDownloadUrl = iosDownloadUrl;
	}

}
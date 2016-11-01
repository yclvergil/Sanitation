package com.songming.sanitation.manage.bean;

/******************************************************************
 ** 类    名：TStaffJobSignDTO
 ** 描    述：员工考勤记录表
 ** 创 建 者：bianj
 ** 创建时间：2016-05-06 16:17:04
 ******************************************************************/

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 员工考勤记录表(T_STAFF_JOB_SIGN)
 * 
 * @author bianj
 * @version 1.0.0 2016-05-06
 */
public class TStaffJobSignDto implements Serializable {
	/** 版本号 */
	private static final long serialVersionUID = -3607715874573330733L;

	/**  */
	private Long id;

	/** 员工id/sys_staff表id */
	private Long staffId;

	private String staffName;

	/** 签到时间 */
	private String signTime;

	/** 签到经度 */
	private Double signLongitude;

	/** 签到纬度 */
	private Double signLatitude;

	/** 签到类型/1：上班 2：下班 */
	private Integer signType;

	/** 备注 */
	private String remark;

	/** 创建人 */
	private String createBy;

	/** 创建时间 */
	private String createDate;

	/** 更新人 */
	private String lastUpdateBy;

	/** 更新时间 */
	private String lastUpdateDate;

	/** 删除标示，0：未删除，1：已删除 */
	private Integer deleteFlag;

	/** 创建人机构id */
	private Long createOrgId;

	/** 创建人id */
	private Long createId;

	/**
	 * 上传文件的id集合
	 */
	private List<Map<String, Long>> fileKeys;

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
	 * 获取员工id/sys_staff表id
	 * 
	 * @return 员工id/sys_staff表id
	 */
	public Long getStaffId() {
		return this.staffId;
	}

	/**
	 * 设置员工id/sys_staff表id
	 * 
	 * @param staffId
	 *            员工id/sys_staff表id
	 */
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	/**
	 * 获取签到时间
	 * 
	 * @return 签到时间
	 */
	public String getSignTime() {
		return this.signTime;
	}

	/**
	 * 设置签到时间
	 * 
	 * @param signTime
	 *            签到时间
	 */
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	/**
	 * 获取签到经度
	 * 
	 * @return 签到经度
	 */
	public Double getSignLongitude() {
		return this.signLongitude;
	}

	/**
	 * 设置签到经度
	 * 
	 * @param signLongtitude
	 *            签到经度
	 */
	public void setSignLongitude(Double signLongitude) {
		this.signLongitude = signLongitude;
	}

	/**
	 * 获取签到纬度
	 * 
	 * @return 签到纬度
	 */
	public Double getSignLatitude() {
		return this.signLatitude;
	}

	/**
	 * 设置签到纬度
	 * 
	 * @param signLatitude
	 *            签到纬度
	 */
	public void setSignLatitude(Double signLatitude) {
		this.signLatitude = signLatitude;
	}

	/**
	 * 获取签到类型/1：上班 2：下班
	 * 
	 * @return 签到类型/1
	 */
	public Integer getSignType() {
		return this.signType;
	}

	/**
	 * 设置签到类型/1：上班 2：下班
	 * 
	 * @param signType
	 *            签到类型/1：上班 2：下班
	 */
	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 设置备注
	 * 
	 * @param remark
	 *            备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取创建人
	 * 
	 * @return 创建人
	 */
	public String getCreateBy() {
		return this.createBy;
	}

	/**
	 * 设置创建人
	 * 
	 * @param createBy
	 *            创建人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 获取创建时间
	 * 
	 * @return 创建时间
	 */
	public String getCreateDate() {
		return this.createDate;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createDate
	 *            创建时间
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取更新人
	 * 
	 * @return 更新人
	 */
	public String getLastUpdateBy() {
		return this.lastUpdateBy;
	}

	/**
	 * 设置更新人
	 * 
	 * @param lastUpdateBy
	 *            更新人
	 */
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	/**
	 * 获取更新时间
	 * 
	 * @return 更新时间
	 */
	public String getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	/**
	 * 设置更新时间
	 * 
	 * @param lastUpdateDate
	 *            更新时间
	 */
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * 获取删除标示，0：未删除，1：已删除
	 * 
	 * @return 删除标示
	 */
	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	/**
	 * 设置删除标示，0：未删除，1：已删除
	 * 
	 * @param deleteFlag
	 *            删除标示，0：未删除，1：已删除
	 */
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Long getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(Long createOrgId) {
		this.createOrgId = createOrgId;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public List<Map<String, Long>> getFileKeys() {
		return fileKeys;
	}

	public void setFileKeys(List<Map<String, Long>> fileKeys) {
		this.fileKeys = fileKeys;
	}
}
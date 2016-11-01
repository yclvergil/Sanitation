package com.songming.sanitation.workdeal.bean;

/******************************************************************
 ** 类    名：TEventDTO
 ** 描    述：事件信息表
 ** 创 建 者：bianj
 ** 创建时间：2016-05-06 16:17:04
 ******************************************************************/

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 事件信息表(T_EVENT)
 * 
 * @author bianj
 * @version 1.0.0 2016-05-06
 */
public class TEventDto implements Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 3914999228323210045L;

	/**  id*/
	private Long id;

	/**
	 * 上传文件的id集合
	 */
	private List<Map<String, Long>> fileKeys;

	/** 事件总数 */
	private String num;

	/** 标题 */
	private String eventTitle;

	/** 发起时间 */
	private String eventDate;

	/** 事件发生地址 */
	private String address;

	/** 发起人id */
	private Long createId;

	/** 发起人所属部门id */
	private Integer createOrgId;

	/** 指派人id */
	private Long assignId;

	/** 执行人id */
	private Long executeId;

	/** 处理状态/1：待处理 2：处理中 3：处理完成 */
	private Integer status;

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

	private String createName;

	private String createOrgName;

	private String assignName;

	private String executeName;
	
	private String phone;
	
	private List<ImageDto> files;
	
	private String isExecute;
	
	private String isAssign;
	
	private String isRead;
	
	

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getIsAssign() {
		return isAssign;
	}

	public void setIsAssign(String isAssign) {
		this.isAssign = isAssign;
	}

	public String getIsExecute() {
		return isExecute;
	}

	public void setIsExecute(String isExecute) {
		this.isExecute = isExecute;
	}

	/**
	 * 事件详情状态
	 */
	private List<TEventDetailDto> details;

	
	
	public List<ImageDto> getFiles() {
		return files;
	}

	public void setFiles(List<ImageDto> files) {
		this.files = files;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
	 * 获取标题
	 * 
	 * @return 标题
	 */
	public String getEventTitle() {
		return this.eventTitle;
	}

	/**
	 * 设置标题
	 * 
	 * @param eventTitle
	 *            标题
	 */
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	/**
	 * 获取发起时间
	 * 
	 * @return 发起时间
	 */
	public String getEventDate() {
		return this.eventDate;
	}

	/**
	 * 设置发起时间
	 * 
	 * @param eventDate
	 *            发起时间
	 */
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * 获取发起人id
	 * 
	 * @return 发起人id
	 */
	public Long getCreateId() {
		return this.createId;
	}

	/**
	 * 设置发起人id
	 * 
	 * @param createId
	 *            发起人id
	 */
	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	/**
	 * 获取发起人所属部门id
	 * 
	 * @return 发起人所属部门id
	 */
	public Integer getCreateOrgId() {
		return this.createOrgId;
	}

	/**
	 * 设置发起人所属部门id
	 * 
	 * @param createOrgId
	 *            发起人所属部门id
	 */
	public void setCreateOrgId(Integer createOrgId) {
		this.createOrgId = createOrgId;
	}

	/**
	 * 获取指派人id
	 * 
	 * @return 指派人id
	 */
	public Long getAssignId() {
		return this.assignId;
	}

	/**
	 * 设置指派人id
	 * 
	 * @param assignId
	 *            指派人id
	 */
	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}

	/**
	 * 获取执行人id
	 * 
	 * @return 执行人id
	 */
	public Long getExecuteId() {
		return this.executeId;
	}

	/**
	 * 设置执行人id
	 * 
	 * @param executeId
	 *            执行人id
	 */
	public void setExecuteId(Long executeId) {
		this.executeId = executeId;
	}

	/**
	 * 获取处理状态/1：待处理 2：处理中 3：处理完成
	 * 
	 * @return 处理状态/1
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 设置处理状态/1：待处理 2：处理中 3：处理完成
	 * 
	 * @param status
	 *            处理状态/1：待处理 2：处理中 3：处理完成
	 */
	public void setStatus(Integer status) {
		this.status = status;
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

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getAssignName() {
		return assignName;
	}

	public void setAssignName(String assignName) {
		this.assignName = assignName;
	}

	public String getExecuteName() {
		return executeName;
	}

	public void setExecuteName(String executeName) {
		this.executeName = executeName;
	}

	public List<Map<String, Long>> getFileKeys() {
		return fileKeys;
	}

	public void setFileKeys(List<Map<String, Long>> fileKeys) {
		this.fileKeys = fileKeys;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<TEventDetailDto> getDetails() {
		return details;
	}

	public void setDetails(List<TEventDetailDto> details) {
		this.details = details;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
}
package com.songming.sanitation.workdeal.bean;

import java.io.Serializable;
import java.util.List;

/******************************************************************
 ** 类    名：TEventDetailDTO
 ** 描    述：事件信息状态表
 ** 创 建 者：bianj
 ** 创建时间：2016-05-25 11:24:42
 ******************************************************************/

/**
 * 事件信息状态表(T_EVENT_DETAIL)
 * 
 * @author bianj
 * @version 1.0.0 2016-05-25
 */
public class TEventDetailDto implements Serializable {
	/** 版本号 */
	private static final long serialVersionUID = -7942158468619205914L;

	/**  */
	private Long id;

	/** 事件标题 */
	private String eventTitle;

	/** 事件发起时间 */
	private String eventDate;

	/** 事件id */
	private Long eventId;

	/** 事件发生描述 */
	private String description;

	/** 指派人id */
	private Long assignId;

	private String assignName;

	/** 执行人id */
	private Long executeId;

	private String executeName;

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

	/** 电话 */
	private String executePhone;

	private List<ImageDto> files;

	public List<ImageDto> getFiles() {
		return files;
	}

	public void setFiles(List<ImageDto> files) {
		this.files = files;
	}

	public String getExecutePhone() {
		return executePhone;
	}

	public void setExecutePhone(String executePhone) {
		this.executePhone = executePhone;
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
	 * 获取事件id
	 * 
	 * @return 事件id
	 */
	public Long getEventId() {
		return this.eventId;
	}

	/**
	 * 设置事件id
	 * 
	 * @param eventId
	 *            事件id
	 */
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Long getAssignId() {
		return assignId;
	}

	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}

	public Long getExecuteId() {
		return executeId;
	}

	public void setExecuteId(Long executeId) {
		this.executeId = executeId;
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

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

}
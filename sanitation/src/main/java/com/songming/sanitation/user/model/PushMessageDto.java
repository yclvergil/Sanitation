package com.songming.sanitation.user.model;

import java.io.Serializable;

import org.apache.http.entity.SerializableEntity;

/******************************************************************
 ** 类    名：PushDriverMessageDTO
 ** 描    述：推送司机消息表
 ** 创 建 者：bianj
 ** 创建时间：2016-04-29 23:06:35
 ******************************************************************/

/**
 * 推送司机消息表(PUSH_DRIVER_MESSAGE)
 * 
 * @author bianj
 * @version 1.0.0 2016-04-29
 */
public class PushMessageDto implements Serializable {
	/** 版本号 */
	private static final long serialVersionUID = -6155492765038847212L;

	/**  */
	private Long id;

	/** 被推送人id */
	private Long staffId;

	/** 消息标题 */
	private String title;

	/** 消息内容 */
	private String description;

	/** 自定义消息参数 */
	private String customContent;

	/** 消息的类型 1.推送给司机，2.推送给货主，3.美软祝福 */
	private Integer msgType;

	/** 阅读状态 0.否，1.阅 */
	private Integer readFlag;

	/** 阅读时间 */
	private String readDate;

	/** 创建人 */
	private String createBy;

	/** 创建日期 */
	private String createDate;

	/** 更新人 */
	private String lastUpdateBy;

	/** 更新日期 */
	private String lastUpdateDate;

	/** 删除标示，0：未删除，1：已删除 */
	private Integer deleteFlag;

	/** 来自各自单据的编号，订单编码，公司发送消息编码等 */
	private Long businessId;

	/** 推送是否成功，0：失败，1：成功 */
	private Integer isSuccess;

	/** 订单状态 0：不感兴趣 1：已报价，2:待报价 */
	private Integer orderStatus;

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

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	/**
	 * 获取消息标题
	 * 
	 * @return 消息标题
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 设置消息标题
	 * 
	 * @param title
	 *            消息标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取消息内容
	 * 
	 * @return 消息内容
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 设置消息内容
	 * 
	 * @param description
	 *            消息内容
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取自定义消息参数
	 * 
	 * @return 自定义消息参数
	 */
	public String getCustomContent() {
		return this.customContent;
	}

	/**
	 * 设置自定义消息参数
	 * 
	 * @param customContent
	 *            自定义消息参数
	 */
	public void setCustomContent(String customContent) {
		this.customContent = customContent;
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
	 * 获取创建日期
	 * 
	 * @return 创建日期
	 */
	public String getCreateDate() {
		return this.createDate;
	}

	/**
	 * 设置创建日期
	 * 
	 * @param createDate
	 *            创建日期
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
	 * 获取更新日期
	 * 
	 * @return 更新日期
	 */
	public String getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	/**
	 * 设置更新日期
	 * 
	 * @param lastUpdateDate
	 *            更新日期
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

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(Integer readFlag) {
		this.readFlag = readFlag;
	}

	public String getReadDate() {
		return readDate;
	}

	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
}
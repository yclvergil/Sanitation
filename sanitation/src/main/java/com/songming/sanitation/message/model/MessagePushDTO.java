package com.songming.sanitation.message.model;

import java.util.Date;

/**
 * 消息推送内容表 (T_MESSAGE_PUSH)
 * 
 * @author bianj
 * @version 1.0.0 2016-02-17
 */
public class MessagePushDTO implements java.io.Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 3252272060669602466L;

	/** 主键ID */
	private String id;

	/** 用户ID */
	private String userId;

	/** 消息内容 */
	private String content;

	/** 消息类型,1.即时,2.普通 */
	private String newsType;

	/** 发布状态,1.草稿,2.已发布 */
	private String projectState;

	/** 查看状态,1.已查看,2.未查看 */
	private String seeState;

	/** 创建时间 */
	private String createDate;

	/** 失效时间 */
	private String abateDate;

	/** 标题 */
	private String title;

	/** 接收用户名称 */
	private String userName;

	/**
	 * 获取主键ID
	 * 
	 * @return 主键ID
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 设置主键ID
	 * 
	 * @param id
	 *            主键ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取用户ID
	 * 
	 * @return 用户ID
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置用户ID
	 * 
	 * @param userId
	 *            用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取消息内容
	 * 
	 * @return 消息内容
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 设置消息内容
	 * 
	 * @param content
	 *            消息内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取消息类型,1.即时,2.普通
	 * 
	 * @return 消息类型
	 */
	public String getNewsType() {
		return this.newsType;
	}

	/**
	 * 设置消息类型,1.即时,2.普通
	 * 
	 * @param newsType
	 *            消息类型,1.即时,2.普通
	 */
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	/**
	 * 获取发布状态,1.草稿,2.已发布
	 * 
	 * @return 发布状态
	 */
	public String getProjectState() {
		return this.projectState;
	}

	/**
	 * 设置发布状态,1.草稿,2.已发布
	 * 
	 * @param projectState
	 *            发布状态,1.草稿,2.已发布
	 */
	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	/**
	 * 获取查看状态,1.已查看,2.未查看
	 * 
	 * @return 查看状态
	 */
	public String getSeeState() {
		return this.seeState;
	}

	/**
	 * 设置查看状态,1.已查看,2.未查看
	 * 
	 * @param seeState
	 *            查看状态,1.已查看,2.未查看
	 */
	public void setSeeState(String seeState) {
		this.seeState = seeState;
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
	 * 获取失效时间
	 * 
	 * @return 失效时间
	 */
	public String getAbateDate() {
		return this.abateDate;
	}

	/**
	 * 设置失效时间
	 * 
	 * @param abateDate
	 *            失效时间
	 */
	public void setAbateDate(String abateDate) {
		this.abateDate = abateDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
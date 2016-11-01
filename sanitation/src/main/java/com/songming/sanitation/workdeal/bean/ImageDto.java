package com.songming.sanitation.workdeal.bean;

import java.io.Serializable;

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
public class ImageDto implements Serializable {
	/** 版本号 */
	private static final long serialVersionUID = -7942158468619205914L;

	/**  */
	private Long id;

	/** 事件标题 */
	private String fileName;

	private String dirName;

	private String filePath;

	private String fileType;

	private String businesId;

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
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getBusinesId() {
		return businesId;
	}

	public void setBusinesId(String businesId) {
		this.businesId = businesId;
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

}
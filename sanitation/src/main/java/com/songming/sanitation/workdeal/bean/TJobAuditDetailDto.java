package com.songming.sanitation.workdeal.bean;

/******************************************************************
 ** 类    名：TJobAuditDetailDTO
 ** 描    述：审批请示流程节点表
 ** 创 建 者：bianj
 ** 创建时间：2016-09-10 18:58:06
 ******************************************************************/

import java.io.Serializable;
import java.util.List;

import com.songming.sanitation.user.model.TUploadFileDto;

/**
 * 审批请示流程节点表(T_JOB_AUDIT_DETAIL)
 * 
 * @author bianj
 * @version 1.0.0 2016-09-10
 */
public class TJobAuditDetailDto implements Serializable {
	/** 版本号 */
	private static final long serialVersionUID = -59148964302749984L;

	/**  */
	private Long id;

	/** 审批id */
	private Long auditId;

	/** 请示内容 */
	private String jobContent;

	/** 状态，0：未通过，1：已通过 */
	private Integer status;

	/** 执行人id */
	private Long executeId;

	private String auditDate;

	/** 创建人 */
	private String createBy;

	/** 创建时间 */
	private String createDate;

	/** 修改人 */
	private String updateBy;

	/** 修改时间 */
	private String updateDate;

	/** 删除标示，0：未删除，1：已删除 */
	private Integer deleteFlag;

	private String applyBy;

	private String applyDate;

	private String auditContent;

	private String remark;

	private List<TUploadFileDto> files;

	private String executeName;

	private String jobTitle;

	private String jobDate;
	
	private String createName;

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	/** 标题 */
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/** 汇报时间 */
	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
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
	 * 获取审批id
	 * 
	 * @return 审批id
	 */
	public Long getAuditId() {
		return this.auditId;
	}

	/**
	 * 设置审批id
	 * 
	 * @param auditId
	 *            审批id
	 */
	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	/**
	 * 获取请示内容
	 * 
	 * @return 请示内容
	 */
	public String getJobContent() {
		return this.jobContent;
	}

	/**
	 * 设置请示内容
	 * 
	 * @param jobContent
	 *            请示内容
	 */
	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}

	/**
	 * 获取状态，0：未通过，1：已通过
	 * 
	 * @return 状态
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 设置状态，0：未通过，1：已通过
	 * 
	 * @param status
	 *            状态，0：未通过，1：已通过
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * 获取修改人
	 * 
	 * @return 修改人
	 */
	public String getUpdateBy() {
		return this.updateBy;
	}

	/**
	 * 设置修改人
	 * 
	 * @param updateBy
	 *            修改人
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 获取修改时间
	 * 
	 * @return 修改时间
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 设置修改时间
	 * 
	 * @param updateDate
	 *            修改时间
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
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

	public String getApplyBy() {
		return applyBy;
	}

	public void setApplyBy(String applyBy) {
		this.applyBy = applyBy;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getAuditContent() {
		return auditContent;
	}

	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<TUploadFileDto> getFiles() {
		return files;
	}

	public void setFiles(List<TUploadFileDto> files) {
		this.files = files;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public String getExecuteName() {
		return executeName;
	}

	public void setExecuteName(String executeName) {
		this.executeName = executeName;
	}
}
package com.songming.sanitation.workdeal.bean;
/******************************************************************
** 类    名：TJobAuditDTO
** 描    述：审批请示表
** 创 建 者：bianj
** 创建时间：2016-09-10 18:58:06
******************************************************************/

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.songming.sanitation.user.model.TUploadFileDto;

/**
 * 审批请示表(T_JOB_AUDIT)
 * 
 * @author bianj
 * @version 1.0.0 2016-09-10
 */
public class TJobAuditDto implements Serializable{
    /** 版本号 */
    private static final long serialVersionUID = -7762208643131174287L;
    
    /**  */
    private Long id;
    
    /** 申请人 */
    private String applyBy;
    
    /** 申请日期 */
    private String applyDate;
    
    /** 请示内容 */
    private String jobContent;
    
    /** 备注 */
    private String remark;
    
    /** 审批状态，0：未完成，1：已完成 */
    private Integer status;
    
    /** 申请人id */
    private Long createId;
    
    /** 申请人机构id */
    private Long createOrgId;
    
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
    
    
    /**
     * 上传文件的id集合
     */
    private List<Map<String, Long>> fileKeys;
    
    private List<TUploadFileDto> files;
    
    private String executeIds;
    
    private List<TJobAuditDetailDto> jobAuditDetails;
    
    
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
     * 获取申请人
     * 
     * @return 申请人
     */
    public String getApplyBy() {
        return this.applyBy;
    }
     
    /**
     * 设置申请人
     * 
     * @param applyBy
     *          申请人
     */
    public void setApplyBy(String applyBy) {
        this.applyBy = applyBy;
    }
    
    /**
     * 获取申请日期
     * 
     * @return 申请日期
     */
    public String getApplyDate() {
        return this.applyDate;
    }
     
    /**
     * 设置申请日期
     * 
     * @param applyDate
     *          申请日期
     */
    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
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
     *          请示内容
     */
    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
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
     *          备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * 获取审批状态，0：未完成，1：已完成
     * 
     * @return 审批状态
     */
    public Integer getStatus() {
        return this.status;
    }
     
    /**
     * 设置审批状态，0：未完成，1：已完成
     * 
     * @param status
     *          审批状态，0：未完成，1：已完成
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 获取申请人id
     * 
     * @return 申请人id
     */
    public Long getCreateId() {
        return this.createId;
    }
     
    /**
     * 设置申请人id
     * 
     * @param createId
     *          申请人id
     */
    public void setCreateId(Long createId) {
        this.createId = createId;
    }
    
    /**
     * 获取申请人机构id
     * 
     * @return 申请人机构id
     */
    public Long getCreateOrgId() {
        return this.createOrgId;
    }
     
    /**
     * 设置申请人机构id
     * 
     * @param createOrgId
     *          申请人机构id
     */
    public void setCreateOrgId(Long createOrgId) {
        this.createOrgId = createOrgId;
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
     *          创建人
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
     *          创建时间
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
     *          修改人
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
     *          修改时间
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
     *          删除标示，0：未删除，1：已删除
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

	public List<Map<String, Long>> getFileKeys() {
		return fileKeys;
	}

	public void setFileKeys(List<Map<String, Long>> fileKeys) {
		this.fileKeys = fileKeys;
	}

	public String getExecuteIds() {
		return executeIds;
	}

	public void setExecuteIds(String executeIds) {
		this.executeIds = executeIds;
	}

	public List<TJobAuditDetailDto> getJobAuditDetails() {
		return jobAuditDetails;
	}

	public void setJobAuditDetails(List<TJobAuditDetailDto> jobAuditDetails) {
		this.jobAuditDetails = jobAuditDetails;
	}

	public List<TUploadFileDto> getFiles() {
		return files;
	}

	public void setFiles(List<TUploadFileDto> files) {
		this.files = files;
	}
}
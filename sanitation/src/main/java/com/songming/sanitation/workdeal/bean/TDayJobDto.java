package com.songming.sanitation.workdeal.bean;
/******************************************************************
** 类    名：TDayJobDTO
** 描    述：日常工作表
** 创 建 者：bianj
** 创建时间：2016-09-10 15:05:08
******************************************************************/

import java.io.Serializable;

/**
 * 日常工作表(T_DAY_JOB)
 * 
 * @author bianj
 * @version 1.0.0 2016-09-10
 */
public class TDayJobDto implements Serializable{
    /** 版本号 */
    private static final long serialVersionUID = -6628498219844141281L;
    
    /**  */
    private Long id;
    
    /** 标题 */
    private String jobTitle;
    
    /** 发起时间 */
    private String jobDate;
    
    /** 发起内容 */
    private String jobContent;
    
    /** 发起人id */
    private Long createId;
    
    /** 发起人所属部门id */
    private Integer createOrgId;
    
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
    
    private String executeIds;
    
    private String createName;
    
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
    public String getJobTitle() {
        return this.jobTitle;
    }
     
    /**
     * 设置标题
     * 
     * @param jobTitle
     *          标题
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    
    /**
     * 获取发起时间
     * 
     * @return 发起时间
     */
    public String getJobDate() {
        return this.jobDate;
    }
     
    /**
     * 设置发起时间
     * 
     * @param jobDate
     *          发起时间
     */
    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }
    
    /**
     * 获取发起内容
     * 
     * @return 发起内容
     */
    public String getJobContent() {
        return this.jobContent;
    }
     
    /**
     * 设置发起内容
     * 
     * @param jobContent
     *          发起内容
     */
    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
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
     *          发起人id
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
     *          发起人所属部门id
     */
    public void setCreateOrgId(Integer createOrgId) {
        this.createOrgId = createOrgId;
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
     *          处理状态/1：待处理 2：处理中 3：处理完成
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
     *          更新人
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
     *          更新时间
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
     *          删除标示，0：未删除，1：已删除
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

	public String getExecuteIds() {
		return executeIds;
	}

	public void setExecuteIds(String executeIds) {
		this.executeIds = executeIds;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
}
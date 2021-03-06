package com.songming.sanitation.workdeal.bean;
/******************************************************************
** 类    名：TDayJobDetailDTO
** 描    述：日常工作内容状态表
** 创 建 者：bianj
** 创建时间：2016-09-10 15:05:08
******************************************************************/

import java.io.Serializable;
import java.util.Date;

/**
 * 日常工作内容状态表(T_DAY_JOB_DETAIL)
 * 
 * @author bianj
 * @version 1.0.0 2016-09-10
 */
public class TDayJobDetailDto implements Serializable{
    /** 版本号 */
    private static final long serialVersionUID = -1393305968080058597L;
    
    /**  */
    private Long id;
    
    /** 事件id */
    private Long jobId;
    
    /** 指派人id */
    private Long assignId;
    
    /** 执行人id */
    private Long executeId;
    
    /** 汇报内容 */
    private String jobContent;
    
    /** 处理状态/1：待处理 2：处理中 3：处理完成 */
    private Integer status;
    
    /** 创建人 */
    private String createBy;
    
    /** 创建时间 */
    private Date createDate;
    
    /** 更新人 */
    private String lastUpdateBy;
    
    /** 更新时间 */
    private Date lastUpdateDate;
    
    /** 删除标示，0：未删除，1：已删除 */
    private Integer deleteFlag;
    
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
     * 获取事件id
     * 
     * @return 事件id
     */
    public Long getJobId() {
        return this.jobId;
    }
     
    /**
     * 设置事件id
     * 
     * @param jobId
     *          事件id
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
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
     *          指派人id
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
     *          执行人id
     */
    public void setExecuteId(Long executeId) {
        this.executeId = executeId;
    }
    
    /**
     * 获取汇报内容
     * 
     * @return 汇报内容
     */
    public String getJobContent() {
        return this.jobContent;
    }
     
    /**
     * 设置汇报内容
     * 
     * @param jobContent
     *          汇报内容
     */
    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
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
    public Date getCreateDate() {
        return this.createDate;
    }
     
    /**
     * 设置创建时间
     * 
     * @param createDate
     *          创建时间
     */
    public void setCreateDate(Date createDate) {
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
    public Date getLastUpdateDate() {
        return this.lastUpdateDate;
    }
     
    /**
     * 设置更新时间
     * 
     * @param lastUpdateDate
     *          更新时间
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
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

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
}
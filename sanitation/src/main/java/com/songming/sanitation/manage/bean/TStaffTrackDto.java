package com.songming.sanitation.manage.bean;

import java.io.Serializable;

/******************************************************************
** 类    名：TStaffTrackDTO
** 描    述：员工运行轨迹表
** 创 建 者：bianj
** 创建时间：2016-05-24 14:01:35
******************************************************************/


/**
 * 员工运行轨迹表(T_STAFF_TRACK)
 * 
 * @author bianj
 * @version 1.0.0 2016-05-24
 */
public class TStaffTrackDto implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8883963660019688287L;
    
    /**  */
    private Long id;
    
    /** 经度 */
    private Double longitude;
    
    /** 纬度 */
    private Double latitude;
    
    /**  */
    private String staffPoint;
    
    /** 员工id/关联sys_staff表 */
    private Long staffId;
    
    /** 创建人 */
    private String createBy;
    
    /** 创建日期 */
    private String createDate;
    
    /** 创建人机构id */
    private Long createOrgId;
    
    /** 创建人id */
    private Long createId;
    
    /** 更新人 */
    private String lastUpdateBy;
    
    /** 更新日期 */
    private String lastUpdateDate;
    
    /** 删除标示，0：未删除，1：已删除 */
    private Integer deleteFlag;
    
    private String staffIds;
    
    private String name;
    private String photoUrl;
    
    public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
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
     * 获取经度
     * 
     * @return 经度
     */
    public Double getLongitude() {
        return this.longitude;
    }
     
    /**
     * 设置经度
     * 
     * @param longitude
     *          经度
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    /**
     * 获取纬度
     * 
     * @return 纬度
     */
    public Double getLatitude() {
        return this.latitude;
    }
     
    /**
     * 设置纬度
     * 
     * @param latitude
     *          纬度
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getStaffPoint() {
        return this.staffPoint;
    }
     
    /**
     * 设置
     * 
     * @param staffPoint
     *          
     */
    public void setStaffPoint(String staffPoint) {
        this.staffPoint = staffPoint;
    }
    
    /**
     * 获取员工id/关联sys_staff表
     * 
     * @return 员工id/关联sys_staff表
     */
    public Long getStaffId() {
        return this.staffId;
    }
     
    /**
     * 设置员工id/关联sys_staff表
     * 
     * @param staffId
     *          员工id/关联sys_staff表
     */
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
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
     *          创建日期
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    
    /**
     * 获取创建人机构id
     * 
     * @return 创建人机构id
     */
    public Long getCreateOrgId() {
        return this.createOrgId;
    }
     
    /**
     * 设置创建人机构id
     * 
     * @param createOrgId
     *          创建人机构id
     */
    public void setCreateOrgId(Long createOrgId) {
        this.createOrgId = createOrgId;
    }
    
    /**
     * 获取创建人id
     * 
     * @return 创建人id
     */
    public Long getCreateId() {
        return this.createId;
    }
     
    /**
     * 设置创建人id
     * 
     * @param createId
     *          创建人id
     */
    public void setCreateId(Long createId) {
        this.createId = createId;
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
     *          更新日期
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

	public String getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(String staffIds) {
		this.staffIds = staffIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
package com.songming.sanitation.user.model;

import java.io.Serializable;

/******************************************************************
** 类    名：TStaffCarRefDTO
** 描    述：车辆员工关联历史记录表
** 创 建 者：bianj
** 创建时间：2016-05-27 10:47:30
******************************************************************/


/**
 * 车辆员工关联历史记录表(T_STAFF_CAR_REF)
 * 
 * @author bianj
 * @version 1.0.0 2016-05-27
 */
public class TStaffCarRefDto implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 6902991520795571739L;
    
    /**  */
    private Long id;
    
    /** 员工id */
    private Long staffId;
    
    /** 车辆id */
    private Long carId;
    
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
    
    /**
     * 车牌号
     */
    private String carNo;
    
    /**
     * 车辆类型
     */
    private String carType;
    
    /**
     * 是否关联车辆，0：未关联，1：已关联
     */
    private Integer isRef;
    
    /**
     * 车牌
     */
    private String carCode;
    
    
    
    public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
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
     * 获取员工id
     * 
     * @return 员工id
     */
    public Long getStaffId() {
        return this.staffId;
    }
     
    /**
     * 设置员工id
     * 
     * @param staffId
     *          员工id
     */
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
    
    /**
     * 获取车辆id
     * 
     * @return 车辆id
     */
    public Long getCarId() {
        return this.carId;
    }
     
    /**
     * 设置车辆id
     * 
     * @param carId
     *          车辆id
     */
    public void setCarId(Long carId) {
        this.carId = carId;
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

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public Integer getIsRef() {
		return isRef;
	}

	public void setIsRef(Integer isRef) {
		this.isRef = isRef;
	}
}
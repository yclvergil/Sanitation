package com.songming.sanitation.user.model;
/******************************************************************
** 类    名：TCarMaintainDTO
** 描    述：车辆保养信息表
** 创 建 者：bianj
** 创建时间：2016-05-06 16:17:04
******************************************************************/

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 车辆保养信息表(T_CAR_MAINTAIN)
 * 
 * @author bianj
 * @version 1.0.0 2016-05-06
 */
public class TCarMaintainDto implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7707070122250851326L;
    
    /**  */
    private Long id;
    
    /** 车辆id/关联t_car表 */
    private Long carId;
    
    /** 保养时间 */
    private String maintainTime;
    
    /** 保养类型 */
    private Integer maintainType;
    
    /**
     * 保养项目
     */
    private Integer maintainProject;
    
    /** 维修原因 */
    private String reason;
    
    /** 公里数 */
    private Float km;
    
    /** 维修费用 */
    private Float price;
    
    /** 经办人 */
    private String managerBy;
    
    /** 备注 */
    private String remark;
    
    /** 下次保养时间 */
    private String maintainNextTime;
    
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
    
    private String carNo;
    
    private String carCode;
    
    private String carType;
    
    private String buyTime;
    
    /** 创建人机构id */
    private Long createOrgId;
    
    /** 创建人id */
    private Long createId;
    
    private List<Map<String, Long>> fileKeys;
    
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
     * 获取车辆id/关联t_car表
     * 
     * @return 车辆id/关联t_car表
     */
    public Long getCarId() {
        return this.carId;
    }
     
    /**
     * 设置车辆id/关联t_car表
     * 
     * @param carId
     *          车辆id/关联t_car表
     */
    public void setCarId(Long carId) {
        this.carId = carId;
    }
    
    /**
     * 获取保养时间
     * 
     * @return 保养时间
     */
    public String getMaintainTime() {
        return this.maintainTime;
    }
     
    /**
     * 设置保养时间
     * 
     * @param maintainTime
     *          保养时间
     */
    public void setMaintainTime(String maintainTime) {
        this.maintainTime = maintainTime;
    }
    
    /**
     * 获取保养类型
     * 
     * @return 保养类型
     */
    public Integer getMaintainType() {
        return this.maintainType;
    }
     
    /**
     * 设置保养类型
     * 
     * @param maintainType
     *          保养类型
     */
    public void setMaintainType(Integer maintainType) {
        this.maintainType = maintainType;
    }
    
    /**
     * 获取维修原因
     * 
     * @return 维修原因
     */
    public String getReason() {
        return this.reason;
    }
     
    /**
     * 设置维修原因
     * 
     * @param reason
     *          维修原因
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    /**
     * 获取公里数
     * 
     * @return 公里数
     */
    public Float getKm() {
        return this.km;
    }
     
    /**
     * 设置公里数
     * 
     * @param km
     *          公里数
     */
    public void setKm(Float km) {
        this.km = km;
    }
    
    /**
     * 获取维修费用
     * 
     * @return 维修费用
     */
    public Float getPrice() {
        return this.price;
    }
     
    /**
     * 设置维修费用
     * 
     * @param price
     *          维修费用
     */
    public void setPrice(Float price) {
        this.price = price;
    }
    
    /**
     * 获取经办人
     * 
     * @return 经办人
     */
    public String getManagerBy() {
        return this.managerBy;
    }
     
    /**
     * 设置经办人
     * 
     * @param managerBy
     *          经办人
     */
    public void setManagerBy(String managerBy) {
        this.managerBy = managerBy;
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
     * 获取下次保养时间
     * 
     * @return 下次保养时间
     */
    public String getMaintainNextTime() {
        return this.maintainNextTime;
    }
     
    /**
     * 设置下次保养时间
     * 
     * @param maintainNextTime
     *          下次保养时间
     */
    public void setMaintainNextTime(String maintainNextTime) {
        this.maintainNextTime = maintainNextTime;
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

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

	public Long getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(Long createOrgId) {
		this.createOrgId = createOrgId;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Integer getMaintainProject() {
		return maintainProject;
	}

	public void setMaintainProject(Integer maintainProject) {
		this.maintainProject = maintainProject;
	}

	public List<Map<String, Long>> getFileKeys() {
		return fileKeys;
	}

	public void setFileKeys(List<Map<String, Long>> fileKeys) {
		this.fileKeys = fileKeys;
	}
}
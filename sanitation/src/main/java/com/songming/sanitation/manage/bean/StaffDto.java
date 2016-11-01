package com.songming.sanitation.manage.bean;

import java.io.Serializable;
/******************************************************************
** 类    名：TNurseStaffDTO
** 描    述：护理人员信息表
** 创 建 者：bianj
** 创建时间：2016-04-19 14:04:40
******************************************************************/

/**
 * 护理人员信息表(T_NURSE_STAFF)
 * 
 * @author bianj
 * @version 1.0.0 2016-04-19
 */
public class StaffDto implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4707489496573674853L;
    
    /**  */
    private Long id;
    
    /** 账号ID，关联sys_user表 */
    private Integer userId;
    
    /** 姓名 */
    private String name;
    
    /** 性别，1：男，2：女 */
    private Integer gender;
    
    /** 年龄 */
    private Integer age;
    
    /** 籍贯 */
    private String origin;
    
    /** 入职时间 */
    private String entryTime;
    
    /** 身份证号码 */
    private String identityCard;
    
    /** 电话 */
    private String phone;
    
    /** 职位，关联sys_station表 */
    private Long stationId;
    
    private String stationName;
    
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
    
    /** 机构id */
    private Integer orgId;
    
    /** 驾驶证号码*/
    private String licenseNumbe;
    
    /** 证件有效期 */
    private String licenseExpiryTime;
    
    /** 准驾车型 */
    private String quasiDrivingType;
    
    private String searchParam;
    
    private String orgName;
    
    private Integer isSign;
    

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
     * 获取姓名
     * 
     * @return 姓名
     */
    public String getName() {
        return this.name;
    }
     
    /**
     * 设置姓名
     * 
     * @param name
     *          姓名
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取性别，1：男，2：女
     * 
     * @return 性别
     */
    public Integer getGender() {
        return this.gender;
    }
     
    /**
     * 设置性别，1：男，2：女
     * 
     * @param gender
     *          性别，1：男，2：女
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    
    /**
     * 获取年龄
     * 
     * @return 年龄
     */
    public Integer getAge() {
        return this.age;
    }
     
    /**
     * 设置年龄
     * 
     * @param age
     *          年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }
    
    /**
     * 获取籍贯
     * 
     * @return 籍贯
     */
    public String getOrigin() {
        return this.origin;
    }
     
    /**
     * 设置籍贯
     * 
     * @param origin
     *          籍贯
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    /**
     * 获取入职时间
     * 
     * @return 入职时间
     */
    public String getEntryTime() {
        return this.entryTime;
    }
     
    /**
     * 设置入职时间
     * 
     * @param entryTime
     *          入职时间
     */
    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }
    
    /**
     * 获取身份证号码
     * 
     * @return 身份证号码
     */
    public String getIdentityCard() {
        return this.identityCard;
    }
     
    /**
     * 设置身份证号码
     * 
     * @param identityCard
     *          身份证号码
     */
    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }
    
    /**
     * 获取电话
     * 
     * @return 电话
     */
    public String getPhone() {
        return this.phone;
    }
     
    /**
     * 设置电话
     * 
     * @param phone
     *          电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /**
     * 获取职位，关联sys_station表
     * 
     * @return 职位
     */
    public Long getStationId() {
        return this.stationId;
    }
     
    /**
     * 设置职位，关联sys_station表
     * 
     * @param position
     *          职位，关联sys_station表
     */
    public void setStationId(Long stationId) {
        this.stationId = stationId;
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

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getLicenseNumbe() {
		return licenseNumbe;
	}

	public void setLicenseNumbe(String licenseNumbe) {
		this.licenseNumbe = licenseNumbe;
	}

	public String getLicenseExpiryTime() {
		return licenseExpiryTime;
	}

	public void setLicenseExpiryTime(String licenseExpiryTime) {
		this.licenseExpiryTime = licenseExpiryTime;
	}

	public String getQuasiDrivingType() {
		return quasiDrivingType;
	}

	public void setQuasiDrivingType(String quasiDrivingType) {
		this.quasiDrivingType = quasiDrivingType;
	}

	public String getSearchParam() {
		return searchParam;
	}

	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}
}
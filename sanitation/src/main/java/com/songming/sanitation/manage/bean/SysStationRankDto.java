package com.songming.sanitation.manage.bean;

import java.io.Serializable;
/******************************************************************
** 类    名：SysStationRankDTO
** 描    述：职位职级表
** 创 建 者：bianj
** 创建时间：2016-08-10 11:52:05
******************************************************************/



/**
 * 职位职级表(SYS_STATION_RANK)
 * 
 * @author bianj
 * @version 1.0.0 2016-08-10
 */
public class SysStationRankDto implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 7875677365767373717L;
    
    /**  */
    private Long id;
    
    /** 员工id */
    private Long staffId;
    
    /** 上级员工id */
    private Long staffPid;
    
    /** 创建人id */
    private Long createId;
    
    /** 创建人机构id */
    private Integer createOrgId;
    
    /** 创建时间 */
    private String createDate;
    
    /** 排序码 */
    private Integer orderNo;
    
    private String staffName;
    
    private String staffPname;
    
    private Integer orgId;
    
    private String summaryText;
    
    private Long stationId;
    
    private int isSign;
    
    private String stationName;
    
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
     * 获取上级员工id
     * 
     * @return 上级员工id
     */
    public Long getStaffPid() {
        return this.staffPid;
    }
     
    /**
     * 设置上级员工id
     * 
     * @param staffPid
     *          上级员工id
     */
    public void setStaffPid(Long staffPid) {
        this.staffPid = staffPid;
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
     * 获取创建人机构id
     * 
     * @return 创建人机构id
     */
    public Integer getCreateOrgId() {
        return this.createOrgId;
    }
     
    /**
     * 设置创建人机构id
     * 
     * @param createOrgId
     *          创建人机构id
     */
    public void setCreateOrgId(Integer createOrgId) {
        this.createOrgId = createOrgId;
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
     * 获取排序码
     * 
     * @return 排序码
     */
    public Integer getOrderNo() {
        return this.orderNo;
    }
     
    /**
     * 设置排序码
     * 
     * @param orderNo
     *          排序码
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffPname() {
		return staffPname;
	}

	public void setStaffPname(String staffPname) {
		this.staffPname = staffPname;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getSummaryText() {
		return summaryText;
	}

	public void setSummaryText(String summaryText) {
		this.summaryText = summaryText;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public int getIsSign() {
		return isSign;
	}

	public void setIsSign(int isSign) {
		this.isSign = isSign;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	
	
}
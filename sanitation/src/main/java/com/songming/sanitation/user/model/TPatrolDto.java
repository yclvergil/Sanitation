package com.songming.sanitation.user.model;
/******************************************************************
** 类    名：TPatrolDTO
** 描    述：巡查信息表
** 创 建 者：bianj
** 创建时间：2016-05-06 16:17:04
******************************************************************/

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 巡查信息表(T_PATROL)
 * 
 * @author bianj
 * @version 1.0.0 2016-05-06
 */
public class TPatrolDto implements Serializable{
    /** 版本号 */
    private static final long serialVersionUID = 3095890195820288964L;
    
    /**  */
    private Long id;
    
    /** 巡查内容 */
    private String content;
    
    /**
     * 上传文件的id集合
     */
    private List<Map<String, Long>> fileKeys;
    
    /** 巡查人 */
    private Long createId;
    
    /** 巡查人所属部门id */
    private Integer createOrgId;
    
    /** 创建人 */
    private String createBy;
    
    /** 创建日期 */
    private String createDate;
    
    /** 更新人 */
    private String lastUpdateBy;
    
    /** 更新日期 */
    private String lastUpdateDate;
    
    /** 删除标示，0：未删除，1：已删除 */
    private Integer deleteFlag;
    
    private String staffName;
    
    private String orgName;
    
    private String stationName;
    
    private String address;
    
    private List<TUploadFileDto> files;
    
	public List<TUploadFileDto> getFiles() {
		return files;
	}

	public void setFiles(List<TUploadFileDto> files) {
		this.files = files;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
     * 获取巡查内容
     * 
     * @return 巡查内容
     */
    public String getContent() {
        return this.content;
    }
     
    /**
     * 设置巡查内容
     * 
     * @param content
     *          巡查内容
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * 获取巡查人
     * 
     * @return 巡查人
     */
    public Long getCreateId() {
        return this.createId;
    }
     
    /**
     * 设置巡查人
     * 
     * @param createId
     *          巡查人
     */
    public void setCreateId(Long createId) {
        this.createId = createId;
    }
    
    /**
     * 获取巡查人所属部门id
     * 
     * @return 巡查人所属部门id
     */
    public Integer getCreateOrgId() {
        return this.createOrgId;
    }
     
    /**
     * 设置巡查人所属部门id
     * 
     * @param createOrgId
     *          巡查人所属部门id
     */
    public void setCreateOrgId(Integer createOrgId) {
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

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public List<Map<String, Long>> getFileKeys() {
		return fileKeys;
	}

	public void setFileKeys(List<Map<String, Long>> fileKeys) {
		this.fileKeys = fileKeys;
	}
}
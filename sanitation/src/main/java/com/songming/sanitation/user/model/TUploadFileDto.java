package com.songming.sanitation.user.model;

import java.io.Serializable;
/******************************************************************
** 类    名：TUploadFileDTO
** 描    述：文件上传信息表
** 创 建 者：bianj
** 创建时间：2016-05-06 16:17:04
******************************************************************/

/**
 * 文件上传信息表(T_UPLOAD_FILE)
 * 
 * @author bianj
 * @version 1.0.0 2016-05-06
 */
public class TUploadFileDto implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -231428809087887976L;
    
    /**  */
    private Long id;
    
    /**  */
    private String fileName;
    
    private String dirName;
    
    /**  */
    private String filePath;
    
    /** 文件类型/1：事件管理文件，2：巡查管理文件 */
    private Integer fileType;
    
    /** 业务数据id，根据file_type区分 */
    private Long businesId;
    
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
     * 获取
     * 
     * @return 
     */
    public String getFileName() {
        return this.fileName;
    }
     
    /**
     * 设置
     * 
     * @param fileName
     *          
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getFilePath() {
        return this.filePath;
    }
     
    /**
     * 设置
     * 
     * @param filePath
     *          
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    /**
     * 获取文件类型/1：事件管理文件
     * 
     * @return 文件类型/1
     */
    public Integer getFileType() {
        return this.fileType;
    }
     
    /**
     * 设置文件类型/1：事件管理文件
     * 
     * @param fileType
     *          文件类型/1：事件管理文件
     */
    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }
    
    /**
     * 获取业务数据id，根据file_type区分
     * 
     * @return 业务数据id
     */
    public Long getBusinesId() {
        return this.businesId;
    }
     
    /**
     * 设置业务数据id，根据file_type区分
     * 
     * @param businesId
     *          业务数据id，根据file_type区分
     */
    public void setBusinesId(Long businesId) {
        this.businesId = businesId;
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

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

}
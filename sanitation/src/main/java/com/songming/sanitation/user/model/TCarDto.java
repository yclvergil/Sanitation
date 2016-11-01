package com.songming.sanitation.user.model;

import java.io.Serializable;

/******************************************************************
 ** 类    名：TCarDTO
 ** 描    述：车辆信息表
 ** 创 建 者：bianj
 ** 创建时间：2016-05-06 16:17:04
 ******************************************************************/

/**
 * 车辆信息表(T_CAR)
 * 
 * @author bianj
 * @version 1.0.0 2016-05-06
 */
public class TCarDto implements Serializable {
	/** 版本号 */
	private static final long serialVersionUID = 7607648321630681372L;

	/**  */
	private Long id;

	/** 员工id/关联sys_staff表 */
	private Long staffId;

	/** 车牌号码 */
	private String carNo;

	/** 车辆编号 */
	private String carCode;

	/** 车辆类型/1：扫路王 2：压缩车 3：洒水车 4：小勾臂车 5：大勾臂车 6：吸污车 */
	private Integer carType;

	/** 车辆类型名 */
	private String carTypeName;

	/** 行驶公里数 */
	private Float carKm;

	/** 购买时间 */
	private String buyTime;

	/** 每公里油耗 */
	private Float oilKm;

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

	private String name;

	private String licenseNumbe;

	private String licenseExpiryTime;

	private String phone;

	/** 创建人机构id */
	private Long createOrgId;

	/** 创建人id */
	private Long createId;

	private String signAddress;

	/**
	 * 获取车辆类型/1：扫路王 2：压缩车 3：洒水车 4：小勾臂车 5：大勾臂车 6：吸污车
	 * 
	 */
	private String dictLabel;

	/**
	 * 更具返回的int类型判断车辆类别，维修项目，保养类别
	 */
	private Integer sortNum;

	private long maintainType;
	
	
	private long price;
	private String managerBy;
	
	
	
	/** 经办人*/
	public String getManagerBy() {
		return managerBy;
	}

	public void setManagerBy(String managerBy) {
		this.managerBy = managerBy;
	}

	private String maintainTime;
	/** 开始保养时间*/
	public String getMaintainTime() {
		return maintainTime;
	}

	public void setMaintainTime(String maintainTime) {
		this.maintainTime = maintainTime;
	}

	/** 保养费用*/
	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	/** 车辆保养类型 1：维修，3：保养，4：加油 */
	public long getMaintainType() {
		return maintainType;
	}

	public void setMaintainType(long maintainType) {
		this.maintainType = maintainType;
	}

	/**
	 * 更具返回的int类型判断车辆类别，维修项目，保养类别
	 */
	public Integer getSortNum() {
		return sortNum;
	}

	/**
	 * 更具返回的int类型判断车辆类别，维修项目，保养类别
	 */
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	/**
	 * 获取车辆类型/1：扫路王 2：压缩车 3：洒水车 4：小勾臂车 5：大勾臂车 6：吸污车
	 * 
	 */
	public String getDictLabel() {
		return dictLabel;
	}

	/**
	 * 获取车辆类型/1：扫路王 2：压缩车 3：洒水车 4：小勾臂车 5：大勾臂车 6：吸污车
	 * 
	 */
	public void setDictLabel(String dictLabel) {
		this.dictLabel = dictLabel;
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
	 *            员工id/关联sys_staff表
	 */
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	/**
	 * 获取车牌号码
	 * 
	 * @return 车牌号码
	 */
	public String getCarNo() {
		return this.carNo;
	}

	/**
	 * 设置车牌号码
	 * 
	 * @param carNo
	 *            车牌号码
	 */
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	/**
	 * 获取车辆编号
	 * 
	 * @return 车辆编号
	 */
	public String getCarCode() {
		return this.carCode;
	}

	/**
	 * 设置车辆编号
	 * 
	 * @param carCode
	 *            车辆编号
	 */
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	/**
	 * 获取车辆类型/1：扫路王 2：压缩车 3：洒水车 4：小勾臂车 5：大勾臂车 6：吸污车
	 * 
	 * @return 车辆类型/1
	 */
	public Integer getCarType() {
		return this.carType;
	}

	/**
	 * 设置车辆类型/1：扫路王 2：压缩车 3：洒水车 4：小勾臂车 5：大勾臂车 6：吸污车
	 * 
	 * @param carType
	 *            车辆类型/1：扫路王 2：压缩车 3：洒水车 4：小勾臂车 5：大勾臂车 6：吸污车
	 */
	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	/**
	 * 获取行驶公里数
	 * 
	 * @return 行驶公里数
	 */
	public Float getCarKm() {
		return this.carKm;
	}

	/**
	 * 设置行驶公里数
	 * 
	 * @param carKm
	 *            行驶公里数
	 */
	public void setCarKm(Float carKm) {
		this.carKm = carKm;
	}

	/**
	 * 获取购买时间
	 * 
	 * @return 购买时间
	 */
	public String getBuyTime() {
		return this.buyTime;
	}

	/**
	 * 设置购买时间
	 * 
	 * @param buyTime
	 *            购买时间
	 */
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

	/**
	 * 获取每公里油耗
	 * 
	 * @return 每公里油耗
	 */
	public Float getOilKm() {
		return this.oilKm;
	}

	/**
	 * 设置每公里油耗
	 * 
	 * @param oilKm
	 *            每公里油耗
	 */
	public void setOilKm(Float oilKm) {
		this.oilKm = oilKm;
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
	 *            创建人
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
	 *            创建时间
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
	 *            更新人
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
	 *            更新时间
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
	 *            删除标示，0：未删除，1：已删除
	 */
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getSignAddress() {
		return signAddress;
	}

	public void setSignAddress(String signAddress) {
		this.signAddress = signAddress;
	}

	public String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}
}
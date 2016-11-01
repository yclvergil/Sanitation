package com.songming.sanitation.map.model;

import java.io.Serializable;

public class MapFragmentListModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// "carId":"10",
	// "tripTimes":1
	// ,"batch":"20160128031"
	// ,"userId":"20e3183b-d19b-4139-943c-7dc43fb5b3aa"
	// ,"carNumber":"湘A.A0A10",
	// "diliveryTime":"2016-01-28 16:55:13"
	// ,"stationtimes":5,
	// "type":"1",
	// "statusnum":0
	private String carId;
	private int tripTimes;
	private String userId;
	private String batch;
	private String carNumber;
	private String diliveryTime;
	private int stationtimes;
	private String type;
	private int statusnum;
	private String totalDistances;// 公里
	private String totalTimes;// 时间
	private String distriType;// 中餐
	private String distriExplain;
	private String auditStatus;// 审核状态

	public String getTotalDistances() {
		return totalDistances;
	}

	public void setTotalDistances(String totalDistances) {
		this.totalDistances = totalDistances;
	}

	public String getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(String totalTimes) {
		this.totalTimes = totalTimes;
	}

	public String getDistriType() {
		return distriType;
	}

	public void setDistriType(String distriType) {
		this.distriType = distriType;
	}

	public String getDistriExplain() {
		return distriExplain;
	}

	public void setDistriExplain(String distriExplain) {
		this.distriExplain = distriExplain;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public int getTripTimes() {
		return tripTimes;
	}

	public void setTripTimes(int tripTimes) {
		this.tripTimes = tripTimes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getDiliveryTime() {
		return diliveryTime;
	}

	public void setDiliveryTime(String diliveryTime) {
		this.diliveryTime = diliveryTime;
	}

	public int getStationtimes() {
		return stationtimes;
	}

	public void setStationtimes(int stationtimes) {
		this.stationtimes = stationtimes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStatusnum() {
		return statusnum;
	}

	public void setStatusnum(int statusnum) {
		this.statusnum = statusnum;
	}

}

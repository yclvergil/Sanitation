package com.songming.sanitation.map.model;

import java.io.Serializable;

public class LineItemModel implements Serializable {
	// {"stationuserphone":"13999996666",
	// "stationCode":"100000",
	// "stationusername":"张无忌",
	// "status":"0",
	// "carDisId":"1cf53d9d-d624-4c66-a1a8-0bc20f41cfb3"
	// ,"diliveryOrder":0,"" +
	// "stationLatitude":28.226059,
	// "stationId":"940F6004-4C14-42E6-B5FD-8749BEAB89E4",
	// "stationName":"湖南松鸣科技服务有限公司",
	// "stationLongtitude":112.908148,
	// "stationAddress":"湖南省长沙市岳麓区汇达路68号航天亚卫科技园",
	// "goodstotalcount":116.0
	// }

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String stationuserphone;
	private String stationCode;
	private String stationusername;
	private String status;
	private String carDisId;
	private String diliveryOrder;
	private String stationLatitude;
	private String stationLongtitude;
	private String stationId;
	private String stationName;
	private String stationAddress;
	private String goodstotalcount;

	private String marker_num;

	public String getMarker_num() {
		return marker_num;
	}

	public void setMarker_num(String marker_num) {
		this.marker_num = marker_num;
	}

	public String getStationuserphone() {
		return stationuserphone;
	}

	public void setStationuserphone(String stationuserphone) {
		this.stationuserphone = stationuserphone;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getStationusername() {
		return stationusername;
	}

	public void setStationusername(String stationusername) {
		this.stationusername = stationusername;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCarDisId() {
		return carDisId;
	}

	public void setCarDisId(String carDisId) {
		this.carDisId = carDisId;
	}

	public String getDiliveryOrder() {
		return diliveryOrder;
	}

	public void setDiliveryOrder(String diliveryOrder) {
		this.diliveryOrder = diliveryOrder;
	}

	public String getStationLatitude() {
		return stationLatitude;
	}

	public void setStationLatitude(String stationLatitude) {
		this.stationLatitude = stationLatitude;
	}

	public String getStationLongtitude() {
		return stationLongtitude;
	}

	public void setStationLongtitude(String stationLongtitude) {
		this.stationLongtitude = stationLongtitude;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationAddress() {
		return stationAddress;
	}

	public void setStationAddress(String stationAddress) {
		this.stationAddress = stationAddress;
	}

	public String getGoodstotalcount() {
		return goodstotalcount;
	}

	public void setGoodstotalcount(String goodstotalcount) {
		this.goodstotalcount = goodstotalcount;
	}

}

package com.songming.sanitation.map.model;

/**
 * 天气
 * @author zyj
 *
 */
public class WeatherInfo {
	
	private String errNum;
	private String errMsg;
	private WeatherBean retData;
	public String getErrNum() {
		return errNum;
	}
	public void setErrNum(String errNum) {
		this.errNum = errNum;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public WeatherBean getRetData() {
		return retData;
	}
	public void setRetData(WeatherBean retData) {
		this.retData = retData;
	}
	
}

package com.songming.sanitation.frameset;

import java.io.Serializable;


/**
 * 功能描述：封装请求标识对象
 * @see BaseActivity#requestHttp(org.json.JSONObject, Object, String, String, boolean)
 * @since 1.0 2014-4.10
 * 
 * */
public class VolleyRequestVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 352866521281562684L;
	
	//请求标识tag
	private String requestTag;
	
	//请求url
	private String requestUrl;
	
	//请求状态标识码
	private int requestCode;
	
	//是否读写缓存标识
	private boolean isreadCache=false;
	
	//是否缓存
	private boolean isCache=true;
	
	
	
	public VolleyRequestVo() {
		super();
		
	}
	
	
	public VolleyRequestVo(String requestTag) {
		super();
		this.requestTag = requestTag;
	}
	
	
	public boolean isIsreadCache() {
		return isreadCache;
	}
	
	
	public void setIsreadCache(boolean isreadCache) {
		this.isreadCache = isreadCache;
	}
	
	
	public String getRequestTag() {
		return requestTag;
	}
	
	
	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}
	
	
	public String getRequestUrl() {
		return requestUrl;
	}
	
	
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	
	public int getRequestCode() {
		return requestCode;
	}
	
	
	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}


	public boolean isCache() {
		return isCache;
	}


	public void setCache(boolean isCache) {
		this.isCache = isCache;
	}
	
	

}

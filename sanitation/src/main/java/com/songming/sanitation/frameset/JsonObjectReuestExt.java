package com.songming.sanitation.frameset;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

	/**
	 * 描述：扩展volley框架的JsonObjectRequest,提供设置请求连接超时时间
	 * @since 1.0 
	 * */
  public class JsonObjectReuestExt extends JsonObjectRequest {
	  
	int postTimeout = 15000;
	
	public JsonObjectReuestExt(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		
		super(method, url, jsonRequest, listener, errorListener);
		
	}
	
	

	public JsonObjectReuestExt(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener,
			Map<String, String> map) {
		
		super(method, url, jsonRequest, listener, errorListener, map);
		
	}



	public JsonObjectReuestExt(String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener,
			Map<String, String> map) {
		
		super(url, jsonRequest, listener, errorListener, map);
		
	}
	


	public JsonObjectReuestExt(String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		
		super(url, jsonRequest, listener, errorListener);
		
	}



	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		
		if(ApplicationExt.getApplication().cookie != null 
				&& ApplicationExt.getApplication().cookie.length() > 0){
			
			HashMap<String, String>	headers = new HashMap<String, String>();
			headers.put("Cookie", ApplicationExt.getApplication().cookie);
			//headers.put("Charset", "UTF-8");  
			//headers.put("Content-Type", "application/x-javascript");  
			//headers.put("Accept-Encoding", "gzip,deflate");  
			return headers;
		}
		
		return super.getHeaders();
	}

	//设置超时时间
	
	@Override
	public RetryPolicy getRetryPolicy() {  
		//DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
		/*RetryPolicy retryPolicy = new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 2.0f); */ 
		
		RetryPolicy retryPolicy = new DefaultRetryPolicy(postTimeout,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 1.0f); 
		return retryPolicy;  
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		
		Response<JSONObject> superResponse = super.parseNetworkResponse(response);  
	    Map<String, String> responseHeaders = response.headers;  
	    String rawCookies = responseHeaders.get("Set-Cookie");  
	    //服务端返回是 Set-cookie:JSESSIONID=D90B58454550B4D37C4B66A76BF27B93; Path=/otn BIGipServerotn=2564030730.64545.0000; path=/  
	    String part1 = substring(rawCookies, "", ";");  
	    String part2 = substring(rawCookies, "\n", ";");  
	    //客户端需要的是 cookie:JSESSIONID=D90B58454550B4D37C4B66A76BF27B93; BIGipServerotn=2564030730.64545.0000;  
	    ApplicationExt.getApplication().cookie = part1 + "; " + part2 + ";";  
	    return superResponse;  
	}
	
	
	protected  String substring(String src, String fromString,
			String toString) {
		if(src==null)return "";
		int fromPos = 0;
		if (fromString != null && fromString.length() > 0) {
			fromPos = src.indexOf(fromString);
			fromPos += fromString.length();
		}
		int toPos = src.indexOf(toString, fromPos);
		
		return src.substring(fromPos, toPos);
	}

}

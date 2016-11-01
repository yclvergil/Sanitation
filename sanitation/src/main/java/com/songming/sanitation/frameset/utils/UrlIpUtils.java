package com.songming.sanitation.frameset.utils;

import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * description 
 * @version V1.0  createTime：2014年5月20日 下午4:30:53 
 */

public class UrlIpUtils {
	
	/**IP的正则表达式*/
	private static final String IP_PATTERN="^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	
	public static boolean validIp(final String ip){          
	      Pattern pattern = Pattern.compile(IP_PATTERN);
	      Matcher matcher = pattern.matcher(ip);
	      return matcher.matches();             
	}
	
	public static boolean vlaidUrl(final String url){
		try {
		    new java.net.URI(url);
		    return true;
		} catch (URISyntaxException e) {
			return false;
		}
	}
	
	

}

package com.songming.sanitation.frameset.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * description
 * 
 * @version V1.0 createTime：2014年5月20日 下午4:30:53
 */
public class SharedPreferencesUtils {

	public static final String SHAREDPREDB_NAME = "smscapp";

	// ip地址key
	public static final String IP_ADDR_KEY = "ipaddr";

	// ip 端口
	public static final String IP_PORT_KEY = "port";

	// 锁屏新密码
	public static final String NEW_PASSWORD = "newPwd";

	// 锁屏重复密码
	public static final String REPEAT_PASSWORD = "repeatPwd";

	// 缓存大小
	public static final String CACHE_SIZE = "cacheSize";

	// 缓存大小
	public static final String CACHE_SIZE_VOLLEY = "volley";

	// 缓存时间
	public static final String CACHE_DATE = "cacheDate";

	public static final String LOCK_SCREEN_TOGGLE = "1";

	/** 登录名 */
	public static final String LOGIN_NAME = "loginName";
	/** 登录密码 */
	public static final String USERPASSWORD = "userPassword";
	/** 姓名 */
	public static final String USERNAME = "userName";
	/** 职称 */
	public static final String STATIONNAME = "stationName";
	/** 职权id */
	public static final String STATIONID = "stationId";
	/** 用户id */
	public static final String USER_ID = "userId";
	/** 员工id */
	public static final String STAFF_ID = "staffId";
	/** 机构id */
	public static final String ORG_ID = "orgId";
	/** 用户手机号码 */
	public static final String PHONE = "phoneNumber";

	public static final String USERHEAD = "userHead";

	public static String getStringValue(Context ctx, String key,
			String defaultValue) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);
		return sp.getString(key, defaultValue);

	}

	public static void setStringValue(Context ctx, String key, String value) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);
		Editor ed = sp.edit();
		ed.putString(key, value);
		ed.commit();
	}

	public static int getIntValue(Context ctx, String key, int defaultValue) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}

	public static void setIntValue(Context ctx, String key, int value) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);
		Editor ed = sp.edit();
		ed.putInt(key, value);
		ed.commit();
	}

	public static long getLongValue(Context ctx, String key, long defaultValue) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);
		return sp.getLong(key, defaultValue);
	}

	public static void setLongValue(Context ctx, String key, long value) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);
		Editor ed = sp.edit();
		ed.putLong(key, value);
		ed.commit();
	}

	public static boolean getBooleanValue(Context ctx, String key,
			boolean defaultValue) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);
		return sp.getBoolean(key, defaultValue);
	}

	public static void setBooleanValue(Context ctx, String key, boolean value) {

		SharedPreferences sp = ctx.getSharedPreferences(SHAREDPREDB_NAME,
				ctx.MODE_PRIVATE);
		Editor ed = sp.edit();
		ed.putBoolean(key, value);
		ed.commit();
	}

}

package com.songming.sanitation.frameset.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;

public class WifiNetUtils {
	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;
	
	
	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	
	
	/**
	 * 获取当前网络类型
	 * 
	 * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
	 */
	public static int getNetworkType(Context context) {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (!StringUtilsExt.isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}

	/**
	 * 检查GPS是否打开
	 * @param context
	 * @return
	 */
	public static boolean isOPen(final Context context) {  
        LocationManager locationManager   
                                 = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）  
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);  
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）  
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);  
        if (gps || network) {  
            return true;  
        }  
  
        return false;  
    } 
	
	 /** 
     * 强制帮用户打开GPS 
     * @param context 
     */  
    public static void openGPS(Context context) {  
        Intent GPSIntent = new Intent();  
        GPSIntent.setClassName("com.android.settings",  
                "com.android.settings.widget.SettingsAppWidgetProvider");  
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");  
        GPSIntent.setData(Uri.parse("custom:3"));  
        try {  
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();  
        } catch (CanceledException e) {  
            e.printStackTrace();  
        }  
    }  
    
    /** 
     * 强制帮用户打开wifi 
     * @param context 
     */  
    public static void openWifi(Context context) {  
    	WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    	
    	if(!wifiManager.isWifiEnabled()){
    		wifiManager.setWifiEnabled(true);
    	}
    	
    }
    
    /**
     * 强制帮用户打开移动网络 ---好像有点问题，慎用！
     * @param context
     * @param enabled
     */
    public static void toggleMobileData(Context context, boolean enabled) {    
        ConnectivityManager connectivityManager =   
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
          
        //ConnectivityManager类    
        Class<?> connectivityManagerClass = null;  
        //ConnectivityManager类中的字段    
        Field connectivityManagerField = null;  
          
          
        //IConnectivityManager接口  
        Class<?> iConnectivityManagerClass = null;  
        //IConnectivityManager接口的对象  
        Object iConnectivityManagerObject = null;  
        //IConnectivityManager接口的对象的方法  
        Method setMobileDataEnabledMethod = null;  
          
        try {  
            //取得ConnectivityManager类  
            connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());  
            //取得ConnectivityManager类中的字段mService  
            connectivityManagerField = connectivityManagerClass.getDeclaredField("mService");  
            //取消访问私有字段的合法性检查   
            //该方法来自java.lang.reflect.AccessibleObject  
            connectivityManagerField.setAccessible(true);  
              
              
            //实例化mService  
            //该get()方法来自java.lang.reflect.Field  
            //一定要注意该get()方法的参数:  
            //它是mService所属类的对象  
            //完整例子请参见:  
            //http://blog.csdn.net/lfdfhl/article/details/13509839  
            iConnectivityManagerObject = connectivityManagerField.get(connectivityManager);  
            //得到mService所属接口的Class  
            iConnectivityManagerClass = Class.forName(iConnectivityManagerObject.getClass().getName());  
            //取得IConnectivityManager接口中的setMobileDataEnabled(boolean)方法  
            //该方法来自java.lang.Class.getDeclaredMethod  
            setMobileDataEnabledMethod =   
            iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);  
            //取消访问私有方法的合法性检查   
            //该方法来自java.lang.reflect.AccessibleObject  
            setMobileDataEnabledMethod.setAccessible(true);  
            //调用setMobileDataEnabled方法  
            setMobileDataEnabledMethod.invoke(iConnectivityManagerObject,enabled);  
        } catch (ClassNotFoundException e) {     
            e.printStackTrace();    
        } catch (NoSuchFieldException e) {     
            e.printStackTrace();    
        } catch (SecurityException e) {     
            e.printStackTrace();    
        } catch (NoSuchMethodException e) {     
            e.printStackTrace();    
        } catch (IllegalArgumentException e) {     
            e.printStackTrace();    
        } catch (IllegalAccessException e) {     
            e.printStackTrace();    
        } catch (InvocationTargetException e) {     
            e.printStackTrace();    
        }   
    }  
}

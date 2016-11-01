package com.songming.sanitation.map.tool;

import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.songming.sanitation.frameset.ApplicationExt;
import com.songming.sanitation.frameset.utils.ToastUtil;
import com.songming.sanitation.frameset.utils.WifiNetUtils;

/**
 * description 百度定位地理位置信息工具类
 * 
 * @author Liudong
 * @version V1.0 createTime：2014年6月4日 下午4:21:22
 */

public class BdMapLocationUtils {

	private static BdMapLocationUtils single = null;

	private GeoCoder mMKSearch = null;

	// 定位客户端
	private LocationClient mLocationClient = null;

	// 百度定位结果回调
	private MyLocationListener myListener = new MyLocationListener();

	// 定位成功返回信息的回调
	private BdLocationSuccessListenner listenner = null;

	public static interface BdLocationSuccessListenner {
		public void locationResult(double _latitude, double _longitude,
				String _city, String _locationAddr, String _locationType);
	}

	public BdMapLocationUtils() {
		if (mLocationClient == null)
			initClient();
	}

	// 静态工厂方法
	public synchronized static BdMapLocationUtils getInstance() {
		if (single == null) {
			single = new BdMapLocationUtils();
		}

		return single;
	}

	public void initClient() {
		// 声明LocationClient类
		mLocationClient = new LocationClient(ApplicationExt.getApplication());
		initLoctaionOpt();
	}

	// 初始化定位参数
	public void initLoctaionOpt() {

		LocationClientOption option = new LocationClientOption();
		// 设置定位模式
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setOpenGps(true);// 打开gps
		// 返回的定位结果是百度经纬度，默认值gcj02
		option.setCoorType("bd09ll");
		// 设置发起定位请求的间隔时间为5000ms
		option.setScanSpan(5000);
		// option.setScanType(5000);
		// 返回的定位结果包含地址信息
		option.setIsNeedAddress(true);
		// 返回的定位结果包含手机机头的方向
		option.setNeedDeviceDirect(true);
		// 设置定位超时上限15秒
		option.setTimeOut(15000);
		mLocationClient.setLocOption(option);
		// 注册监听函数
		mLocationClient.registerLocationListener(myListener);
	}

	/**
	 * 启动百度定位
	 * 
	 * @param
	 * */
	public void startLocation(BdLocationSuccessListenner listenner) {

		stopLocation();
		
		// 首先检测下网络是否连接
		if (!WifiNetUtils.isNetworkConnected(ApplicationExt.getApplication())) {
			ToastUtil.makeText(ApplicationExt.getApplication(), "无网络连接!",
					Toast.LENGTH_SHORT);
			return;
		}

		this.listenner = listenner;

		if (mLocationClient == null)
			initClient();

		if (!mLocationClient.isStarted())
			mLocationClient.start();

		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.requestLocation();
		}/*
		 * else { Toast.makeText(ApplicationExt.getApplication(),
		 * "locClient is null or not started", Toast.LENGTH_SHORT) .show();
		 * return; }
		 */
	}

	public void stopLocation() {

		if (mLocationClient != null) {
			mLocationClient.stop();
			mLocationClient = null;
		}
		// MKSearch 对象在不使用时需执行销毁函数.
		if (mMKSearch != null)
			mMKSearch.destroy();

	}

	private class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			/*
			 * StringBuffer sb = new StringBuffer(256); sb.append("time : ");
			 * sb.append(location.getTime()); sb.append("\nerror code : ");
			 * sb.append(location.getLocType()); sb.append("\nlatitude : ");
			 * sb.append(location.getLatitude()); sb.append("\nlontitude : ");
			 * sb.append(location.getLongitude()); sb.append("\nradius : ");
			 * sb.append(location.getRadius());
			 */
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				/*
				 * sb.append("\nspeed : "); sb.append(location.getSpeed());
				 * sb.append("\nsatellite : ");
				 * sb.append(location.getSatelliteNumber());
				 */

				if (mMKSearch == null)
					mMKSearch = GeoCoder.newInstance();

				// 注意，MKSearchListener只支持一个，以最后一次设置为准
				mMKSearch.setOnGetGeoCodeResultListener(new MySearchListener());

				// 逆地址解析

				LatLng ptCenter = new LatLng(location.getLatitude(),
						location.getLongitude());
				// 反Geo搜索
				mMKSearch.reverseGeoCode(new ReverseGeoCodeOption()
						.location(ptCenter));

				// mMKSearch.geocode(key, city);//地址解析

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				/*
				 * sb.append("\naddr : "); sb.append(location.getAddrStr());
				 */

				if (listenner != null)
					listenner.locationResult(location.getLatitude(),
							location.getLongitude(), location.getCity(),
							location.getAddrStr(), "TypeNetWorkLocation");

				stopLocation();
			}

			// logMsg(sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			// 将在下个版本中去除poi功能
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}

			// logMsg(sb.toString());
		}
	}

	private class MySearchListener implements OnGetGeoCoderResultListener {

		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {

			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				ToastUtil.makeText(ApplicationExt.getApplication(),
						"抱歉，未能找到结果", Toast.LENGTH_LONG);
			}

		}

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				ToastUtil.makeText(ApplicationExt.getApplication(),
						"抱歉，未能找到结果", Toast.LENGTH_LONG);
			}
			if (listenner != null) {
				listenner.locationResult(result.getLocation().latitude,
						result.getLocation().longitude,
						result.getAddressDetail().city, result.getAddress(),
						"TypeGPSLocation");
				stopLocation();
			}
		}
	}
}

package com.songming.sanitation.manage;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.map.service.MyLocationService;
import com.songming.sanitation.map.tool.BdMapLocationUtils;
import com.songming.sanitation.map.tool.BdMapLocationUtils.BdLocationSuccessListenner;

public class RecordLineActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private TextView startrecord;// 签到
	private TextView finishrecord;// 签退

	private MapView mMapView; // MapView 是地图主控件
	private static BaiduMap mBaiduMap;
	private static Marker myMarker;
	private LatLng llE;// 定位个人位置经纬度
	private BitmapDescriptor bdE = BitmapDescriptorFactory
			.fromResource(R.drawable.recordline03);// 起点
	private static BitmapDescriptor bdEnd = BitmapDescriptorFactory
			.fromResource(R.drawable.recordline04);// 终点

	private long carId;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		setContentView(R.layout.recordline);
		carId = getIntent().getLongExtra("carId", -1);
		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		startrecord = (TextView) findViewById(R.id.startrecord);
		finishrecord = (TextView) findViewById(R.id.finishrecord);

		initMap();
	}

	@Override
	protected void initViews() {

		topTitle.setText("车辆轨迹上报");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		startrecord.setOnClickListener(this);
		finishrecord.setOnClickListener(this);

		if (isMyServiceRunning(MyLocationService.class)) {
			startrecord.setBackgroundResource(R.drawable.recordline02);
			finishrecord.setBackgroundResource(R.drawable.recordline01);
		}
		Log.d("aa", "Is my service live ? "
				+ isMyServiceRunning(MyLocationService.class));

		getCurrentPosition();

	}

	private void initMap() {
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setKeepScreenOn(true);
		mMapView.removeViewAt(2);// 移除百度地图右下角缩放图标
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setTrafficEnabled(false);
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);// 设置地图类型
	}

	/**
	 * 判断我的服务是否在运行
	 * 
	 * @param serviceClass
	 * @return
	 */
	private boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 以动画方式移动marker到地图中心
	 */
	public static void moveMapCenter(LatLng latl) {
		if (mBaiduMap == null) {
			return;
		}
		// 定义地图状态
		MapStatus mMapStatus = new MapStatus.Builder().target(latl).zoom(18)
				.build();

		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// 设置地图以动画方式切换
		mBaiduMap.animateMapStatus(mMapStatusUpdate);
	}

	/**
	 * 在地图标记我的位置
	 */
	public static void initMyLoaction(LatLng llE) {
		if (mBaiduMap == null) {
			return;
		}
		if (myMarker != null) {
			// 隐藏marker
			myMarker.setVisible(false);
		}
		OverlayOptions ooE = new MarkerOptions().position(llE).icon(bdEnd)
				.perspective(false);
		myMarker = (Marker) mBaiduMap.addOverlay(ooE);
	}

	/**
	 * 获取客户端当前的位置 坐标
	 */
	private void getCurrentPosition() {
		// 获取客户端当前的位置 坐标
		BdMapLocationUtils.getInstance().startLocation(
				new BdLocationSuccessListenner() {

					@Override
					public void locationResult(double _latitude,
							double _longitude, String _city,
							String _locationAddr, String _locationType) {
						llE = new LatLng(_latitude, _longitude);

						if (mBaiduMap != null) {
							mBaiduMap.clear();
						}
						// 设置地图以动画方式切换,让target居中
						MapStatusUpdate u = MapStatusUpdateFactory
								.newMapStatus(new MapStatus.Builder()
										.target(llE).zoom(16).build());

						// 构建MarkerOption，用于在地图上添加Marker
						OverlayOptions option = new MarkerOptions()
								.position(llE).perspective(true)
								.anchor(0.5f, 1).icon(bdE);

						// 在地图上添加Marker，并显示
						mBaiduMap.addOverlay(option);
						mBaiduMap.animateMapStatus(u);

					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mBaiduMap = null;
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();

	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("login", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("unbindCar".equals(requestStr)) {

			boolean flag = jsonObject.optBoolean("flag");
			String msg = jsonObject.optString("msg", "取消关联失败！");
			if (flag) {
				this.finish();
			} else {
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

			}

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.layoutbackimg:
			this.finish();

			break;
		case R.id.startrecord:
			// 开始记录
			if (isMyServiceRunning(MyLocationService.class)) {
				Toast.makeText(this, "已经在记录了哦！", Toast.LENGTH_SHORT).show();
			} else {
				startrecord.setBackgroundResource(R.drawable.recordline02);
				finishrecord.setBackgroundResource(R.drawable.recordline01);
				// 开启定位坐标上传服务
				startPositionService();
			}
			break;
		case R.id.finishrecord:
			// 结束
			if (isMyServiceRunning(MyLocationService.class)) {

				startrecord.setBackgroundResource(R.drawable.recordline01);
				finishrecord.setBackgroundResource(R.drawable.recordline02);
				// 停止服务
				Intent intent = new Intent(MyLocationService.MYLOCATIONSERVICE);
				// android5.0以上必须加上以下一行代码，显示指出
				intent.setPackage(getPackageName());
				stopService(intent);
				// 取消车辆关联
				requestUnBindCar();
			} else {
				Toast.makeText(this, "还没开始记录哦！", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 开启定位服务
	 */
	public void startPositionService() {
		Intent intent = new Intent();
		intent.setAction(MyLocationService.MYLOCATIONSERVICE);
		intent.setPackage(getPackageName());
		intent.putExtra(MyLocationService.MYLOCATIONSERVICE_TYPE,
				MyLocationService.MYLOCATIONSERVICE_START);

		intent.putExtra("carId", carId);

		startService(intent);
	}

	/**
	 * 取消车俩关联
	 * 
	 */
	private void requestUnBindCar() {

		long staffId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("staffId", staffId);
			jsonObject.put("id", carId);
			this.showLoading("正在取消关联……", "unbindCar");
			requestHttp(jsonObject, "unbindCar", Constants.CAR_UNBIND,
					Constants.SERVER_URL);
		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

}

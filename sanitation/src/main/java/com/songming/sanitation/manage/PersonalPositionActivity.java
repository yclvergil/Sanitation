package com.songming.sanitation.manage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.manage.bean.TStaffTrackDto;
import com.songming.sanitation.map.tool.BdMapLocationUtils;
import com.songming.sanitation.map.tool.BdMapLocationUtils.BdLocationSuccessListenner;

/**
 * 人员位置详情界面
 * 
 * @author Administrator
 * 
 */
public class PersonalPositionActivity extends BaseActivity implements OnClickListener {
	private LinearLayout layoutback;
	private ImageView layoutbackimg;
	private TextView topTitle;

	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private List<TStaffTrackDto> locationList = new ArrayList<TStaffTrackDto>();
	private Double latitude;
	private Double longitude;
	private LatLng llE;
	private LatLng ll_left;// 最左
	private LatLng ll_right;// 最右
	private LatLng ll_top;// 最上
	private LatLng ll_bottom;// 最下
	private LatLngBounds bounds;

	private long staffId;
	private String staffIds;
	
	private View mView;
	private TextView marker_name;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.manage_car_track);
		
		staffId = getIntent().getLongExtra("staffId", -1);
		staffIds = getIntent().getStringExtra("staffIds");
		//自定义marker
		mView = LayoutInflater.from(this).inflate(R.layout.map_marker, null);
		marker_name = (TextView) mView.findViewById(R.id.marker_name);
		
		findViews();
		initViews();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		mMapView = (MapView) findViewById(R.id.bmapView);
		
	}

	private void initMap() {
		mMapView.removeViewAt(2);// 移除百度地图右下角缩放图标
		mBaiduMap = mMapView.getMap();
		// 设置地图类型
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// 设置交通图
		// mBaiduMap.setTrafficEnabled(true);
		
		mBaiduMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {
		
			@Override
			public void onMapLoaded() {
			
				//获取数据
				requestData();
			}
		});
	}

	/**
	 * 获取当前客户端位置
	 */
	private void getCurPosition() {
		// 初始化地图显示当前客户端位置
		BdMapLocationUtils.getInstance().startLocation(
				new BdLocationSuccessListenner() {

					@Override
					public void locationResult(double _latitude,
							double _longitude, String _city,
							String _locationAddr, String _locationType) {
						LatLng ll = new LatLng(_latitude, _longitude);
						// 设置地图以动画方式切换,让target居中
						MapStatusUpdate u = MapStatusUpdateFactory
								.newMapStatus(new MapStatus.Builder()
										.target(ll).zoom(16).build());
						mBaiduMap.animateMapStatus(u);
					}
				});
	}

	@Override
	protected void initViews() {

		topTitle.setText("查看人员位置");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		//初始化地图设置
		initMap();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();

	}

	/**
	 * 位置查询
	 */
	private void requestData() {

		JSONObject jsonObject = new JSONObject();
		try {
			if(StringUtilsExt.isEmpty(staffIds)){
				jsonObject.put("staffIds", staffId);
			}else{
				jsonObject.put("staffIds", staffIds);
			}
			this.showLoading("正在查询……", "location");
			requestHttp(jsonObject, "location", Constants.FINDLATESTPOSITION,
					Constants.SERVER_URL);
		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("location".equals(requestStr)) {
			Toast.makeText(this, "获取失败！", Toast.LENGTH_SHORT).show();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("location", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("location".equals(requestStr)) {
			
			Gson gson = new Gson();
			String data = jsonObject.optString("data", "{}");
			locationList = gson.fromJson(data,
					new TypeToken<List<TStaffTrackDto>>() {
			}.getType());
			
			if(locationList.size() == 0){
				Toast.makeText(PersonalPositionActivity.this, "亲，还没有人签到哦！", Toast.LENGTH_SHORT).show();
				getCurPosition();
			}else if (locationList.size() == 1) {
				
				addMark(0);
				// 设置地图以动画方式切换,让target居中
				MapStatusUpdate u = MapStatusUpdateFactory
						.newMapStatus(new MapStatus.Builder()
								.target(llE).zoom(16).build());
				mBaiduMap.animateMapStatus(u);
				
			} else {
				// 显示多人位置
				showStaffPosition();
				
			}
			
		}

	}

	/**
	 * 绘制marker
	 * @param i
	 */
	private void addMark(int i) {
		
		latitude = locationList.get(i).getLatitude();
		longitude = locationList.get(i).getLongitude();
		llE = new LatLng(latitude, longitude);
		
		marker_name.setText(locationList.get(i).getName());
		// 在地图上添加marker标出位置
		OverlayOptions ooD = new MarkerOptions().position(llE)
				.icon(BitmapDescriptorFactory.fromView(mView)).perspective(false).anchor(0.5f, 1).zIndex(7);
		mBaiduMap.addOverlay(ooD);
	}

	/**
	 * 绘制位置marker，并全屏显示
	 */
	private void showStaffPosition() {

		Double lat_b = locationList.get(locationList.size() - 1).getLatitude();
		Double lat_t = locationList.get(locationList.size() - 1).getLatitude();
		Double lnt_r = locationList.get(locationList.size() - 1).getLongitude();
		Double lnt_l = locationList.get(locationList.size() - 1).getLongitude();
		for (int i = 0; i < locationList.size(); i++) {
			latitude = locationList.get(i).getLatitude();
			longitude = locationList.get(i).getLongitude();
			llE = new LatLng(latitude, longitude);
			// 求取轨迹最上下左右点
			lat_t = Math.max(latitude, lat_t);
			lat_b = Math.min(latitude, lat_b);
			lnt_r = Math.max(longitude, lnt_r);
			lnt_l = Math.min(longitude, lnt_l);
			if (lat_b.equals(latitude)) {
				ll_bottom = llE;
			}
			if (lat_t.equals(latitude)) {
				ll_top = llE;
			}
			if (lnt_r.equals(longitude)) {
				ll_right = llE;
			}
			if (lnt_l.equals(longitude)) {
				ll_left = llE;
			}
			//描点
			addMark(i);
		}
		
		// 设置轨迹边界
		bounds = new LatLngBounds.Builder().include(ll_left).include(ll_right)
				.include(ll_top).include(ll_bottom).build();
		// 设置全屏显示
		MapStatusUpdate mu = MapStatusUpdateFactory.newLatLngBounds(bounds);
		mBaiduMap.setMapStatus(mu);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layoutbackimg:
			this.finish();

			break;

		default:
			break;
		}
	}

}

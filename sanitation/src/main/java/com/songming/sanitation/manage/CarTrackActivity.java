package com.songming.sanitation.manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.util.LangUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
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
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.manage.bean.TCarTrackDto;
import com.songming.sanitation.map.tool.BdMapLocationUtils;
import com.songming.sanitation.map.tool.BdMapLocationUtils.BdLocationSuccessListenner;
import com.songming.sanitation.user.CheckUpDetailsActivity;
import com.squareup.timessquare.CalendarPickerView;

/**
 * 汽车轨迹详情界面
 * 
 * @author Administrator
 * 
 */
public class CarTrackActivity extends BaseActivity implements OnClickListener {
	private LinearLayout layoutback;
	private ImageView layoutbackimg;
	private TextView topTitle;

	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private List<TCarTrackDto> locationList = new ArrayList<TCarTrackDto>();
	private Double latitude;
	private Double longitude;
	private LatLng llE;
	private LatLng ll_left;// 最左
	private LatLng ll_right;// 最右
	private LatLng ll_top;// 最上
	private LatLng ll_bottom;// 最下
	private List<LatLng> points = new ArrayList<LatLng>();
	private LatLngBounds bounds;
	private BitmapDescriptor bdE = BitmapDescriptorFactory
			.fromResource(R.drawable.recordline03);// 起点
	private BitmapDescriptor bdEnd = BitmapDescriptorFactory
			.fromResource(R.drawable.recordline04);// 终点
	private long staffId;

	private ImageView calendar;
	private CalendarPickerView calendarView;
	private String seletedDate = "";// 选中的日期

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.manage_car_track);
		staffId = getIntent().getLongExtra("staffId", -1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		seletedDate = sdf.format(new Date());
		
		findViews();
		initViews();
		requestData(seletedDate);
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		mMapView = (MapView) findViewById(R.id.bmapView);
		calendar = (ImageView) findViewById(R.id.checkup_calendar);
		initMap();
	}

	private void initMap() {
		mMapView.removeViewAt(2);// 移除百度地图右下角缩放图标
		mBaiduMap = mMapView.getMap();
		// 设置地图类型
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// 设置交通图
		// mBaiduMap.setTrafficEnabled(true);

	}

	/**
	 * 获取客户端当前位置
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

		topTitle.setText("轨迹详情");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		calendar.setVisibility(View.VISIBLE);
		calendar.setOnClickListener(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();

	}

	/**
	 * 轨迹查询
	 */
	private void requestData(String day) {

		long createId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("staffId", staffId);
			jsonObject.put("createId", createId);
			jsonObject.put("createDate", day);
			
			this.showLoading("正在查询……", "location");
			requestHttp(jsonObject, "location", Constants.LOCATIONLIST,
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
			Log.i("contacts", volleyError.getMessage());
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
					new TypeToken<List<TCarTrackDto>>() {
					}.getType());
			if (locationList == null || locationList.size() < 2) {
				Toast.makeText(CarTrackActivity.this, "当前没有轨迹可供查看！",
						Toast.LENGTH_SHORT).show();
				getCurPosition();
			} else {
				// 显示轨迹
				showCarRoute();

			}

		}

	}

	/**
	 * 绘制车辆轨迹路线图，并全屏显示
	 */
	private void showCarRoute() {

		// 如果既不是起点也不是终点，则剔除点
		for (int i = 1; i < locationList.size() - 2; i++) {
			double lati0 = Double
					.valueOf(locationList.get(i - 1).getLatitude());
			double lnti0 = Double.valueOf(locationList.get(i - 1)
					.getLongitude());
			double lati = Double.valueOf(locationList.get(i).getLatitude());
			double lnti = Double.valueOf(locationList.get(i).getLongitude());

			double distance = DistanceUtil.getDistance(
					new LatLng(lati0, lnti0), new LatLng(lati, lnti));
			if (Math.abs(distance) < 40) {
				locationList.remove(i);
				i--;
			}

		}

		Double lat_b = locationList.get(locationList.size() - 1).getLatitude();
		Double lat_t = locationList.get(locationList.size() - 1).getLatitude();
		Double lnt_r = locationList.get(locationList.size() - 1).getLongitude();
		Double lnt_l = locationList.get(locationList.size() - 1).getLongitude();
		for (int i = 0; i < locationList.size(); i++) {
			latitude = locationList.get(i).getLatitude();
			longitude = locationList.get(i).getLongitude();
			llE = new LatLng(latitude, longitude);
			points.add(llE);
			// 求取轨迹最上下左右点
			lat_b = Math.max(latitude, lat_b);
			lat_t = Math.min(latitude, lat_t);
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

		}

		// 设置起点标签
		OverlayOptions ooD = new MarkerOptions().position(points.get(0))
				.icon(bdE).perspective(false).anchor(0.5f, 1).zIndex(7);
		mBaiduMap.addOverlay(ooD);
		// 设置终点标签
		OverlayOptions ooE = new MarkerOptions()
				.position(points.get(points.size() - 1)).icon(bdEnd)
				.perspective(false).anchor(0.5f, 1).zIndex(7);
		mBaiduMap.addOverlay(ooE);

		// 绘制轨迹路线
		OverlayOptions ooPolyline = new PolylineOptions().width(10)
				.color(getResources().getColor(R.color.red_text))
				.points(points).zIndex(7);
		mBaiduMap.addOverlay(ooPolyline);

		// 设置轨迹边界
		bounds = new LatLngBounds.Builder().include(ll_left).include(ll_right)
				.include(ll_top).include(ll_bottom).build();
		// 设置全屏显示
		MapStatusUpdate mu = MapStatusUpdateFactory.newLatLngBounds(bounds);
		mBaiduMap.setMapStatus(mu);
		mMapView.refreshDrawableState();
	}

	/**
	 * 显示日历
	 */
	private void showCalendar() {
		// 明年
		final Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.YEAR, 1);
		// 去年
		final Calendar lastYear = Calendar.getInstance();
		lastYear.add(Calendar.YEAR, -1);

		final Dialog dialog = new Dialog(this, R.style.dialog);
		dialog.setContentView(R.layout.calendar);
		// 日历返回
		ImageView backImg = (ImageView) dialog.findViewById(R.id.calendar_back);
		backImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		// 日历控件
		calendarView = (CalendarPickerView) dialog
				.findViewById(R.id.calendar_view);
		calendarView.init(lastYear.getTime(), nextYear.getTime())
				.withSelectedDate(new Date());
		calendarView
				.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {

					@Override
					public void onDateUnselected(Date date) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onDateSelected(Date date) {
						if (calendarView.getSelectedDate().getTime() > System
								.currentTimeMillis()) {
							Toast.makeText(CarTrackActivity.this,
									"未来无迹可寻，请选择回顾过去!", 0).show();
							return;
						}
						dialog.dismiss();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						seletedDate = sdf.format(calendarView.getSelectedDate());
						requestData(seletedDate);
					}
				});
		dialog.show();

	}
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layoutbackimg:
			this.finish();

			break;
		case R.id.checkup_calendar:
			showCalendar();
			break;
		default:
			break;
		}
	}

}

package com.songming.sanitation.map;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.HttpRequestTool;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.map.adapter.MissionDetailListAdapter;
import com.songming.sanitation.map.model.LineItemModel;
import com.songming.sanitation.map.model.MapFragmentListModel;
import com.songming.sanitation.map.model.MapModel;
import com.songming.sanitation.map.model.MyLineModel;
import com.songming.sanitation.map.pup.MapPointPup;
import com.songming.sanitation.map.service.MyLocationService;
import com.songming.sanitation.map.tool.BdMapLocationUtils;
import com.songming.sanitation.map.tool.BdMapLocationUtils.BdLocationSuccessListenner;
import com.songming.sanitation.message.MessageAcitivity;
import com.songming.sanitation.message.pup.MessagePup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MapDetailActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener, OnCheckedChangeListener, OnMarkerClickListener {

	private CheckBox position_chechbox;
	private LinearLayout position_layout;

	private LinearLayout layoutback;
	private TextView topTitle = null;

	private MapView mMapView = null;
	private static BaiduMap mBaiduMap = null;
	private Double mLatitude = 0.0;
	private Double mLongitude = 0.0;

	// 中心点位置28.2268120000,112.9070420000
	private Double cLatitude = 0.0;
	private Double cLongitude = 0.0;

	private ImageView jgg_image; // 我的位置
	private ImageView route_image; // 导航
	private ImageView message_image;// 消息

	private ImageView message_new_image;

	// 我的位置
	private static BitmapDescriptor myioc = BitmapDescriptorFactory
			.fromResource(R.drawable.mylocation);
	// 结束
	private BitmapDescriptor start_ioc = BitmapDescriptorFactory
			.fromResource(R.drawable.location_start_ic);
	// 开始
	private BitmapDescriptor nstart_ioc = BitmapDescriptorFactory
			.fromResource(R.drawable.nlocation_start_ioc);

	// 途径点
	private BitmapDescriptor approach_ioc = BitmapDescriptorFactory
			.fromResource(R.drawable.location_end_ioc);

	// marker点击后的ic
	private BitmapDescriptor click_ioc = BitmapDescriptorFactory
			.fromResource(R.drawable.map_click_ic);
	// 开始
	private BitmapDescriptor mystart_ioc = BitmapDescriptorFactory
			.fromResource(R.drawable.my_start_ic);
	// 完成以后的ioc
	private BitmapDescriptor ok_ioc = BitmapDescriptorFactory
			.fromResource(R.drawable.location_ok_ioc);

	private static Marker myMarker = null;
	// 线路规划
	private RoutePlanSearch mSearch = null;
	/*
	 * 网点marker
	 */
	private List<Marker> marKerList = new ArrayList<Marker>();

	public MapPointPup pup;

	private LinearLayout listlayout; // 任务list layout
	private ListView mylist; // 任务list
	private LinearLayout no_mission_layout;// 未完成任务
	private LinearLayout mission_layout;// 已完成任务
	private TextView complete_num_text;// 完成任务数
	private TextView nocomplete_num_text;// 未完成任务数
	private TextView complete_title;
	private TextView nocomplete_title;

	private ImageView complete_imageview;
	private ImageView nocomplete_imageview;

	private MissionDetailListAdapter adapter;

	private List<LatLng> toLatLngList;

	/*
	 * 我经过的点
	 */
	private List<MyLineModel> myLatLngList = new ArrayList<MyLineModel>();

	/*
	 * 左1 右2
	 */
	private int state = 0;

	private MapFragmentListModel model = null;

	private List<LineItemModel> data1 = new ArrayList<LineItemModel>();
	private List<LineItemModel> data2 = new ArrayList<LineItemModel>();
	private ArrayList<LineItemModel> datalist = new ArrayList<LineItemModel>();

	MessagePup mPup = null;

	@Override
	public void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		reReceiver();
		setContentView(R.layout.activity_map_detail);
		model = (MapFragmentListModel) getIntent().getSerializableExtra(
				"MapFragmentListModel");
		findViews();
		initViews();
		//开启定位服务
		startPositionService();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initMap();
		requestlinedetails();
		requestDataUserLine();
		// userid + carId + batch
		requestDataMe();
		String id_m = SharedPreferencesUtils.getStringValue(
				getApplicationContext(),
				MyLocationService.MYLOCATIONSERVICE_STRING, "");
		if (id_m.equals(model.getUserId() + model.getCarId() + model.getBatch()
				+ model.getTripTimes())) {
			position_chechbox.setChecked(true);

		}
		mMapView.onResume();
	}
	
	/*
	 * 添加我的线路
	 */
	public void mapAddLine() {
		List<LatLng> llList = new ArrayList<LatLng>();
		for (MyLineModel lm : myLatLngList) {
			llList.add(new LatLng(Double.parseDouble(lm.getLatitude()), Double
					.parseDouble(lm.getLongtitude())));
		}
		if (llList.size() <= 0) {
			return;
		}
		LatLng llE = llList.get(llList.size() - 1);
		OverlayOptions ooE = new MarkerOptions().position(llE).icon(myioc)
				.perspective(false);
		myMarker = (Marker) (mBaiduMap.addOverlay(ooE));

		LatLng llE1 = llList.get(0);
		OverlayOptions ooE1 = new MarkerOptions().position(llE1)
				.icon(mystart_ioc).perspective(false);
		mBaiduMap.addOverlay(ooE1);

		if (llList.size() > 2) {
			OverlayOptions ooPolyline = new PolylineOptions().width(10)
					.color(getResources().getColor(R.color.red)).zIndex(7)
					.points(llList);
			mBaiduMap.addOverlay(ooPolyline);
		}
	}

	public void initMap() {
		mBaiduMap = mMapView.getMap();
		// 设置是否显示比例尺控件
		mMapView.showScaleControl(true);
		// 设置是否显示缩放控件
		mMapView.showZoomControls(false);
		mBaiduMap.setOnMarkerClickListener(this);
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);

		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(listener);
	}

	OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
		public void onGetWalkingRouteResult(WalkingRouteResult result) {
			// 获取步行线路规划结果
		}

		public void onGetTransitRouteResult(TransitRouteResult result) {
			// 获取公交换乘路径规划结果
		}

		public void onGetDrivingRouteResult(DrivingRouteResult result) {
			// 获取驾车线路规划结果
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(MapDetailActivity.this, "抱歉，未找到结果",
						Toast.LENGTH_SHORT).show();
			}
			if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
				// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
				// result.getSuggestAddrInfo()
				return;
			}
			if (result.error == SearchResult.ERRORNO.NO_ERROR) {
				String llString = "";
				List<DrivingRouteLine> lineList = result.getRouteLines();
				// clearAllMarker();
				if (lineList.size() > 0) {
					DrivingRouteLine line = lineList.get(0);
					List<DrivingStep> drvingList = line.getAllStep();
					// List<LatLng> mlistL = toLatLngList;
					for (int i = 0; i < drvingList.size(); i++) {
						DrivingStep step = drvingList.get(i);

						List<LatLng> points = step.getWayPoints();
						OverlayOptions ooPolyline = new PolylineOptions()
								.width(10)
								.color(getResources().getColor(
										R.color.map_point_pupbgcolor_08))
								.zIndex(7).points(points);
						mBaiduMap.addOverlay(ooPolyline);

					}
				}
			}
		}
	};
	
	/**
	 * 以动画方式移动marker到地图中心
	 */
	public static void moveMapCenter(LatLng latl) {
		if(mBaiduMap == null){
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

	// 添加Marker
	public Marker addMarKer(LatLng latLng, BitmapDescriptor ioc) {
		OverlayOptions ooE = new MarkerOptions().position(latLng).icon(ioc)
				.perspective(false);

		return (Marker) mBaiduMap.addOverlay(ooE);
	}

	/**
	 * 在地图标记我的位置
	 */
	public static void initMyLoaction(LatLng llE) {
		if(mBaiduMap == null){
			return;
		}
		if(myMarker != null){
			//隐藏marker
			myMarker.setVisible(false);
		}
		OverlayOptions ooE = new MarkerOptions().position(llE).icon(myioc)
				.perspective(false);
		myMarker = (Marker) mBaiduMap.addOverlay(ooE);
	}

	// 发起线路规划
	public void startSearch(LatLng startLatLngList, LatLng toLatLngList,
			List<LatLng> byLatLngList) {

		DrivingRoutePlanOption option = new DrivingRoutePlanOption();
		// 设置起点
		option.from(PlanNode.withLocation(startLatLngList));
		if (byLatLngList != null) {
			List<PlanNode> pList = new ArrayList<PlanNode>();
			for (LatLng ll : byLatLngList) {
				pList.add(PlanNode.withLocation(ll));
			}
			option.passBy(pList);
		}
		// 设置终点
		option.to(PlanNode.withLocation(toLatLngList));
		mSearch.drivingSearch(option);
	}

	/*
	 * 清除所以标题
	 */
	public void clearAllMarker() {
		for (Marker mk : marKerList) {
			mk.remove();
		}
		marKerList.clear();
	}

	/*
	 * 还原所以标题
	 */
	public void initAllMarker() {
		for (int i = 0; i < marKerList.size(); i++) {
			Marker mk = marKerList.get(i);

			Bundle bundle = mk.getExtraInfo();
			LineItemModel model = (LineItemModel) bundle.get("data");
			if ("1".equals(model.getStatus())) {
				mk.setIcon(ok_ioc);
			} else {
				if (i == marKerList.size() - 1) {
					mk.setIcon(start_ioc);
				} else if (i == 0) {
					mk.setIcon(nstart_ioc);
				} else {
					mk.setIcon(approach_ioc);
				}
			}
		}
	}

	@Override
	protected void findViews() {
		mMapView = (MapView) findViewById(R.id.bmapView);
		topTitle = (TextView) findViewById(R.id.topTitle);
		jgg_image = (ImageView) findViewById(R.id.jgg_image);
		route_image = (ImageView) findViewById(R.id.route_image);
		message_image = (ImageView) findViewById(R.id.message_image);
		listlayout = (LinearLayout) findViewById(R.id.listlayout);
		mylist = (ListView) findViewById(R.id.mylist);
		no_mission_layout = (LinearLayout) findViewById(R.id.no_mission_layout);
		mission_layout = (LinearLayout) findViewById(R.id.mission_layout);
		complete_num_text = (TextView) findViewById(R.id.complete_num_text);
		nocomplete_num_text = (TextView) findViewById(R.id.nocomplete_num_text);

		complete_title = (TextView) findViewById(R.id.complete_title);
		nocomplete_title = (TextView) findViewById(R.id.nocomplete_title);
		layoutback = (LinearLayout) findViewById(R.id.layoutback);

		complete_imageview = (ImageView) findViewById(R.id.complete_imageview);
		nocomplete_imageview = (ImageView) findViewById(R.id.nocomplete_imageview);

		position_layout = (LinearLayout) findViewById(R.id.position_layout);
		position_chechbox = (CheckBox) findViewById(R.id.position_chechbox);

		message_new_image = (ImageView) findViewById(R.id.message_new_image);
	}

	@Override
	protected void initViews() {
		topTitle.setText("线路");
		topTitle.setVisibility(View.VISIBLE);
		layoutback.setVisibility(View.VISIBLE);
		layoutback.setOnClickListener(this);
		jgg_image.setOnClickListener(this);
		route_image.setOnClickListener(this);
		no_mission_layout.setOnClickListener(this);
		mission_layout.setOnClickListener(this);
		message_image.setOnClickListener(this);
		pup = new MapPointPup(MapDetailActivity.this, this);
		mPup = new MessagePup(MapDetailActivity.this, this);
		pup.setOnDismissListener(new PopupWindow.OnDismissListener() {

			@Override
			public void onDismiss() {
				initAllMarker();
			}
		});
		mylist.setOnItemClickListener(this);

		/*position_chechbox
				.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (!buttonView.isPressed())
							return;
						if (isChecked) {
							Intent intent = new Intent();
							intent.setAction(MyLocationService.MYLOCATIONSERVICE);
							intent.setPackage(getPackageName());
							intent.putExtra(
									MyLocationService.MYLOCATIONSERVICE_TYPE,
									MyLocationService.MYLOCATIONSERVICE_START);
							intent.putExtra("userId", applications.getUser()
									.getId());
							intent.putExtra("carId", model.getCarId());
							intent.putExtra("batch", model.getBatch());
							intent.putExtra("tripTimes", model.getTripTimes()
									+ "");
							intent.putExtra("datalist", datalist);

							MapDetailActivity.this.startService(intent);
							Toast.makeText(MapDetailActivity.this, "已开启定位！", 1)
									.show();
							// myHandler.sendEmptyMessageDelayed(0, 1000);
						} else {
							Intent intent = new Intent();
							intent.setAction(MyLocationService.MYLOCATIONSERVICE);
							intent.setPackage(getPackageName());
							intent.putExtra(
									MyLocationService.MYLOCATIONSERVICE_TYPE,
									MyLocationService.MYLOCATIONSERVICE_STOP);
							MapDetailActivity.this.startService(intent);
							Toast.makeText(MapDetailActivity.this, "已关闭定位！", 1)
									.show();
							SharedPreferencesUtils.setStringValue(
									getApplicationContext(),
									MyLocationService.MYLOCATIONSERVICE_STRING,
									"");
						}
					}
				});
		position_layout.setVisibility(View.VISIBLE);*/
	}

	/**
	 * 开启定位服务
	 */
	public void startPositionService(){
		Intent intent = new Intent();
		intent.setAction(MyLocationService.MYLOCATIONSERVICE);
		intent.setPackage(getPackageName());
		intent.putExtra(
				MyLocationService.MYLOCATIONSERVICE_TYPE,
				MyLocationService.MYLOCATIONSERVICE_START);
		intent.putExtra("userId", applications.getUser()
				.getId());
		intent.putExtra("carId", model.getCarId());
		intent.putExtra("batch", model.getBatch());
		intent.putExtra("tripTimes", model.getTripTimes()
				+ "");
		intent.putExtra("datalist", datalist);

		MapDetailActivity.this.startService(intent);
	}
	
	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			BdMapLocationUtils.getInstance().startLocation(
					new BdLocationSuccessListenner() {
						@Override
						public void locationResult(double _latitude,
								double _longitude, String _city,
								String _locationAddr, String _locationType) {

							if (cLatitude == 0.0 || cLongitude == 0.0) {
								cLatitude = _latitude;
								cLongitude = _longitude;
								execute();
							} else {
								double distance = DistanceUtil.getDistance(
										new LatLng(_latitude, _longitude),
										new LatLng(cLatitude, cLongitude));

								if (Math.abs(distance) >= 100) {
									execute();
								}
							}
							// 初始化覆盖物
							cLatitude = _latitude;
							cLongitude = _longitude;
						}
					});
			myHandler.sendEmptyMessageDelayed(0, 30 * 1000);
		}
	};
	
	/**
	 * 获取客户端自身当前位置
	 */
	private void getMyPosition(){
		BdMapLocationUtils.getInstance().startLocation(
				new BdLocationSuccessListenner() {
					@Override
					public void locationResult(double _latitude,
							double _longitude, String _city,
							String _locationAddr, String _locationType) {
						LatLng ll = new LatLng(_latitude, _longitude);
						//在地图上显示自己位置
						moveMapCenter(ll);
						initMyLoaction(ll);
					}
				});
	}

	private void execute() {

		JSONObject jsonObject = new JSONObject();
		try {
			// jsonObject.put("userId", "07041971-56eb-4a1d-a0c9-c9277e891a0e");
			// jsonObject.put("carId", "1");
			// jsonObject.put("batch", "20160126032");
			// jsonObject.put("tripTimes", "1");
			jsonObject.put("userId", applications.getUser().getId());
			jsonObject.put("carId", model.getCarId());
			jsonObject.put("batch", model.getBatch());
			jsonObject.put("tripTimes", model.getTripTimes());
			jsonObject.put("longtitude", "" + cLatitude);
			jsonObject.put("latitude", "" + cLatitude);
			jsonObject.put("positionTime", "");

			requestHttp(jsonObject, "adddistributiongrace",
					Constants.ADDDISTRIBUTIONGRACE, Constants.SERVER_URL);
		} catch (JSONException e) {
			Toast.makeText(MapDetailActivity.this,
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(MapDetailActivity.this, "请重新启动", Toast.LENGTH_SHORT)
					.show();
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
		if ("car_dis_id".equals(requestStr)) {
			Toast.makeText(MapDetailActivity.this, "确定网点失败！",
					Toast.LENGTH_SHORT).show();
		}
		if ("findlongtitudeaanlatitude".equals(requestStr)) {
			Toast.makeText(MapDetailActivity.this, "获取用户行驶线路失败！",
					Toast.LENGTH_SHORT).show();
		}
		if ("findlinedetails".equals(requestStr)) {
			Toast.makeText(MapDetailActivity.this, "获取网点信息失败！",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("car_dis_id".equals(requestStr)) {
			String data = jsonObject.optString("flag");
			String msg = jsonObject.optString("msg");
			if ("true".equals(data)) {
				Toast.makeText(MapDetailActivity.this, "确定网点成功！",
						Toast.LENGTH_SHORT).show();
				pup.setBgok();
				requestlinedetails();
			} else {
				Toast.makeText(MapDetailActivity.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
		}

		if ("findnewestcount".equals(requestStr)) {
			String data = jsonObject.optString("flag");
			if ("true".equals(data)) {
				int num = 0;
				try {
					num = jsonObject.getInt("data");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					num = 0;
				}
				if (num > 0) {
					message_new_image.setVisibility(View.VISIBLE);
				} else {
					message_new_image.setVisibility(View.GONE);
				}
			}
		}

		if ("findlongtitudeaanlatitude".equals(requestStr)) {
			String data = jsonObject.optString("data");
			if (data != null && !"".equals(data)) {
				Gson gson = new Gson();
				myLatLngList = gson.fromJson(data,
						new TypeToken<List<MyLineModel>>() {
						}.getType());
				mapAddLine();
			}
		}
		if ("findlinedetails".equals(requestStr)) {
			String data = jsonObject.optString("data");
			if (data != null && !"".equals(data)) {
				Gson gson = new Gson();
				datalist = gson.fromJson(data,
						new TypeToken<List<LineItemModel>>() {
						}.getType());

				data1.clear();
				data2.clear();
				for (LineItemModel model : datalist) {
					if ("0".equals(model.getStatus())) {
						data1.add(model);
					} else if ("1".equals(model.getStatus())) {
						data2.add(model);
					}
				}
				nocomplete_num_text.setText("" + data1.size());
				complete_num_text.setText("" + data2.size());
				initList();
				findlineMap();
			}
		}
	}

	private boolean isend = false;

	/*
	 * 画线路
	 */
	public void findlineMap() {
		clearAllMarker();
		List<LatLng> bsList = new ArrayList<LatLng>();
		isend = false;
		if (datalist == null) {
			return;
		}
		if (datalist.size() <= 0) {
			return;
		}

		if (datalist.size() > 1) {
			LatLng ll_left = null;// 最左
			LatLng ll_right = null;// 最右
			LatLng ll_top = null;// 最上
			LatLng ll_bottom = null;// 最下
			Double lat_b = Double.valueOf(datalist.get(0).getStationLatitude());
			Double lat_t = Double.valueOf(datalist.get(0).getStationLatitude());
			Double lnt_r = Double.valueOf(datalist.get(0)
					.getStationLongtitude());
			Double lnt_l = Double.valueOf(datalist.get(0)
					.getStationLongtitude());

			for (int i = 0; i < datalist.size() - 1; i++) {

				LineItemModel model1 = datalist.get(i);
				LineItemModel model2 = datalist.get(i + 1);
				model1.setMarker_num((i + 1) + "");
				model2.setMarker_num((i + 2) + "");
				LatLng Ll1 = new LatLng(Double.parseDouble(model1
						.getStationLatitude()), Double.parseDouble(model1
						.getStationLongtitude()));

				LatLng Ll2 = new LatLng(Double.parseDouble(model2
						.getStationLatitude()), Double.parseDouble(model2
						.getStationLongtitude()));

				if (i == 0) {
					moveMapCenter(Ll1);
					mLatitude = Double.parseDouble(model1.getStationLatitude());
					mLongitude = Double.parseDouble(model1
							.getStationLongtitude());

					Marker maker;
					if ("0".equals(model1.getStatus())) {
						maker = addMarKer(Ll1, nstart_ioc);
					} else {
						maker = addMarKer(Ll1, ok_ioc);
					}
					Bundle bundle = new Bundle();
					bundle.putSerializable("data", model1);
					maker.setExtraInfo(bundle);
					marKerList.add(maker);
				}
				if (i == datalist.size() - 2) {
					Marker maker;
					if ("0".equals(model2.getStatus())) {
						maker = addMarKer(Ll2, start_ioc);
					} else {
						maker = addMarKer(Ll2, ok_ioc);
					}
					Bundle bundle = new Bundle();
					bundle.putSerializable("data", model2);
					maker.setExtraInfo(bundle);
					marKerList.add(maker);
					isend = true;
				}
				if (!isend) {
					bsList.add(Ll2);
					Marker maker;
					if ("0".equals(model2.getStatus())) {
						maker = addMarKer(Ll2, approach_ioc);
					} else {
						maker = addMarKer(Ll2, ok_ioc);
					}
					Bundle bundle = new Bundle();
					bundle.putSerializable("data", model2);
					maker.setExtraInfo(bundle);
					marKerList.add(maker);
				}

				// 求取轨迹最上下左右点
				lat_b = Math.max(Double.valueOf(Ll2.latitude), lat_b);
				lat_t = Math.min(Double.valueOf(Ll2.latitude), lat_t);
				lnt_r = Math.max(Double.valueOf(Ll2.longitude), lnt_r);
				lnt_l = Math.min(Double.valueOf(Ll2.longitude), lnt_l);

				if (String.valueOf(lat_b).equals(Ll2.latitude)) {
					ll_bottom = Ll2;
				}
				if (String.valueOf(lat_t).equals(Ll2.latitude)) {
					ll_top = Ll2;
				}
				if (String.valueOf(lnt_r).equals(Ll2.longitude)) {
					ll_right = Ll2;
				}
				if (String.valueOf(lnt_l).equals(Ll2.longitude)) {
					ll_left = Ll2;
				}
			}

			LatLng startlL = new LatLng(Double.parseDouble(datalist.get(0)
					.getStationLatitude()), Double.parseDouble(datalist.get(0)
					.getStationLongtitude()));
			LatLng endL = new LatLng(Double.parseDouble(datalist.get(
					datalist.size() - 1).getStationLatitude()),
					Double.parseDouble(datalist.get(datalist.size() - 1)
							.getStationLongtitude()));
			// 开始线路规格
			startSearch(startlL, endL, bsList);

			// 设置轨迹边界
			LatLngBounds bounds = new LatLngBounds.Builder().include(ll_left)
					.include(ll_right).include(ll_top).include(ll_bottom)
					.include(startlL).include(endL).build();
			// 设置全屏显示
			MapStatusUpdate mu = MapStatusUpdateFactory.newLatLngBounds(bounds);
			mBaiduMap.setMapStatus(mu);
			mMapView.refreshDrawableState();
		} else {
			Marker maker = addMarKer(
					new LatLng(Double.parseDouble(datalist.get(0)
							.getStationLatitude()), Double.parseDouble(datalist
							.get(0).getStationLongtitude())), approach_ioc);
			Bundle bundle = new Bundle();
			bundle.putSerializable("data", datalist.get(0));
			maker.setExtraInfo(bundle);
			marKerList.add(maker);
		}
	}

	private void initList() {
		//
		if (state == 1) {
			// data1
			adapter = new MissionDetailListAdapter(MapDetailActivity.this,
					data1);
		} else if (state == 2) {
			// data2
			adapter = new MissionDetailListAdapter(MapDetailActivity.this,
					data2);
		}
		mylist.setAdapter(adapter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mBaiduMap = null;
		mMapView.onDestroy();
		unreceiver();
	}

	@Override
	public void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	// 导航
	public void getRoute(Double current_latitude, Double current_longitude) {

		Intent intent = null;

		try {// 如果有安装百度地图 就启动百度地图
			StringBuffer sbs = new StringBuffer();

			sbs.append("intent://map/direction?")
					.append("origin=latlng:")
					// 我的位置
					.append(current_latitude)
					.append(",")
					.append(current_longitude)
					.append("|name:")
					.append(getResources().getString(R.string.location))
					// 去的位置
					// .append("&destination=latlng:").append(llE.latitude)
					// // 经度
					// .append(",").append(llE.longitude)
					// 纬度
					// .append("|name:")
					// 城市
					.append("&mode=driving®")
					.append("&src=健康芯#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
			try {
				intent = Intent.getIntent(sbs.toString());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			startActivity(intent);

		} catch (Exception e) {// 没有百度地图则弹出网页端
			StringBuffer sb = new StringBuffer();
			sb.append("http://api.map.baidu.com/direction?origin=latlng:")
					// 我的位置
					.append(current_latitude).append(",")
					.append(current_longitude)
					// 去的位置
					// .append("&destination=latlng:").append(llE.latitude)
					// // 经度
					// .append(",").append(llE.longitude)
					// 纬度
					.append("&mode=driving®").append("&output=html");
			Uri uri = Uri.parse(sb.toString());
			intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.message_image:
			Intent intent = new Intent(this, MessageAcitivity.class);
			startActivity(intent);
			break;
		case R.id.layoutback:
			finish();
			break;
		case R.id.ok_text:
			requestDataConfirm();
			break;
		case R.id.jgg_image:
			//定位
			getMyPosition();
			// if (mLatitude != 0.0) {
			// LatLng llE = new LatLng(mLatitude, mLongitude);
			// // 在地图上显示位置
			// moveMapCenter(llE);
			// initMyLoaction(llE);
			// }
			break;
		case R.id.route_image:
			//导航
			if (mLatitude != 0.0) {
				getRoute(mLatitude, mLongitude);
			}
			break;
		// 未完成点击
		case R.id.no_mission_layout:
			if (listlayout.getVisibility() == View.GONE) {
				listlayout.setVisibility(View.VISIBLE);
				no_mission_layout.setBackground(getResources().getDrawable(
						R.drawable.map_fragment_select1));
				nocomplete_title.setTextColor(getResources().getColor(
						R.color.title_bar_28c3b1));
				nocomplete_num_text.setTextColor(getResources().getColor(
						R.color.title_bar_28c3b1));
				complete_imageview
						.setImageResource(R.drawable.location_dropdown_ic);
				nocomplete_imageview
						.setImageResource(R.drawable.location_dropdown_ic1);
				state = 1;
			} else if (state == 1) {
				listlayout.setVisibility(View.GONE);
				no_mission_layout.setBackground(getResources().getDrawable(
						R.drawable.map_fragment_select0));
				nocomplete_title.setTextColor(getResources().getColor(
						R.color.white));
				nocomplete_num_text.setTextColor(getResources().getColor(
						R.color.white));
				complete_imageview
						.setImageResource(R.drawable.location_dropdown_ic);
				nocomplete_imageview
						.setImageResource(R.drawable.location_dropdown_ic);
			} else {
				no_mission_layout.setBackground(getResources().getDrawable(
						R.drawable.map_fragment_select1));
				mission_layout.setBackground(getResources().getDrawable(
						R.drawable.map_fragment_select0));

				nocomplete_title.setTextColor(getResources().getColor(
						R.color.title_bar_28c3b1));
				nocomplete_num_text.setTextColor(getResources().getColor(
						R.color.title_bar_28c3b1));
				complete_title.setTextColor(getResources().getColor(
						R.color.white));
				complete_num_text.setTextColor(getResources().getColor(
						R.color.white));

				complete_imageview
						.setImageResource(R.drawable.location_dropdown_ic);
				nocomplete_imageview
						.setImageResource(R.drawable.location_dropdown_ic1);
				state = 1;
			}
			initList();
			break;
		// 完成点击
		case R.id.mission_layout:
			if (listlayout.getVisibility() == View.GONE) {
				listlayout.setVisibility(View.VISIBLE);
				mission_layout.setBackground(getResources().getDrawable(
						R.drawable.map_fragment_select1));
				complete_title.setTextColor(getResources().getColor(
						R.color.title_bar_28c3b1));
				complete_num_text.setTextColor(getResources().getColor(
						R.color.title_bar_28c3b1));

				complete_imageview
						.setImageResource(R.drawable.location_dropdown_ic1);
				nocomplete_imageview
						.setImageResource(R.drawable.location_dropdown_ic);
				state = 2;
			} else if (state == 2) {
				listlayout.setVisibility(View.GONE);
				mission_layout.setBackground(getResources().getDrawable(
						R.drawable.map_fragment_select0));
				complete_title.setTextColor(getResources().getColor(
						R.color.white));
				complete_num_text.setTextColor(getResources().getColor(
						R.color.white));
				complete_imageview
						.setImageResource(R.drawable.location_dropdown_ic);
				nocomplete_imageview
						.setImageResource(R.drawable.location_dropdown_ic);
			} else {
				mission_layout.setBackground(getResources().getDrawable(
						R.drawable.map_fragment_select1));
				no_mission_layout.setBackground(getResources().getDrawable(
						R.drawable.map_fragment_select0));
				nocomplete_title.setTextColor(getResources().getColor(
						R.color.white));
				nocomplete_num_text.setTextColor(getResources().getColor(
						R.color.white));

				complete_title.setTextColor(getResources().getColor(
						R.color.title_bar_28c3b1));
				complete_num_text.setTextColor(getResources().getColor(
						R.color.title_bar_28c3b1));

				complete_imageview
						.setImageResource(R.drawable.location_dropdown_ic1);
				nocomplete_imageview
						.setImageResource(R.drawable.location_dropdown_ic);
				state = 2;
			}
			initList();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (state == 1) {
			// data1
			pup.setData(data1.get(position));
		} else if (state == 2) {
			// data2
			pup.setData(data2.get(position));
		}
		pup.showAtLocation(findViewById(R.id.layout), Gravity.FILL, 0, 0);
		listlayout.setVisibility(View.GONE);
		mission_layout.setBackground(getResources().getDrawable(
				R.drawable.map_fragment_select0));
		no_mission_layout.setBackground(getResources().getDrawable(
				R.drawable.map_fragment_select0));
		nocomplete_title.setTextColor(getResources().getColor(R.color.white));
		nocomplete_num_text
				.setTextColor(getResources().getColor(R.color.white));
		complete_title.setTextColor(getResources().getColor(R.color.white));
		complete_num_text.setTextColor(getResources().getColor(R.color.white));
		complete_imageview.setImageResource(R.drawable.location_dropdown_ic);
		nocomplete_imageview.setImageResource(R.drawable.location_dropdown_ic);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (group.getCheckedRadioButtonId()) {
		// 网点信息
		case R.id.mission_radio_1:
			pup.visiLayout(1);
			break;
		// 报表
		case R.id.mission_radio_2:
			pup.visiLayout(2);
			break;
		default:
			break;
		}
	}

	BroadcastReceiver myReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if ("MyLocationBroadcastReceiver".equals(action)) {
				// mLatitude = intent.getDoubleExtra("_latitude", 0.0);
				// mLongitude = intent.getDoubleExtra("_longitude", 0.0);
				// initMyLoactio();

				mPup.showAtLocation(findViewById(R.id.layout), Gravity.FILL, 0,
						0);
				mPup.setcontent(intent.getStringExtra("data") + "");
				mPup.setid(intent.getStringExtra("id"));
			}
		}
	};

	public void reReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("MyLocationBroadcastReceiver");
		registerReceiver(myReceiver, filter);
	}

	public void unreceiver() {
		unregisterReceiver(myReceiver);
	}

	// marker点击事件
	@Override
	public boolean onMarkerClick(Marker arg0) {
		for (Marker mk : marKerList) {
			if (mk.getPosition().latitude == arg0.getPosition().latitude
					&& mk.getPosition().longitude == arg0.getPosition().longitude) {
				initAllMarker();
				arg0.setIcon(click_ioc);
				moveMapCenter(mk.getPosition());
				Bundle bundle = mk.getExtraInfo();
				LineItemModel model = (LineItemModel) bundle
						.getSerializable("data");
				if (model != null) {
					pup.setData(model);
				}
				pup.showAtLocation(findViewById(R.id.layout), Gravity.FILL, 0,
						0);
				break;
			}
		}

		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& listlayout.getVisibility() == View.VISIBLE) {
			listlayout.setVisibility(View.GONE);

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 确认网点
	private void requestDataConfirm() {

		LineItemModel data = pup.getData();
		if (data != null) {
			this.showLoading("正在确认网点。。。请稍后...", "refresh");

			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("car_dis_id", data.getCarDisId());
				requestHttp(jsonObject, "car_dis_id",
						Constants.MODIFYSTATIONSTATUS, Constants.SERVER_URL);
			} catch (JSONException e) {
				Toast.makeText(MapDetailActivity.this,
						"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT)
						.show();

			} catch (Exception e) {
				Toast.makeText(MapDetailActivity.this, "请重新启动",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(MapDetailActivity.this, "确认网点失败！",
					Toast.LENGTH_SHORT).show();
		}
	}

	// 获取用户经纬度
	private void requestDataUserLine() {

		this.showLoading("正在获取位置线路...", "refresh");

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("userId", applications.getUser().getId());
			jsonObject.put("carId", model.getCarId());
			jsonObject.put("batch", model.getBatch());
			jsonObject.put("tripTimes", model.getTripTimes() + "");
			requestHttp(jsonObject, "findlongtitudeaanlatitude",
					Constants.FINDLONGTITUDEAANLATITUDE, Constants.SERVER_URL);

		} catch (JSONException e) {
			Toast.makeText(MapDetailActivity.this,
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(MapDetailActivity.this, "请重新启动", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/*
	 * 根据线路查询线路详情
	 */
	private void requestlinedetails() {
		this.showLoading("正在获取位置线路...", "refresh");
		JSONObject jsonObject = new JSONObject();
		try {
			// jsonObject.put("userId", "07041971-56eb-4a1d-a0c9-c9277e891a0e");
			// jsonObject.put("carId", "1");
			// jsonObject.put("batch", "20160126032");
			// jsonObject.put("tripTimes", "1");

			jsonObject.put("userId", applications.getUser().getId());
			jsonObject.put("carId", model.getCarId());
			jsonObject.put("batch", model.getBatch());
			jsonObject.put("tripTimes", model.getTripTimes() + "");

			requestHttp(jsonObject, "findlinedetails",
					Constants.FINDLINEDETAILS, Constants.SERVER_URL);
		} catch (JSONException e) {
			Toast.makeText(MapDetailActivity.this,
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(MapDetailActivity.this, "请重新启动", Toast.LENGTH_SHORT)
					.show();
		}
	}

	// 获取用户消息最查看数目
	private void requestDataMe() {

		this.showLoading("正在获取位置线路...", "refresh");

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("userId", applications.getUser().getId());
			requestHttp(jsonObject, "findnewestcount",
					Constants.FINDNEWESTCOUNT, Constants.SERVER_URL);

		} catch (JSONException e) {
			Toast.makeText(MapDetailActivity.this,
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(MapDetailActivity.this, "请重新启动", Toast.LENGTH_SHORT)
					.show();
		}
	}
	// public void
}

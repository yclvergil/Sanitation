package com.songming.sanitation.user;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActionBar.LayoutParams;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.RefreshUtils;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.manage.RecordLineActivity;
import com.songming.sanitation.map.service.MyLocationService;
import com.songming.sanitation.user.adapter.BusNumberAdapter;
import com.songming.sanitation.user.adapter.CarAdapter;
import com.songming.sanitation.user.adapter.CarTypeAdapter;
import com.songming.sanitation.user.model.TCarDto;
import com.songming.sanitation.user.model.TStaffCarRefDto;

/**
 * 关联车辆界面
 * */
public class CarActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;
	private PopupWindow popupwindow;// 下拉框显示列表
	private ListView popup_listview;// popupwindow里面展示信息的listview
	private TextView car_type;// 车辆类别下拉列表
	private String carType;
	private String carNo;

	private TextView addCar;// 新增关联
	private TextView bus_number;// 车牌号码下拉列表
	private List<TCarDto> carList = new ArrayList<TCarDto>();// 车辆列表
	private CarTypeAdapter cartype_adapter;// 车辆类别适配器;
	// private BusNumberAdapter busnumber_adapter;// 车牌号码适配器;
	private CarAdapter carAdapter;// 关联记录适配器

	private Button relevance;// 关联按键
	private Button car_unbind;// 取消关联

	private List<TStaffCarRefDto> list = new ArrayList<TStaffCarRefDto>();// 历史关联记录集合
	private PullToRefreshListView listview;// 关联记录展示列表
	private int mCurIndex = 1;
	private static final String TAG = "CarActivity";
	private long carId;// 车辆id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car);

		findViews();
		initViews();
		requestCarList();
		requestCarHistoryList();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		car_type = (TextView) findViewById(R.id.car_type);
		addCar = (TextView) findViewById(R.id.addCar);

		bus_number = (TextView) findViewById(R.id.car_number);

		relevance = (Button) findViewById(R.id.car_relevance);
		car_unbind = (Button) findViewById(R.id.car_unbind);

		listview = (PullToRefreshListView) findViewById(R.id.car_mlistview);
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		topTitle.setText("关联车辆");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		addCar.setOnClickListener(this);
		// 车辆类别点击
		// car_type.setOnClickListener(this);
		// 车辆号码点击
		// bus_number.setOnClickListener(this);
		// 车辆类别适配器
		cartype_adapter = new CarTypeAdapter(this, carList);
		// 车牌号码适配器
		// busnumber_adapter = new BusNumberAdapter(this, null);

		relevance.setOnClickListener(this);
		car_unbind.setOnClickListener(this);

		carAdapter = new CarAdapter(this, list);
		// 车辆关联记录
		listview.setAdapter(carAdapter);

		listview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				mCurIndex = 1;
				requestCarHistoryList();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				mCurIndex++;
				requestCarHistoryList();
			}
		});

		RefreshUtils.setRefreshPrompt(listview);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// 关联此车
				carId = list.get(position - 1).getCarId();
				requestBindCar();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		mCurIndex = 1;
		requestCarHistoryList();

	}

	// 下拉列表显示框
	private void PopSite(View control, BaseAdapter adapter) {
		View PopView = getLayoutInflater().inflate(R.layout.upkeep_popup, null);
		// 设置宽高
		popupwindow = new PopupWindow(PopView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		// 焦点设置，点击区域外消失参数必须为true
		popupwindow.setFocusable(true);
		// 点击区域外消失必须设置此方法
		popupwindow.setBackgroundDrawable(new BitmapDrawable());
		// 选择在某一个控件下面
		popupwindow.showAsDropDown(control);
		popup_listview = (ListView) PopView
				.findViewById(R.id.upkeep_pop_listview);

		popup_listview.setAdapter(adapter);
		// 下拉列表item点击事件
		popup_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (null != popupwindow && popupwindow.isShowing()) {
					popupwindow.dismiss();
				}

				carId = carList.get(position).getId();
				carType = carList.get(position).getCarTypeName();
				carNo = carList.get(position).getCarNo();
				String carCode = carList.get(position).getCarCode();
				bus_number.setText(carType + "-" + carNo + "\t" + carCode);
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.addCar:
			PopSite(v, cartype_adapter);
			break;
		case R.id.car_type:
			// PopSite(car_type, cartype_adapter);
			break;
		case R.id.car_number:
			// PopSite(bus_number, busnumber_adapter);
			break;
		case R.id.layoutbackimg:
			this.finish();
			break;
		case R.id.car_relevance:
			// 关联车辆
			requestBindCar();
			break;
		case R.id.car_unbind:
			// 取消关联车辆
			requestUnBindCar(carId);
			break;
		default:
			break;
		}
	}

	/**
	 * 车俩列表查询
	 * 
	 */
	private void requestCarList() {

		long staffId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("staffId", staffId);
			this.showLoading("正在查询……", TAG);
			requestHttp(jsonObject, TAG, Constants.CARLIST,
					Constants.SERVER_URL);
		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 车俩关联
	 * 
	 */
	private void requestBindCar() {

		long staffId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("staffId", staffId);
			jsonObject.put("id", carId);
			this.showLoading("正在关联中……", "bindCar");
			requestHttp(jsonObject, "bindCar", Constants.CAR_BIND,
					Constants.SERVER_URL);
		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 取消车俩关联
	 * 
	 */
	private void requestUnBindCar(long carId) {

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

	/**
	 * 车俩关联记录查询
	 * 
	 */
	private void requestCarHistoryList() {

		long staffId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		try {
			jsonObject2.put("staffId", staffId);
			jsonObject.put("paramsMap", jsonObject2);
			jsonObject.put("page", mCurIndex);
			jsonObject.put("rows", 20);
			this.showLoading("正在查询……", "contacts");
			requestHttp(jsonObject, "contacts", Constants.CAR_USED_RECORDS,
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
		if ("contacts".equals(requestStr)) {
			Toast.makeText(this, "获取失败！", Toast.LENGTH_SHORT).show();
			listview.onRefreshComplete();
		}
		if ("bindCar".equals(requestStr)) {
			Toast.makeText(this, "关联失败！", Toast.LENGTH_SHORT).show();
		}
		if ("unbindCar".equals(requestStr)) {
			Toast.makeText(this, "取消关联失败！", Toast.LENGTH_SHORT).show();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i(TAG, volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("contacts".equals(requestStr)) {

			Gson gson = new Gson();
			String data = jsonObject.optString("data", "{}");
			List<TStaffCarRefDto> newList = gson.fromJson(data,
					new TypeToken<List<TStaffCarRefDto>>() {
					}.getType());
			if (mCurIndex <= 1) {
				if (newList == null || newList.size() == 0) {
					Toast.makeText(CarActivity.this, "您还没有关联过任何车辆！",
							Toast.LENGTH_SHORT).show();
				} else {
					carId = newList.get(0).getCarId();
					String carType = newList.get(0).getCarType();
					String carCode = newList.get(0).getCarCode();
					carNo = newList.get(0).getCarNo();
					bus_number.setText(carType + "-" + carNo + "\t" + carCode);
					list = newList;
				}
			} else {
				list.addAll(newList);
			}
			carAdapter.setDatalist(list);
			carAdapter.notifyDataSetChanged();
			listview.onRefreshComplete();

		}
		if (TAG.equals(requestStr)) {

			Gson gson = new Gson();
			String data = jsonObject.optString("data", "{}");
			carList = gson.fromJson(data, new TypeToken<List<TCarDto>>() {
			}.getType());
			if (carList == null || carList.size() == 0) {
				Toast.makeText(CarActivity.this, "没有空闲车辆！", Toast.LENGTH_SHORT)
						.show();
			}

			cartype_adapter.setData(carList);
			carAdapter.notifyDataSetChanged();

		}
		if ("bindCar".equals(requestStr)) {

			boolean flag = jsonObject.optBoolean("flag");
			String msg = jsonObject.optString("msg", "关联失败！");
			if (flag) {
				Intent intent = new Intent(CarActivity.this,
						RecordLineActivity.class);
				intent.putExtra("carId", carId);
				startActivity(intent);
			} else {

				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				// 如果已关联车辆，则跳转到下一级页面
				if (msg.contains("重复")) {

					Intent intent = new Intent(CarActivity.this,
							RecordLineActivity.class);
					intent.putExtra("carId", carId);
					startActivity(intent);
				}
			}

		}
		if ("unbindCar".equals(requestStr)) {

			boolean flag = jsonObject.optBoolean("flag");
			String msg = jsonObject.optString("msg", "取消关联失败！");
			if (flag) {
				requestCarHistoryList();
				Toast.makeText(this, "取消关联成功！", Toast.LENGTH_SHORT).show();
				bus_number.setText("");
				// 如果位置上传服务在运行，则关闭服务
				if (isMyServiceRunning(MyLocationService.class)) {
					Intent intent = new Intent(
							MyLocationService.MYLOCATIONSERVICE);
					// android5.0以上必须加上以下一行代码，显示指出
					intent.setPackage(getPackageName());
					stopService(intent);
				}
			} else {
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

			}

		}

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

}

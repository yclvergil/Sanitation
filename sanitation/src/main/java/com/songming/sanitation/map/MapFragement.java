package com.songming.sanitation.map;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.baidu.mapapi.model.LatLng;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseFragment;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.HttpRequestTool;
import com.songming.sanitation.frameset.utils.ImageUtil;
import com.songming.sanitation.frameset.utils.RefreshUtils;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.map.adapter.MissionListAdapter;
import com.songming.sanitation.map.model.MapFragmentListModel;
import com.songming.sanitation.map.model.WeatherInfo;
import com.songming.sanitation.map.tool.BdMapLocationUtils;
import com.songming.sanitation.map.tool.BdMapLocationUtils.BdLocationSuccessListenner;
import com.songming.sanitation.message.MessageAcitivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MapFragement extends BaseFragment implements OnClickListener,
		OnItemClickListener, OnCheckedChangeListener {
	private DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();

	private TextView topTitle = null;
	private LinearLayout layoutright;
	protected View rootView = null;

	private MissionListAdapter adapter;

	private PullToRefreshListView listview;
	private int mCurIndex = 1;

	private List<MapFragmentListModel> data1 = new ArrayList<MapFragmentListModel>();
	private ImageView head_image;
	private TextView line_sum;

	private LinearLayout gone_layout;
	private String weather;// 天气预报
	private TextView weather_text;
	private String city = "潍坊";

	@Override
	public void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showImageOnLoading(R.drawable.app_head)
				.showImageForEmptyUri(R.drawable.app_head)
				.showImageOnFail(R.drawable.app_head).cacheInMemory(false)
				.cacheOnDisk(true).considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.displayer(new RoundedBitmapDisplayer(55)).build();// 设置圆角
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		requestData();
		gone_layout.setVisibility(View.GONE);
		listview.setVisibility(View.VISIBLE);

//		imageLoader.displayImage(
//				Constants.SERVER_IP
//						+ ImageUtil.isAddImagesUrl(applications.getUser()
//								.getHeadUrl()), head_image, options);
//		line_sum.setText(applications.getUser().getUserRealName() + "您今天有"
//				+ data1.size() + "条配送线路");
		
		//获取所在城市及天气预报
		getMyCity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		// if (rootView == null) {
		rootView = inflater.inflate(R.layout.map_fragment, null);
		findViews();
		initViews();

		// }
		// ViewGroup parent = (ViewGroup) rootView.getParent();
		// if (parent != null) {
		// parent.removeView(rootView);
		// }
		return rootView;
	}

	@Override
	protected void findViews() {
		topTitle = (TextView) rootView.findViewById(R.id.topTitle);
		listview = (PullToRefreshListView) rootView.findViewById(R.id.listview);
		head_image = (ImageView) rootView.findViewById(R.id.head_image);

		line_sum = (TextView) rootView.findViewById(R.id.line_sum);

		gone_layout = (LinearLayout) rootView.findViewById(R.id.gone_layout);

		layoutright = (LinearLayout) rootView.findViewById(R.id.layoutright);
		weather_text = (TextView) rootView.findViewById(R.id.weather_text);
	}

	@Override
	protected void initViews() {
		topTitle.setText("线路");
		topTitle.setVisibility(View.VISIBLE);
		listview.setOnItemClickListener(this);
		listview.setMode(Mode.PULL_FROM_START);

		layoutright.setVisibility(View.VISIBLE);
		layoutright.setOnClickListener(this);

		listview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				MapFragement.this.hideLoading();
				// mCurIndex=0;
				mCurIndex = 1;
				requestData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				MapFragement.this.hideLoading();
				mCurIndex++;
				requestData();
			}
		});
		RefreshUtils.setRefreshPrompt(listview);
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("user_task".equals(requestStr)) {
			Toast.makeText(getActivity(), "获取数据失败！", Toast.LENGTH_SHORT).show();
		}
		listview.onRefreshComplete();
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		// TODO Auto-generated method stub
		String requestStr = "";
		listview.onRefreshComplete();
		this.hideLoading();
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("findroutepaging".equals(requestStr)) {

			String data = jsonObject.optString("data");

			if (StringUtilsExt.isEmpty(data) || data.equals("{}")
					|| data.equals("null")) {

				return;

			} else {
				Gson gson = new Gson();
				List<MapFragmentListModel> bean = gson.fromJson(data,
						new TypeToken<List<MapFragmentListModel>>() {
						}.getType());
				// 判断是否有数据可供加载
				// if (mCurIndex <= 1) {
				// orderRecordList = bean;
				// } else {
				// if (bean != null)
				// orderRecordList.addAll(bean);
				// }
				data1.clear();
				for (MapFragmentListModel model : bean) {
					if ("1".equals(model.getType())) {
						data1.add(model);
					} else if ("2".equals(model.getType())) {
					}
				}
				initlist();
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.vista_image:
			Intent intent = new Intent(getActivity(), PanoMainActivity.class);
			intent.putExtra("LatLng", new LatLng(28.2314110000, 112.9345710000));
			startActivity(intent);
			break;
		// 消息页面
		case R.id.layoutright:
			Intent intent1 = new Intent(getActivity(), MessageAcitivity.class);
			startActivity(intent1);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MapFragmentListModel model = null;
		model = data1.get(position - 1);

		if (model != null) {
			Intent intent = new Intent(getActivity(), MapDetailActivity.class);
			intent.putExtra("MapFragmentListModel", model);
			startActivity(intent);
		}
	}

	// 请求数据
	private void requestData() {

		this.showLoading("请稍后...", "refresh");
		JSONObject jsonObject = new JSONObject();
		try {
			// 设置页码
			// jsonObject.put("page", mCurIndex + "");
			jsonObject.put("userId", applications.getUser().getId());
			jsonObject.put("page", mCurIndex + "");
			// 设置条数
			jsonObject.put("pageSize", "100");
			requestHttp(jsonObject, "findroutepaging",
					Constants.FINDROUTEPAGING, Constants.SERVER_URL);
		} catch (JSONException e) {
			Toast.makeText(getActivity(), "json参数出错:" + e.getMessage(),
					Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(getActivity(), "请重新启动", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

		switch (group.getCheckedRadioButtonId()) {
		// 今日任务
		case R.id.mission_radio_1:
			initlist();
			break;
		// 历史记录
		case R.id.mission_radio_2:
			initlist();
			break;
		default:
			break;
		}
	}

	private void initlist() {
		// TODO Auto-generated method stub
		adapter = new MissionListAdapter(getActivity(), data1);
		listview.setAdapter(adapter);

		line_sum.setText(applications.getUser().getName() + "您今天有"
				+ data1.size() + "条配送线路");
		if (data1.size() == 0) {
			gone_layout.setVisibility(View.VISIBLE);
			listview.setVisibility(View.GONE);
		}
	}

	/**
	 * 获取客户端自身所在城市
	 */
	private void getMyCity() {
		BdMapLocationUtils.getInstance().startLocation(
				new BdLocationSuccessListenner() {
					@Override
					public void locationResult(double _latitude,
							double _longitude, String _city,
							String _locationAddr, String _locationType) {

						//获取天气预报
						getWeather(_city);
					}
				});
	}

	// 获取天气预报
	private void getWeather(String city) {
		try {
			String cityName = URLEncoder.encode(city.replace("市", ""), "UTF-8");
			String param = "cityname=" + cityName;
			GetWeatherTask getWeatherTask = new GetWeatherTask(getActivity());
			getWeatherTask.execute(param);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	// 获取天气预报异步任务
	class GetWeatherTask extends AsyncTask<String, String, String> {
		private Context context;

		GetWeatherTask(Context context) {
			this.context = context;
		}

		@Override
		protected String doInBackground(String... param) {
			return HttpRequestTool.requestGet(Constants.WEATHERAPI, param[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			Gson gson = new Gson();
			WeatherInfo weatherInfo = gson.fromJson(result, WeatherInfo.class);
			if (weatherInfo.getErrMsg().equals("success")) {
				weather = weatherInfo.getRetData().getWeather();
				weather_text.setText(weather);
			}
			super.onPostExecute(result);
		}
	}

}

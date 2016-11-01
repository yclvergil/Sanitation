package com.songming.sanitation.workdeal;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.RefreshUtils;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.workdeal.adapter.WorkDealAdapter;
import com.songming.sanitation.workdeal.bean.TEventDto;

/**
 * 工单处理
 * 
 * @author Administrator
 * 
 */
@SuppressLint("NewApi")
public class WorkDealActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private PullToRefreshListView prlistview;// 下拉列表
	private ListView mListView;// 下拉列表ListView
	private int mCurIndex = 1;
	private WorkDealAdapter adapter;
	private List<TEventDto> datalist = new ArrayList<TEventDto>();

	private LinearLayout wait;// 待处理
	private LinearLayout doing;// 处理中
	private LinearLayout finishit;// 已完成

	private TextView waitsum;// 待处理数
	private TextView doingsum;// 处理中数
	private TextView nulltext;

	private int i = 1;// 记录事件标签
	private Long staffid;// 员工ID

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.workdeal);

		staffid = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

		findViews();
		initViews();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		requestData(i);

		requestCount(1);
		requestCount(2);
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		wait = (LinearLayout) findViewById(R.id.wait);
		doing = (LinearLayout) findViewById(R.id.doing);
		finishit = (LinearLayout) findViewById(R.id.finishit);
		doingsum = (TextView) findViewById(R.id.doingsum);
		waitsum = (TextView) findViewById(R.id.waitsum);
		prlistview = (PullToRefreshListView) findViewById(R.id.prlistview);

	}

	@Override
	protected void initViews() {

		topTitle.setText("事件处理");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		wait.setOnClickListener(this);
		doing.setOnClickListener(this);
		finishit.setOnClickListener(this);

		mListView = prlistview.getRefreshableView();
		adapter = new WorkDealAdapter(this, datalist);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (i) {
				case 1:
					// 待受理
					Intent intent1 = new Intent(WorkDealActivity.this,
							UndisposedActivity.class);
					long nextId = datalist.get(position - 1).getId();
					//跳转标签
					intent1.putExtra("index", 1);
					intent1.putExtra("id", nextId);
					startActivity(intent1);
					break;
				case 2:
					// 正在处理界面

					Intent intent2 = new Intent(WorkDealActivity.this,
							SisposeActivity.class);
					long nextId2 = datalist.get(position - 1).getId();

					intent2.putExtra("id", nextId2);
					intent2.putExtra("executeid", datalist.get(position - 1)
							.getExecuteId());
					startActivity(intent2);
					break;
				case 3:
					// 工单完成

					Intent intent3 = new Intent(WorkDealActivity.this,
							AccomplishActivity.class);
					long nextId3 = datalist.get(position - 1).getId();
					intent3.putExtra("id", nextId3);
					startActivity(intent3);
					break;

				default:
					break;
				}

			}
		});

		prlistview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				mCurIndex = 1;
				requestData(i);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				mCurIndex++;
				requestData(i);
			}
		});
		RefreshUtils.setRefreshPrompt(prlistview);
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("worklist".equals(requestStr)) {
			Toast.makeText(this, "数据申请失败", Toast.LENGTH_SHORT).show();
			prlistview.onRefreshComplete();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("worklist", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("worklistcount1".equals(requestStr)) {

			String count = jsonObject.optString("data", "{}");

			Gson gson = new Gson();
			TEventDto bean = gson.fromJson(count, TEventDto.class);
			waitsum.setText(bean.getNum());
		}

		if ("worklistcount2".equals(requestStr)) {

			Gson gson = new Gson();
			TEventDto bean = gson.fromJson(jsonObject.optString("data", "{}"),
					TEventDto.class);
			doingsum.setText(bean.getNum());
		}

		if ("worklist".equals(requestStr)) {
			String data = jsonObject.optString("data");
			Gson gson = new Gson();

			List<TEventDto> bean = gson.fromJson(data,
					new TypeToken<List<TEventDto>>() {
					}.getType());
			// 判断是否有数据可供加载
			if (mCurIndex <= 1) {
				if (bean == null || bean.size() == 0) {
					Toast.makeText(this, "无相关信息！", Toast.LENGTH_SHORT).show();
				}
				datalist = bean;
			} else {
				if (bean != null)
					datalist.addAll(bean);
			}
			adapter.setData(datalist, i);
			adapter.notifyDataSetChanged();
			prlistview.onRefreshComplete();
		}
	}

	/**
	 * 描述：获取数据列表
	 * 
	 * */
	private void requestData(int status) {

		Long staffid = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		try {
			// 1.待完成，2.正在处理，3.已完成
			jsonObject2.put("status", status);
			jsonObject2.put("staffId", staffid);
			jsonObject.put("paramsMap", jsonObject2);
			jsonObject.put("page", mCurIndex + "");
			jsonObject.put("rows", 10);

			this.showLoading("正在请求数据……", "worklist");
			requestHttp(jsonObject, "worklist", Constants.WORKLIST,
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
	 * 描述：获取数据总数
	 * 
	 * */
	private void requestCount(int status) {

		int staffidint = Integer.parseInt(staffid + "");

		JSONObject jsonObject = new JSONObject();
		try {
			// 1.待完成，2.正在处理，
			jsonObject.put("status", status);
			jsonObject.put("staffId", staffidint);

			if (status == 1) {
				requestHttp(jsonObject, "worklistcount1",
						Constants.WORKLISTCOUNT, Constants.SERVER_URL);
			} else if (status == 2) {
				requestHttp(jsonObject, "worklistcount2",
						Constants.WORKLISTCOUNT, Constants.SERVER_URL);
			}
		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layoutbackimg:
			this.finish();

			break;
		case R.id.wait:
			// 设置点击后layout的色彩变化
			wait.setBackgroundResource(R.drawable.workdeal02);
			doing.setBackgroundResource(R.color.color_00000000);
			finishit.setBackgroundResource(R.color.color_00000000);

			for (int i = 0; i < 2; i++) {
				nulltext = (TextView) wait.getChildAt(i);
				nulltext.setTextColor(Color.parseColor("#ffffff"));
			}
			for (int i = 0; i < 2; i++) {
				nulltext = (TextView) doing.getChildAt(i);
				nulltext.setTextColor(Color.parseColor("#000000"));
			}
			nulltext = (TextView) finishit.getChildAt(0);
			nulltext.setTextColor(Color.parseColor("#000000"));

			i = 1;
			mCurIndex = 1;
			requestData(i);

			break;
		case R.id.doing:
			wait.setBackgroundResource(R.color.color_00000000);
			doing.setBackgroundResource(R.drawable.workdeal02);
			finishit.setBackgroundResource(R.color.color_00000000);
			for (int i = 0; i < 2; i++) {
				nulltext = (TextView) wait.getChildAt(i);
				nulltext.setTextColor(Color.parseColor("#000000"));
			}
			for (int i = 0; i < 2; i++) {
				nulltext = (TextView) doing.getChildAt(i);
				nulltext.setTextColor(Color.parseColor("#ffffff"));
			}
			nulltext = (TextView) finishit.getChildAt(0);
			nulltext.setTextColor(Color.parseColor("#000000"));

			i = 2;
			mCurIndex = 1;
			requestData(i);
			break;
		case R.id.finishit:
			finishit.setBackgroundResource(R.drawable.workdeal02);
			doing.setBackgroundResource(R.color.color_00000000);
			wait.setBackgroundResource(R.color.color_00000000);
			for (int i = 0; i < 2; i++) {
				nulltext = (TextView) wait.getChildAt(i);
				nulltext.setTextColor(Color.parseColor("#000000"));
			}
			for (int i = 0; i < 2; i++) {
				nulltext = (TextView) doing.getChildAt(i);
				nulltext.setTextColor(Color.parseColor("#000000"));
			}
			nulltext = (TextView) finishit.getChildAt(0);
			nulltext.setTextColor(Color.parseColor("#ffffff"));

			i = 3;
			mCurIndex = 1;
			requestData(i);
			break;

		default:
			break;
		}
	}

}

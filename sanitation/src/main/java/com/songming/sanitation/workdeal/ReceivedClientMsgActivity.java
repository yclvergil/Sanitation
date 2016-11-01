package com.songming.sanitation.workdeal;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

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
import com.songming.sanitation.workdeal.adapter.ReceivedClientMsgAdapter;
import com.songming.sanitation.workdeal.adapter.WorkDealAdapter;
import com.songming.sanitation.workdeal.bean.TEventDto;
import com.songming.sanitation.workdeal.bean.TJobAuditDetailDto;

/** 收到下级汇报 */
public class ReceivedClientMsgActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private PullToRefreshListView prlistview;// 下拉列表
	private ListView mListView;// 下拉列表ListView
	private int mCurIndex = 1;
	private ReceivedClientMsgAdapter adapter;
	private List<TJobAuditDetailDto> datalist = new ArrayList<TJobAuditDetailDto>();

	private int i = 1;// 记录事件标签
	private Long staffid;// 员工ID

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.received_client_msg_activity);

		staffid = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

		findViews();
		initViews();
		requestData();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		prlistview = (PullToRefreshListView) findViewById(R.id.received_mListView);

	}

	@Override
	protected void initViews() {

		topTitle.setText("收到下级汇报");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		mListView = prlistview.getRefreshableView();
		adapter = new ReceivedClientMsgAdapter(this, datalist);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		prlistview.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurIndex = 1;
				requestData();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurIndex++;
				requestData();
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

		if ("lowerlist".equals(requestStr)) {
			String data = jsonObject.optString("data");
			Gson gson = new Gson();

			List<TJobAuditDetailDto> bean = gson.fromJson(data,
					new TypeToken<List<TJobAuditDetailDto>>() {
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
			adapter.setData(datalist);
			adapter.notifyDataSetChanged();
			prlistview.onRefreshComplete();
		}
	}

	/**
	 * 描述：获取数据列表
	 * 
	 * */
	private void requestData() {

		Long staffid = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);
		
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		try {
			jsonObject2.put("executeId", staffid);
			jsonObject.put("paramsMap", jsonObject2);
			jsonObject.put("page", mCurIndex);
			jsonObject.put("rows", 10);

			this.showLoading("正在请求数据……", "lowerlist");
			requestHttp(jsonObject, "lowerlist", Constants.LOWER_LIST,
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
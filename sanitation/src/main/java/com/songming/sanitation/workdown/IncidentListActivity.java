package com.songming.sanitation.workdown;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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
import com.songming.sanitation.workdeal.AccomplishActivity;
import com.songming.sanitation.workdeal.SisposeActivity;
import com.songming.sanitation.workdeal.UndisposedActivity;
import com.songming.sanitation.workdeal.WorkDealActivity;
import com.songming.sanitation.workdeal.bean.TEventDto;
import com.songming.sanitation.workdown.adapter.IncidentListAdapter;

/**
 * 事件列表界面
 * 
 * @author Administrator
 * 
 */
public class IncidentListActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private PullToRefreshListView prlistview;// 下拉列表
	private ListView mListView;// 下拉列表ListView
	private int mCurIndex = 1;

	private IncidentListAdapter adapter;// 列表显示适配器

	private List<TEventDto> datalist = new ArrayList<TEventDto>();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.incident_list);
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
		prlistview = (PullToRefreshListView) findViewById(R.id.prlistview);
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		topTitle.setText("事件列表");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		mListView = prlistview.getRefreshableView();
		adapter = new IncidentListAdapter(this, datalist);

		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(IncidentListActivity.this,
				// IncidentStatusActivity.class);
				// startActivity(intent);
				int i = datalist.get(position - 1).getStatus();
				switch (i) {
				case 1:
					// 待受理
					Intent intent1 = new Intent(IncidentListActivity.this,
							UndisposedActivity.class);
					long nextId = datalist.get(position - 1).getId();
					intent1.putExtra("id", nextId);
					intent1.putExtra("whatsup", 888);
					intent1.putExtra("creatdate", datalist.get(position - 1)
							.getCreateDate());

					startActivity(intent1);
					break;
				case 2:
					// 正在处理界面

					Intent intent2 = new Intent(IncidentListActivity.this,
							SisposeActivity.class);
					long nextId2 = datalist.get(position - 1).getId();
					intent2.putExtra("id", nextId2);
					intent2.putExtra("whatsup", 888);
					startActivity(intent2);
					break;
				case 3:
					// 工单完成

					Intent intent3 = new Intent(IncidentListActivity.this,
							AccomplishActivity.class);
					long nextId3 = datalist.get(position - 1).getId();
					intent3.putExtra("id", nextId3);
					intent3.putExtra("whatsup", 888);
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
				requestData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				mCurIndex++;
				requestData();
			}
		});
		RefreshUtils.setRefreshPrompt(prlistview);
	}

	/**
	 * 描述：接收系统消息提示
	 * 
	 * */
	private void requestData() {

		Long staffid = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		try {
			jsonObject.put("page", mCurIndex);
			jsonObject.put("rows", 10);
			jsonObject.put("paramsMap", jsonObject2);
			jsonObject2.put("staffId", staffid);
			this.showLoading("正在查询中，请稍后...", "list");
			requestHttp(jsonObject, "list", Constants.WORKLIST,
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
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("list".equals(requestStr)) {
			Toast.makeText(this, "获取失败！", Toast.LENGTH_SHORT).show();
			prlistview.onRefreshComplete();
		}
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("list".equals(requestStr)) {
			String data = jsonObject.optString("data");
			Gson gson = new Gson();
			List<TEventDto> bean = gson.fromJson(data,
					new TypeToken<List<TEventDto>>() {
					}.getType());
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

package com.songming.sanitation.workdeal;

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
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.RefreshUtils;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.workdeal.adapter.ApprovalApplyListAdapter;
import com.songming.sanitation.workdeal.bean.TJobAuditDetailDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
/**
 * 我审批的（审批申请）列表页面
 * @author Administrator
 *
 */
public class ApprovalApplyListAcitivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	private TextView topTitle;
	private LinearLayout layoutback;
	private ImageView layoutbackimg;
	private List<TJobAuditDetailDto> data = new ArrayList<TJobAuditDetailDto>();
	private ApprovalApplyListAdapter adapter;
	private PullToRefreshListView listview;
	private int mCurIndex = 1;
	private static final String TAG = "ApprovalApplyListAcitivity";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.approvalapply_list_activity);
		findViews();
		initViews();

	}

	@Override
	protected void onResume() {
		super.onResume();
		requestData();
	}

	@Override
	protected void findViews() {
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		listview = (PullToRefreshListView) findViewById(R.id.listview);
	}

	@Override
	protected void initViews() {
		topTitle.setText("我审批的");
		topTitle.setVisibility(View.VISIBLE);
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		adapter = new ApprovalApplyListAdapter(this, data);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		listview.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				mCurIndex = 1;
				requestData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				mCurIndex++;
				requestData();
			}
		});
		RefreshUtils.setRefreshPrompt(listview);
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if (TAG.equals(requestStr)) {
			Toast.makeText(this, "请求数据失败", Toast.LENGTH_SHORT).show();
			listview.onRefreshComplete();
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

		if (TAG.equals(requestStr)) {
			String data1 = jsonObject.optString("data", "");
			String flag = jsonObject.optString("flag", "");
			if (flag.equals("false")) {
				Toast.makeText(this, "获取数据失败！", Toast.LENGTH_SHORT).show();
			} else {

				Gson gson = new Gson();
				List<TJobAuditDetailDto> dataNew = gson.fromJson(data1,
						new TypeToken<List<TJobAuditDetailDto>>() {
						}.getType());
				// 判断是否有数据可供加载
				if (mCurIndex <= 1) {
					data = dataNew;
				} else {
					if (dataNew != null)
						data.addAll(dataNew);
				}
				adapter.setList(data);
			}
			listview.onRefreshComplete();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layoutbackimg:
			this.finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		 Intent intent = new Intent(this, ApplyDetailActivity.class);
		 intent.putExtra("dto", data.get(position - 1));
		 startActivity(intent);
	}

	/**
	 *  请求数据
	 *  param={"page":1,"rows":20,"paramsMap":{"executeId":2}}
	 */
	private void requestData() {

		long id = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			// 设置页码
			jsonObject.put("page", mCurIndex);
			// 设置条数
			jsonObject.put("rows", 15);
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("executeId", id);
			jsonObject.put("paramsMap", jsonObject2);
			this.showLoading("请稍后...", TAG);
			requestHttp(jsonObject, TAG,
					Constants.APPROVAL_MINE, Constants.SERVER_URL);

		} catch (JSONException e) {
			Toast.makeText(this, "json参数出错:" + e.getMessage(),
					Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this, "请重新启动", Toast.LENGTH_SHORT).show();
		}
	}
}

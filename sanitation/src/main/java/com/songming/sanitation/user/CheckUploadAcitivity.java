package com.songming.sanitation.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.songming.sanitation.user.adapter.CheckUploadAdapter;
import com.songming.sanitation.user.model.TPatrolDto;


/**
 * 巡查情况上传
 * 
 * */
public class CheckUploadAcitivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private PullToRefreshListView prlistview;// 下拉列表
	private List<TPatrolDto> list = new ArrayList<TPatrolDto>();
	private int mCurIndex = 1;
	private CheckUploadAdapter adapter;
	private static final String TAG = "CheckUploadAcitivity";
	private String day;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.checkupload);

		findViews();
		initViews();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		day = sdf.format(new Date());

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mCurIndex = 1;
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

		topTitle.setText("巡查上传");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		adapter = new CheckUploadAdapter(this, list);
		prlistview.setAdapter(adapter);

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

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if (TAG.equals(requestStr)) {
			Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show();
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
			String data = jsonObject.optString("data", "");
			boolean flag = jsonObject.optBoolean("flag");
			if (!flag) {
				Toast.makeText(this, "查询失败！", Toast.LENGTH_SHORT).show();
			} else {

				Gson gson = new Gson();
				List<TPatrolDto> dataNew = new ArrayList<TPatrolDto>();

				dataNew = gson.fromJson(data,
						new TypeToken<List<TPatrolDto>>() {
						}.getType());
				if (mCurIndex <= 1) {
					list = dataNew;
					if(dataNew == null || dataNew.size() <= 0){
						Toast.makeText(this, "暂无任何消息！", Toast.LENGTH_SHORT).show();
					}
				} else {
					if (dataNew != null)
						list.addAll(dataNew);
				}
				adapter.setDatalist(list);
				adapter.notifyDataSetChanged();
			}

			prlistview.onRefreshComplete();
		}

	}

	/**
	 * 巡查情况列表查询
	 * 
	 * */
	private void requestData() {

		long staffId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("page", mCurIndex);
			jsonObject.put("rows", 20);
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("staffId", staffId);
			jsonObject2.put("createDate", day);
			jsonObject.put("paramsMap", jsonObject2);
			this.showLoading("正在获取中……", TAG);
			requestHttp(jsonObject, TAG, Constants.WORKCHECKLIST,
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

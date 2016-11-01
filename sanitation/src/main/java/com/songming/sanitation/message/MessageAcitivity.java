package com.songming.sanitation.message;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.RefreshUtils;
import com.songming.sanitation.message.adapter.MessageActivityListAdapter;
import com.songming.sanitation.message.model.MessagePushDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

@SuppressLint("NewApi")
public class MessageAcitivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {

	private TextView topTitle;
	private LinearLayout layoutback;
	private ImageView layoutbackimg;

	private List<MessagePushDTO> data = new ArrayList<MessagePushDTO>();
	private MessageActivityListAdapter adapter;
	private PullToRefreshListView listview;
	private int mCurIndex = 1;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_message);
		findViews();
		initViews();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		requestData();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		listview = (PullToRefreshListView) findViewById(R.id.listview);
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		topTitle.setText("消息");
		topTitle.setVisibility(View.VISIBLE);
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		listview.setMode(Mode.PULL_FROM_START);

		listview.setOnItemClickListener(this);
		listview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// mCurIndex=0;
				mCurIndex = 1;
				requestData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
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
		listview.onRefreshComplete();
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("findpagingmessagepushapp".equals(requestStr)) {
			Toast.makeText(this, "请求数据失败	！", Toast.LENGTH_SHORT).show();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("findpagingmessagepushapp", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		listview.onRefreshComplete();
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("findpagingmessagepushapp".equals(requestStr)) {
			String data1 = jsonObject.optString("data", "");
			String flag = jsonObject.optString("flag", "");
			if (flag.equals("false")) {
				Toast.makeText(this, "查询失败！", Toast.LENGTH_SHORT).show();
			} else {

				Gson gson = new Gson();
				List<MessagePushDTO> dataNew = new ArrayList<MessagePushDTO>();

				dataNew = gson.fromJson(data1,
						new TypeToken<List<MessagePushDTO>>() {
						}.getType());
				// 判断是否有数据可供加载
				if (dataNew.size() == 0) {
					// Toast.makeText(getActivity(), "无数据可供加载",
					// Toast.LENGTH_SHORT)
					// .show();
				} else {
					// 判断是否有数据可供加载
					if (mCurIndex <= 1) {
						data = dataNew;
					} else {
						if (dataNew != null)
							data.addAll(dataNew);
					}
				}
				adapter = new MessageActivityListAdapter(this, data);
				listview.setAdapter(adapter);
			}

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layoutbackimg:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, MessageDetailAcitivity.class);
		intent.putExtra("id", data.get(position - 1).getId());
		startActivity(intent);
	}

	// 请求数据
	private void requestData() {

		this.showLoading("请稍后...", "refresh");

		JSONObject jsonObject = new JSONObject();
		// mCurIndex++;
		try {
			// 设置页码
			jsonObject.put("page", mCurIndex + "");
			jsonObject.put("type", "1");
			jsonObject.put("userId", applications.getUser().getId());
			// 设置条数
			jsonObject.put("pageSize", "10");

			requestHttp(jsonObject, "findpagingmessagepushapp",
					Constants.FINDPAGINGMESSAGEPUSHAPP, Constants.SERVER_URL);

		} catch (JSONException e) {
			Toast.makeText(this, "json参数出错:" + e.getMessage(),
					Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this, "请重新启动", Toast.LENGTH_SHORT).show();
		}
	}
}

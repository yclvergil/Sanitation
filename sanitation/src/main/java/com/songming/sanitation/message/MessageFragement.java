package com.songming.sanitation.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseFragment;
import com.songming.sanitation.frameset.utils.RefreshUtils;
import com.songming.sanitation.map.model.MapModel;
import com.songming.sanitation.message.adapter.MessageListAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MessageFragement extends BaseFragment {

	protected View rootView = null;

	private TextView topTitle;

	private List<MapModel> data;
	private MessageListAdapter adapter;
	private PullToRefreshListView listview;
	private int mCurIndex = 1;

	@Override
	public void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		// if (rootView == null) {
		rootView = inflater.inflate(R.layout.message_fragment, null);
		findViews();
		initViews();
		initData();
		// }
		// ViewGroup parent = (ViewGroup) rootView.getParent();
		// if (parent != null) {
		// parent.removeView(rootView);
		// }
		return rootView;
	}

	private void initData() {

		data = new ArrayList<MapModel>();
		data.add(new MapModel());
		data.add(new MapModel());
		data.add(new MapModel());
		data.add(new MapModel());
		adapter = new MessageListAdapter(getActivity(), data);
		listview.setAdapter(adapter);
	}

	@Override
	protected void findViews() {
		topTitle = (TextView) rootView.findViewById(R.id.topTitle);
		listview = (PullToRefreshListView) rootView.findViewById(R.id.listview);
	}

	@Override
	protected void initViews() {
		topTitle.setVisibility(View.VISIBLE);
		topTitle.setText("消息");

		// listview.setAdapter(order);
		// listview.setOnItemClickListener(this);

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

	// 请求数据
	private void requestData() {

		// this.showLoading("请稍后...", "refresh");

		Map<String, String> jsonObject = new HashMap<String, String>();
		// mCurIndex++;
		// try {
		// // 设置页码
		// jsonObject.put("page", mCurIndex + "");
		// // 设置条数
		// jsonObject.put("pageSize", "10");
		//
		// requestHttpMap(jsonObject, "knowledgeList",
		// Constants.SELECTSERVICESBYTYPE, Constants.SERVER_URL);
		//
		// } catch (JSONException e) {
		// Toast.makeText(this, "json参数出错:" + e.getMessage(),
		// Toast.LENGTH_SHORT).show();
		//
		// } catch (Exception e) {
		// Toast.makeText(this, "请重新启动", Toast.LENGTH_SHORT).show();
		// }
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		// TODO Auto-generated method stub

	}

}

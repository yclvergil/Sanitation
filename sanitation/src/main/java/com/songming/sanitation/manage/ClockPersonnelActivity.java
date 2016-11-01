package com.songming.sanitation.manage;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.manage.adapter.ClockPersonnelAdapter;
import com.songming.sanitation.manage.bean.StaffDto;

/**
 * 考勤情况列表界面
 * 
 * @author Administrator
 * 
 */
public class ClockPersonnelActivity extends BaseActivity implements
		OnClickListener, TextWatcher {

	private LinearLayout layoutback;
	private ImageView layoutbackimg;
	private ImageView imgRight;
	private TextView topTitle;

	private Button notify_sign;// 签到提醒

	private ListView mListView;// 列表ListView
	private List<StaffDto> list;
	private int mCurIndex = 1;
	private EditText search;// 搜索的内容
	private int orgId;
	private static final String TAG = "ClockPersonnelActivity";

	private ClockPersonnelAdapter adapter;

	private String staffIds = "";

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.clock_personnel);

		orgId = getIntent().getIntExtra("orgId", -1);
		findViews();
		initViews();

		requestSignList(null);

	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		imgRight = (ImageView) findViewById(R.id.layoutsearchimg);
		notify_sign = (Button) findViewById(R.id.notify_sign);

		mListView = (ListView) findViewById(R.id.listview);

		search = (EditText) findViewById(R.id.clock_personnel_et);
	}

	@Override
	protected void initViews() {

		topTitle.setText("查看考勤");
		layoutback.setVisibility(View.VISIBLE);
		imgRight.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		imgRight.setOnClickListener(this);
		notify_sign.setOnClickListener(this);
		search.addTextChangedListener(this);
		adapter = new ClockPersonnelAdapter(this, list);
		mListView.setAdapter(adapter);

		// 设置listview滑动时隐藏软键盘
		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				im.hideSoftInputFromWindow(getWindow().getDecorView()
						.getWindowToken(), 0);
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (list.get(position).getIsSign() == 1) {
					// 已签到，跳转到地图查看最新位置
					Intent intent = new Intent(ClockPersonnelActivity.this,
							PersonalPositionActivity.class);
					intent.putExtra("staffId", list.get(position).getId());
					startActivity(intent);
				} else {
					// 未签到，无法查看
					Toast.makeText(ClockPersonnelActivity.this, list.get(position).getName()+"当前未签到！",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

	}

	/**
	 * 考勤查询
	 * 
	 */
	private void requestSignList(String searchKey) {

		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("orgId", orgId);
			if (!StringUtilsExt.isEmpty(searchKey)) {
				jsonObject.put("name", searchKey);
			}
			requestHttp(jsonObject, TAG, Constants.SIGNLIST,
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
	 * 签到提醒
	 * 
	 */
	private void notifySign() {

		JSONObject jsonObject = new JSONObject();
		try {

			jsonObject.put("orgIds", orgId + "");// orgIds - 多个用","隔开
			showLoading("正在发送中...", "notify_sign");
			requestHttp(jsonObject, "notify_sign", Constants.NOTIFY_SIGN,
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
		if (TAG.equals(requestStr)) {
			Toast.makeText(this, "获取失败！", Toast.LENGTH_SHORT).show();
		}

		if ("notify_sign".equals(requestStr)) {
			Toast.makeText(this, "催签到失败！", Toast.LENGTH_SHORT).show();
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

			Gson gson = new Gson();
			String data = jsonObject.optString("data", "{}");
			list = gson.fromJson(data, new TypeToken<List<StaffDto>>() {
			}.getType());

			if (list == null || list.size() == 0) {
				Toast.makeText(ClockPersonnelActivity.this, "没有查询到匹配的结果！", 0)
						.show();
			} else {
				for (int i = 0; i < list.size(); i++) {
					if (i == list.size() - 1) {
						staffIds += list.get(i).getId();
					} else {
						staffIds += list.get(i).getId() + ",";
					}
				}
			}
			adapter.setData(list);
			adapter.notifyDataSetChanged();
		}

		if ("notify_sign".equals(requestStr)) {
			boolean flag = jsonObject.optBoolean("flag");
			if (flag) {

				Toast.makeText(this, "催签到成功！", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "催签到失败！", Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layoutbackimg:
			this.finish();
			break;

		case R.id.layoutsearchimg:
			// 跳转到人员位置查看页面
			Intent intent = new Intent(this, PersonalPositionActivity.class);
			if (!StringUtilsExt.isEmpty(staffIds)) {
				intent.putExtra("staffIds", staffIds);
			}
			startActivity(intent);
			break;

		case R.id.notify_sign:
			// 签到提醒：崔签到
			notifySign();
			break;

		default:
			break;
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {

		String searchKey = search.getText().toString().trim();
		requestSignList(searchKey);

	}

}

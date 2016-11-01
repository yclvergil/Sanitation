package com.songming.sanitation.manage;

import java.util.ArrayList;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.manage.adapter.CarSearchAdapter;
import com.songming.sanitation.user.model.UserDto;

/**
 * 车辆轨迹搜索界面
 * 
 * @author Administrator
 * 
 */
public class CarSearchActivity extends BaseActivity implements OnClickListener,
		TextWatcher {

	private TextView cancel;// 搜索取消按键
	private TextView category;// 类别
	private EditText seek;// 搜索输入框
	private ImageView image;// 搜索框清空按钮；
	private ListView mListView;// 搜索展示列表

	private List<UserDto> list = new ArrayList<UserDto>();

	private List<UserDto> temp_list = new ArrayList<UserDto>();
	private CarSearchAdapter adapter;// 搜索列表适配器

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.manage_car_search);
		findViews();
		initViews();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		cancel = (TextView) findViewById(R.id.scearch_finish);
		category = (TextView) findViewById(R.id.scearch_category);
		seek = (EditText) findViewById(R.id.searchtext);
		image = (ImageView) findViewById(R.id.scearch_cancel);
		mListView = (ListView) findViewById(R.id.scearch_listview);
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		category.setText("司机");
		cancel.setOnClickListener(this);
		image.setOnClickListener(this);

		seek.addTextChangedListener(this);
		adapter = new CarSearchAdapter(this, list);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				long staffId = list.get(position).getId();
				Intent intent = new Intent(CarSearchActivity.this,
						CarTrackActivity.class);
				intent.putExtra("staffId", staffId);
				startActivity(intent);
			}
		});
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
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("carsearch".equals(requestStr)) {
			Toast.makeText(this, "获取失败！", Toast.LENGTH_SHORT).show();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("carsearch", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("carsearch".equals(requestStr)) {

			Gson gson = new Gson();
			String data = jsonObject.optString("data", "{}");

			temp_list = gson.fromJson(data, new TypeToken<List<UserDto>>() {
			}.getType());

			list.clear();
			for (int i = 0; i < temp_list.size(); i++) {
				//筛选司机、车队长
				if (temp_list.get(i).getStationId() == 5 || temp_list.get(i).getStationId() == 2) {
					list.add(temp_list.get(i));
				}
			}
			if (list == null || list.size() == 0) {
				Toast.makeText(CarSearchActivity.this, "没有查询到匹配的结果！",
						Toast.LENGTH_SHORT).show();
			}
			adapter.setData(list);
			adapter.notifyDataSetChanged();

		}
	}

	/**
	 * 查询联系人
	 * 
	 * @param searchKey
	 */
	private void requestData(String searchKey) {

		int orgId = SharedPreferencesUtils.getIntValue(this,
				SharedPreferencesUtils.ORG_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("orgId", orgId);
			jsonObject.put("searchParam", searchKey);
			requestHttp(jsonObject, "carsearch", Constants.CONTACTS,
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

		case R.id.scearch_cancel:
			// 搜索文字清空
			seek.setText("");
			break;
		case R.id.scearch_finish:
			this.finish();

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
		// TODO Auto-generated method stub
		if (s.length() != 0) {
			String searchKey = seek.getText().toString().trim();
			if (!StringUtilsExt.isEmpty(searchKey)) {
				requestData(searchKey);
			}
		} else {
			adapter.setData(null);
			adapter.notifyDataSetChanged();
		}
	}

}

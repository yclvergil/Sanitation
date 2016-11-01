package com.songming.sanitation.user;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
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
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.message.ApprovalOpenAcitivity;
import com.songming.sanitation.user.adapter.SearchAdapter;
import com.songming.sanitation.user.model.UserDto;

/**
 * 通讯录搜索界面
 * 
 * @author Administrator
 * 
 */
public class SearchActivity extends BaseActivity implements OnClickListener,
		TextWatcher {

	private LinearLayout layoutback;
	private TextView topTitle;
	private EditText searchtext;
	private ImageView layoutbackimg;
	private ImageView searchbtn;

	private List<UserDto> list = new ArrayList<UserDto>();
	private ListView listview;
	private SearchAdapter adapter;
	private boolean fromApproval;
	private boolean fromPatrol;
	private boolean fromContacts;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.search_activity);

		fromApproval = getIntent().getBooleanExtra("fromApproval", false);
		fromPatrol = getIntent().getBooleanExtra("fromPatrol", false);
		fromContacts = getIntent().getBooleanExtra("fromContacts", false);
		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		searchtext = (EditText) findViewById(R.id.searchtext);
		searchbtn = (ImageView) findViewById(R.id.searchbtn);
		listview = (ListView) findViewById(R.id.listview);

		searchtext.setFocusable(true);
		searchtext.requestFocus();

	}

	@Override
	protected void initViews() {

		topTitle.setText("搜索");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		// searchbtn.setOnClickListener(this);
		searchtext.addTextChangedListener(this);
		adapter = new SearchAdapter(this, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (fromApproval) {
					// 从发起审批页面过来拿数据的
					Intent intent = new Intent(SearchActivity.this,
							ApprovalOpenAcitivity.class);
					intent.putExtra("user", list.get(position));
					setResult(RESULT_OK, intent);
					SearchActivity.this.finish();
				} else if (fromPatrol) {
					// 从巡查页面来
					Intent intent = new Intent(SearchActivity.this,
							CheckUpDetailsActivity.class);
					intent.putExtra("id", list.get(position).getId());
					startActivity(intent);
				} else {
					String phone = list.get(position).getPhone();
					if (StringUtilsExt.isEmpty(phone)) {
						return;
					}

					Intent it = new Intent("android.intent.action.CALL", Uri
							.parse("tel:" + phone));
					startActivity(it);
				}

			}
		});
		// 设置listview滑动时隐藏软键盘
		listview.setOnScrollListener(new OnScrollListener() {

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
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("contacts".equals(requestStr)) {
			Toast.makeText(this, "获取失败！", Toast.LENGTH_SHORT).show();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("contacts", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("contacts".equals(requestStr)) {

			Gson gson = new Gson();
			String data = jsonObject.optString("data", "{}");
			list = gson.fromJson(data, new TypeToken<List<UserDto>>() {
			}.getType());
			if (list == null || list.size() == 0) {
				Toast.makeText(SearchActivity.this, "没有查询到匹配的结果！", 0).show();
			}else{
				if (fromPatrol) {
					// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
					long stationId = SharedPreferencesUtils.getLongValue(this,
							SharedPreferencesUtils.STATIONID, -1);
					if (stationId == 1) {
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).getStationId() == 6) {
								list.remove(i);
								i--;
							}
						}
					} else if (stationId == 2) {
						for (int i = 0; i < list.size(); i++) {
							long id = list.get(i).getStationId();
							if (id == 6 || id == 1 || id == 4) {
								list.remove(i);
								i--;
							}
						}
					} else if (stationId == 3) {
						for (int i = 0; i < list.size(); i++) {
							long id = list.get(i).getStationId();
							if (id == 6 || id == 1 || id == 4) {
								list.remove(i);
								i--;
							}
						}
					} else if (stationId == 4) {
						for (int i = 0; i < list.size(); i++) {
							long id = list.get(i).getStationId();
							if (id == 6 || id == 1) {
								list.remove(i);
								i--;
							}
						}
					} else if (stationId == 5) {
						for (int i = 0; i < list.size(); i++) {
							long id = list.get(i).getStationId();
							if (id == 6 || id == 1 || id == 4 || id == 2
									|| id == 3) {
								list.remove(i);
								i--;
							}
						}
					}
				}else if(fromContacts){
					//去掉一级人员
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getStationId() == 6) {
							list.remove(i);
							i--;
						}
					}
				}
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
			requestHttp(jsonObject, "contacts", Constants.CONTACTS,
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
		switch (v.getId()) {
		case R.id.layoutbackimg:
			this.finish();

			break;

		default:
			break;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {

		if (s.length() != 0) {
			String searchKey = searchtext.getText().toString().trim();
			if (!StringUtilsExt.isEmpty(searchKey)) {
				requestData(searchKey);
			}
		} else {
			adapter.setData(null);
			adapter.notifyDataSetChanged();
		}

	}

}

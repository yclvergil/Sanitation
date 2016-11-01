package com.songming.sanitation.user;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.songming.sanitation.manage.RecordLineActivity;
import com.songming.sanitation.user.adapter.CheckUpAdapter;
import com.songming.sanitation.user.adapter.PopAdapter;
import com.songming.sanitation.user.model.UserDto;

/**
 * 巡查情况界面
 * 
 * @author Administrator
 * 
 */
public class CheckUpActivity extends BaseActivity implements OnClickListener {
	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private LinearLayout layout_site;// 区域选择
	private PopupWindow pop_site;// 区域显示下拉框

	private ListView mListView;// 列表ListView
	private ListView listview;// popupwindow里面展示信息的listview

	private List<UserDto> list = new ArrayList<UserDto>();// 人员列表数据
	private CheckUpAdapter madapter;// 下拉刷新列表适配器
	private PopAdapter adapter;// PopupWindow区域适配器

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		setContentView(R.layout.checkup_activity);
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

		layout_site = (LinearLayout) findViewById(R.id.checkup_layou_site);
		mListView = (ListView) findViewById(R.id.listview);

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		topTitle.setText("巡查情况");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		layout_site.setOnClickListener(this);
		// 区域下拉列表
		adapter = new PopAdapter(this, null);

		// 人员信息
		madapter = new CheckUpAdapter(this, list);
		mListView.setAdapter(madapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(CheckUpActivity.this,
						CheckUpDetailsActivity.class);
				intent.putExtra("id", list.get(position).getId());
				startActivity(intent);
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
			List<UserDto> list1 = gson.fromJson(data,
					new TypeToken<List<UserDto>>() {
					}.getType());
			if (list1 == null || list1.size() == 0) {
				// Toast.makeText(CheckUpActivity.this, "没有任何信息！", 0).show();
			} else {
				// 因为只有主管拥有巡查上报的权利，故此处筛选主管
				for (int i = 0; i < list1.size(); i++) {
					if (list1.get(i).getStationId() == 3) {
						list.add(list1.get(i));
					}
				}
			}
			madapter.setData(list);
			madapter.notifyDataSetChanged();

		}

	}

	/**
	 * 查询人员列表
	 */
	private void requestData() {

		int orgId = SharedPreferencesUtils.getIntValue(this,
				SharedPreferencesUtils.ORG_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("orgId", orgId);
			this.showLoading("正在查询……", "contacts");
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

	// 地区点击下拉框，显示地区列表
	private void PopSite() {
		View PopView = getLayoutInflater().inflate(R.layout.checkup_pop, null);
		pop_site = new PopupWindow(PopView, 300, LayoutParams.WRAP_CONTENT,
				true);
		// 焦点设置，点击区域外消失参数必须为true
		pop_site.setFocusable(true);
		// 点击区域外消失必须设置此方法
		pop_site.setBackgroundDrawable(new BitmapDrawable());
		// 选择在某一个控件下面
		pop_site.showAsDropDown(layout_site);
		listview = (ListView) PopView.findViewById(R.id.pop_listview);
		listview.setAdapter(adapter);
		// 下拉列表item点击事件
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				startActivity(new Intent(CheckUpActivity.this,
						CheckUpActivity.class));
				if (null != pop_site && pop_site.isShowing()) {
					pop_site.dismiss();
				}

			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.checkup_layou_site:
			// 区域点击 出现区域列表
			PopSite();
			break;

		case R.id.layoutbackimg:
			this.finish();
			break;

		default:
			break;
		}
	}

}

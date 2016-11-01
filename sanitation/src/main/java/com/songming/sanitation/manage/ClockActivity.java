package com.songming.sanitation.manage;

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
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.manage.adapter.ClockAdapter;
import com.songming.sanitation.manage.bean.OrganizationDto;
import com.songming.sanitation.manage.bean.SysStationRankDto;
import com.songming.sanitation.user.UpKeepActivity;

/**
 * 考勤查询界面
 * 
 * 分地区查询
 * 
 * @author Administrator
 * 
 */
public class ClockActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private ImageView layoutbackimg;
	private TextView topTitle;
	private ImageView imgRight;

	private ListView listview;// 区域展示列表
	private List<SysStationRankDto> list = new ArrayList<SysStationRankDto>();

	private EditText search;// 搜索的内容
	private ClockAdapter adapter;// 地区列表适配器
	private String staffIds = "";// 参数
	private String pushStaffIds = "";// 签到参数

	private Button notify_sign;// 签到提醒
	private Long stationId;// 员工ID

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.clock_activity);

		stationId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STATIONID, -1);
		findViews();
		initViews();
		requestOrgList();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		imgRight = (ImageView) findViewById(R.id.layoutsearchimg);

		notify_sign = (Button) findViewById(R.id.notify_sign);
		listview = (ListView) findViewById(R.id.clock_listview);

		search = (EditText) findViewById(R.id.clock_et);

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		topTitle.setText("人员");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		adapter = new ClockAdapter(this, null);
		imgRight.setVisibility(View.VISIBLE);
		imgRight.setOnClickListener(this);
		notify_sign.setOnClickListener(this);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ClockActivity.this,
						ClockUnderlingActivity.class);
				intent.putExtra("thistaffid", list.get(position).getStaffId());
				startActivity(intent);
			}
		});
	}

	/**
	 * 人员下属查询
	 */
	private void requestOrgList() {

		long staffid = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("staffId", staffid);
			this.showLoading("正在查询……", "contacts");
			requestHttp(jsonObject, "contacts", Constants.ORGLIST,
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
			jsonObject.put("staffIds", pushStaffIds + "");// orgIds - 多个用","隔开
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
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("contacts".equals(requestStr)) {
			Toast.makeText(this, "获取失败！", Toast.LENGTH_SHORT).show();
		}
		if ("notify_sign".equals(requestStr)) {
			Toast.makeText(this, "催签到失败！", Toast.LENGTH_SHORT).show();
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
			list = gson.fromJson(data,
					new TypeToken<List<SysStationRankDto>>() {
					}.getType());

			for (int i = 0; i < list.size(); i++) {
				if (i == list.size() - 1) {
					staffIds += list.get(i).getStaffId();
				} else {
					staffIds += list.get(i).getStaffId() + ",";
				}

				if (list.get(i).getIsSign() == 0) {

					if (i == list.size() - 1) {
						pushStaffIds += list.get(i).getStaffId();
					} else {
						pushStaffIds += list.get(i).getStaffId() + ",";
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
			// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
			// 跳转到人员位置查看页面
			if (stationId == 1 || stationId == 2 || stationId == 3
					|| stationId == 4 || stationId == 5 || stationId == 6) {
				Intent intent = new Intent(this, PersonalPositionActivity.class);
				if (!StringUtilsExt.isEmpty(staffIds)) {
					intent.putExtra("staffIds", staffIds);
				}
				startActivity(intent);
			} else {
				Toast.makeText(this,
						getResources().getString(R.string.nopower),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.notify_sign:
			// 签到提醒：崔签到
			// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
			if (stationId == 1 || stationId == 2 || stationId == 3
					|| stationId == 4 || stationId == 5 || stationId == 6) {

				notifySign();
			} else {
				Toast.makeText(this,
						getResources().getString(R.string.nopower),
						Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}
	}

}

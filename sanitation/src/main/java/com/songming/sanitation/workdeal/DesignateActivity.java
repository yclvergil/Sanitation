package com.songming.sanitation.workdeal;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.songming.sanitation.user.model.UserDto;
import com.songming.sanitation.workdeal.adapter.DesignateAdapter;

/**
 * 指派人员界面
 * 
 * @author Administrator
 * 
 */
public class DesignateActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private GridView mGridView;// 指派人员显示列表
	private List<UserDto> list = new ArrayList<UserDto>();// 队员集合
	private DesignateAdapter adapter;// 指派人员显示列表适配器

	private Button confirm;// 指派人员确定按键;
	private Button cancel;// 指派人员取消按键

	private long taskId;// 事件id
	private String executeId = "";
	private List<String> executeIds = new ArrayList<String>();
	private List<String> select = new ArrayList<String>();
	private EditText designate_input;// 安排工作描述输入
	/** 标题 */
	private String title = null;
	/** 内容 */
	private String content = null;
	/** 跳转标签 1.事件指派；2.发布公告 ；3.安排工作 */
	private int index;

	private TextView designate_appoint;// 工单指派标题

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.designate);

		index = getIntent().getIntExtra("index", -1);
		if (index == 1) {
			// 事件指派
			taskId = getIntent().getLongExtra("taskId", -1);
		} else if (index == 2) {
			// 发布公告
			content = getIntent().getStringExtra("content");
			title = getIntent().getStringExtra("title");
		} else if (index == 3) {
			// 安排工作
			content = getIntent().getStringExtra("content");
			title = getIntent().getStringExtra("title");
			
		} else if (index == 4) {
			// 向上级汇报
			title = getIntent().getStringExtra("title");
			content = getIntent().getStringExtra("content");

		} else if (index == 5) {

		}

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

		mGridView = (GridView) findViewById(R.id.personnel);
		confirm = (Button) findViewById(R.id.designate_confirm);
		cancel = (Button) findViewById(R.id.designate_cancel);
		designate_appoint = (TextView) findViewById(R.id.designate_appoint);

		designate_input = (EditText) findViewById(R.id.designate_input);

	}

	private boolean isContains(List<String> select, String str) {
		if (select == null || select.size() == 0) {
			return false;
		}
		for (int i = 0; i < select.size(); i++) {
			if (select.get(i).equals(str)) {
				return true;
			}
		}
		return false;

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub

		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);

		if (index == 1) {
			topTitle.setText("工单处理");
			designate_appoint.setText("工单指派");
		} else if (index == 2) {
			confirm.setText("发送全员");
			cancel.setText("确认发送");
			topTitle.setText("发布公告");
			designate_appoint.setText("发布公告");
			designate_appoint.setVisibility(View.GONE);
			cancel.setTextColor(getResources().getColor(R.color.white));
			cancel.setBackgroundResource(R.drawable.undisposed_accept);
		} else if (index == 3) {
			confirm.setText("安排人员");
			topTitle.setText("安排工作");
			designate_appoint.setVisibility(View.GONE);
//			designate_input.setVisibility(View.VISIBLE);
		} else if (index == 4) {
			confirm.setText("汇报上级");
			designate_appoint.setText("确认汇报");
			designate_appoint.setVisibility(View.VISIBLE);
		}

		adapter = new DesignateAdapter(this, list);
		mGridView.setAdapter(adapter);

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (!isContains(select, position + "")) {
					select.add(position + "");
					executeIds.add(list.get(position).getId() + "");
				} else {
					select.remove(position + "");
					executeIds.remove(list.get(position).getId() + "");
				}

				adapter.setSelect(select);
			}
		});

	}

	/**
	 * 查询team
	 */
	private void requestData() {

		int orgId = SharedPreferencesUtils.getIntValue(this,
				SharedPreferencesUtils.ORG_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			if (index == 1) {
				// 指派事件 查询本级，下级所有人员
				jsonObject.put("orgId", orgId);
				jsonObject.put("eventId", taskId);
				this.showLoading("正在查询……", "contacts");
				requestHttp(jsonObject, "contacts", Constants.TEAMLIST,
						Constants.SERVER_URL);
			}
			if (index == 2 || index == 3) {
				// 发布公告 调用本机构所有本级，下级人员
				jsonObject.put("orgId", orgId);
				this.showLoading("正在查询……", "staffcomment");
				requestHttp(jsonObject, "staffcomment",
						Constants.STAFF_COMMENT, Constants.SERVER_URL);
			}
			if (index == 4) {
				// 汇报上级
				jsonObject.put("orgId", orgId);
				this.showLoading("正在查询人员……", "jobcontacts");
				requestHttp(jsonObject, "jobcontacts", Constants.JOB_CONTACTS,
						Constants.SERVER_URL);
				/*requestHttp(jsonObject, "jobcontacts",
						Constants.STAFF_COMMENT, Constants.SERVER_URL);*/
			}

		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 工单处理中的 指派功能；以及安排工作中的 指派功能
	 */
	private void assign() {

		if (select == null || select.size() == 0) {
			Toast.makeText(this, "请先选定指派人员！", Toast.LENGTH_SHORT).show();
			return;
		}

		long id = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

		Integer createOrgId = SharedPreferencesUtils.getIntValue(this,
				SharedPreferencesUtils.ORG_ID, -1);

		// 遍历所有被指派的人的ID
		for (int i = 0; i < executeIds.size() - 1; i++) {
			executeId = executeId + executeIds.get(i) + ",";
		}
		executeId = executeId + executeIds.get(executeIds.size() - 1);
		JSONObject jsonObject = new JSONObject();
		try {
			if (index == 1) {
				// 工单处理 中的 指派人员
				jsonObject.put("id", taskId);
				jsonObject.put("assignId", id);
				jsonObject.put("executeIds", executeId);
				this.showLoading("正在指派……", "assign");
				requestHttp(jsonObject, "assign", Constants.ASSIGN,
						Constants.SERVER_URL);
			} else if (index == 3) {
				// 安排工作中的 指派人员
				jsonObject.put("jobTitle", title);
				// 输入工作内容
				jsonObject.put("jobContent", content);
				jsonObject.put("createOrgId", createOrgId);
				jsonObject.put("createId", id);
				jsonObject.put("executeIds", executeId);
				this.showLoading("正在安排人员……", "dayjobassign");
				requestHttp(jsonObject, "dayjobassign",
						Constants.DAYJOB_ASSIGN, Constants.SERVER_URL);
			} else if (index == 4) {
				// 汇报上级
				// 安排工作中的 指派人员
				jsonObject.put("jobTitle", title);
				// 输入工作内容
					jsonObject.put("jobContent", designate_input.getText()
							.toString());
				jsonObject.put("createOrgId", createOrgId);
				jsonObject.put("createId", id);
				jsonObject.put("executeIds", executeId);
				this.showLoading("正在查询人员……", "darjobreport");
				requestHttp(jsonObject, "darjobreport",
						Constants.DARJOB_REPORT, Constants.SERVER_URL);
			}

		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 发布公告
	 */
	private void sendMassage(int tymp) {

		if (tymp == 1) {

			if (select == null || select.size() == 0) {
				Toast.makeText(this, "请先选定指派人员！", Toast.LENGTH_SHORT).show();
				return;
			}

			// 遍历所有被指派的人的ID
			for (int i = 0; i < executeIds.size() - 1; i++) {
				executeId = executeId + executeIds.get(i) + ",";
			}
			executeId = executeId + executeIds.get(executeIds.size() - 1);
		} else {
			for (int i = 0; i < list.size(); i++) {
				executeId = executeId + list.get(i).getId() + ",";
			}
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("title", title);
			jsonObject.put("description", content);
			jsonObject.put("staffIds", executeId);
			if (tymp == 1) {
				this.showLoading("正在发布公告……", "sendMassage");
			} else {
				this.showLoading("正在发布全员公告……", "sendMassage");
			}
			requestHttp(jsonObject, "sendMassage", Constants.SEND_MASSAGE,
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
		if ("assign".equals(requestStr)) {
			if (index == 1) {
				Toast.makeText(DesignateActivity.this, "指派失败！",
						Toast.LENGTH_SHORT).show();
			} else if (index == 3) {
				Toast.makeText(DesignateActivity.this, "安排失败！",
						Toast.LENGTH_SHORT).show();
			}
		}
		if ("dayjobassign".equals(requestStr)) {
			Toast.makeText(this, "安排失败！", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(DesignateActivity.this, "当前没有人员可指派！",
						Toast.LENGTH_SHORT).show();
			}
			adapter.setData(list);
			mGridView.setAdapter(adapter);

		}
		if ("assign".equals(requestStr)) {

			boolean flag = jsonObject.optBoolean("flag");
			String msg = jsonObject.optString("msg", "指派失败！");
			if (flag) {
				if (index == 1) {
					Toast.makeText(DesignateActivity.this, "指派成功！",
							Toast.LENGTH_SHORT).show();
				} else if (index == 3) {
					Toast.makeText(DesignateActivity.this, "安排成功！",
							Toast.LENGTH_SHORT).show();
				}

				this.finish();
			} else {
				Toast.makeText(DesignateActivity.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
		}

		if ("staffcomment".equals(requestStr)) {
			Gson gson = new Gson();
			String data = jsonObject.optString("data", "{}");
			list = gson.fromJson(data, new TypeToken<List<UserDto>>() {
			}.getType());
			if (list == null || list.size() == 0) {
				Toast.makeText(DesignateActivity.this, "当前没有人员可接收公告！",
						Toast.LENGTH_SHORT).show();
			}
			adapter.setData(list);
			mGridView.setAdapter(adapter);

		}

		if ("sendMassage".equals(requestStr)) {
			boolean flag = jsonObject.optBoolean("flag");
			String msg = jsonObject.optString("msg", "发布失败！");
			if (flag) {
				Toast.makeText(DesignateActivity.this, "发布成功！",
						Toast.LENGTH_SHORT).show();
				this.finish();
			} else {
				Toast.makeText(DesignateActivity.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
		}

		if ("dayjobassign".equals(requestStr)) {
			boolean flag = jsonObject.optBoolean("flag");
			String msg = jsonObject.optString("msg", "安排失败！");
			if (flag) {
				Toast.makeText(DesignateActivity.this, "安排成功！",
						Toast.LENGTH_SHORT).show();
				this.finish();
			} else {
				Toast.makeText(DesignateActivity.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
		}

		if ("jobcontacts".equals(requestStr)) {
			Gson gson = new Gson();
			String data = jsonObject.optString("data", "{}");
			list = gson.fromJson(data, new TypeToken<List<UserDto>>() {
			}.getType());
			if (list == null || list.size() == 0) {
				Toast.makeText(DesignateActivity.this, "当前无法汇报上级！",
						Toast.LENGTH_SHORT).show();
			}
			/*for (UserDto user : list) {
				long stationid = SharedPreferencesUtils.getLongValue(this, SharedPreferencesUtils.STATIONID, 1000);
				Log.d("DesignateActivity", user.getStationId() + "~~~~~~" + stationid);
				if (user.getStationId() >= stationid) {
					list.remove(user);
				}
			}*/
			adapter.setData(list);
			mGridView.setAdapter(adapter);
		}

		if ("darjobreport".equals(requestStr)) {
			boolean flag = jsonObject.optBoolean("flag");
			String msg = jsonObject.optString("msg", "汇报失败！");
			if (flag) {
				Toast.makeText(DesignateActivity.this, "汇报成功！",
						Toast.LENGTH_SHORT).show();
				this.finish();
			} else {
				Toast.makeText(DesignateActivity.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.designate_confirm:
			// 确定指派人员按键，发送全员公告
			if (index == 1) {
				// 事件指派
				assign();
			} else if (index == 2) {
				// 发布公告，全员发送
				sendMassage(0);
			} else if (index == 3) {
				// 安排工作
				assign();
			} else if (index == 4) {
				// 汇报上级
				assign();
			}
			break;

		case R.id.designate_cancel:
			// 取消指派人员按键;选中人员发送公告

			if (index == 1) {
				this.finish();

			} else if (index == 2) {
				// 发布公告，指定发送
				sendMassage(1);
			} else if (index == 3) {
				// 安排工作
				this.finish();
			} else if (index == 4) {
				// 汇报上级
				this.finish();
			}

			break;
		case R.id.layoutbackimg:

			this.finish();
			break;

		default:
			break;
		}
	}

}

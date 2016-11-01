package com.songming.sanitation.workdeal;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.workdown.DownloadAcitivity;
import com.songming.sanitation.workdown.IncidentListActivity;

/** 突发事件 */
public class IncidentActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private LinearLayout message_layout;// 事件上报
	private LinearLayout incident_list;// 我的事件
	private LinearLayout contacts_layout;// 查看事件状态
	private Long stationId;// 员工ID

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.incident_activity);
		stationId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STATIONID, -1);
		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		message_layout = (LinearLayout) findViewById(R.id.incident_message_layout);
		incident_list = (LinearLayout) findViewById(R.id.incident_list);
		contacts_layout = (LinearLayout) findViewById(R.id.incident_contacts_layout);

	}

	@Override
	protected void initViews() {
		topTitle.setText("突发事件");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		message_layout.setOnClickListener(this);
		incident_list.setOnClickListener(this);
		contacts_layout.setOnClickListener(this);

	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {

	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		switch (id) {
		case R.id.layoutbackimg:
			this.finish();
			break;
		case R.id.incident_message_layout:
			// 上报事件
			startActivity(new Intent(this, DownloadAcitivity.class));
			break;
		case R.id.incident_list:
			// 工单处理
			startActivity(new Intent(this, WorkDealActivity.class));
			break;
		case R.id.incident_contacts_layout:
			// 事件列表查看
			startActivity(new Intent(this, IncidentListActivity.class));
			break;

		default:
			break;
		}
	}
}

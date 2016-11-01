package com.songming.sanitation.workdown;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;

/**
 * 事件上报一级页面
 * 
 * @author Administrator
 * 
 */
public class WorkDownloadAcitivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private LinearLayout message_layout;// 事件上报
	private LinearLayout contacts_layout;// 查看事件状态

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.workdownroad);

		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		message_layout = (LinearLayout) findViewById(R.id.message_layout);
		contacts_layout = (LinearLayout) findViewById(R.id.contacts_layout);

	}

	@Override
	protected void initViews() {

		topTitle.setText("事件上报");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		message_layout.setOnClickListener(this);
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
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.message_layout:
			// 上报事件
			startActivity(new Intent(this, DownloadAcitivity.class));

			break;
		case R.id.contacts_layout:
			// 事件列表查看
			startActivity(new Intent(this, IncidentListActivity.class));
			break;

		case R.id.layoutbackimg:
			this.finish();

			break;
		default:
			break;
		}
	}

}

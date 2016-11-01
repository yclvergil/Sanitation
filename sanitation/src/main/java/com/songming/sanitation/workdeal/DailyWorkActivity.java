package com.songming.sanitation.workdeal;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.utils.ToastUtil;

/**
 * 事件 工作页面
 * 
 * @author Administrator
 * 
 */
public class DailyWorkActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private LinearLayout receivedboss_layout;// 收到上级指令
	private LinearLayout receivedclient_layout;// 收到下级汇报
	private LinearLayout report_layout;// 我要汇报情况
	private LinearLayout arrange_layout;// 我要安排工作

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.dailywork_activity);

		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		receivedboss_layout = (LinearLayout) findViewById(R.id.receivedboss_layout);
		receivedclient_layout = (LinearLayout) findViewById(R.id.receivedclient_layout);
		report_layout = (LinearLayout) findViewById(R.id.report_layout);
		arrange_layout = (LinearLayout) findViewById(R.id.arrange_layout);

	}

	@Override
	protected void initViews() {

		topTitle.setText("日常工作");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		receivedboss_layout.setOnClickListener(this);
		receivedclient_layout.setOnClickListener(this);
		report_layout.setOnClickListener(this);
		arrange_layout.setOnClickListener(this);
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
		case R.id.receivedboss_layout:
			// 收到上级指令
			startActivity(new Intent(this, ReceivedBossMsgActivity.class));
			
			break;
		case R.id.receivedclient_layout:
			// 收到下级汇报
			startActivity(new Intent(this, ReceivedClientMsgActivity.class));

			break;
		case R.id.report_layout:
			// 我要汇报情况
			startActivity(new Intent(this, ReportActivity.class));

			break;
		case R.id.arrange_layout:
			// 我要安排工作
			startActivity(new Intent(this, ArrangeWorkActivity.class));
			break;

		case R.id.layoutbackimg:
			this.finish();

			break;
		default:
			break;
		}
	}

}

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
import com.songming.sanitation.manage.FunctionDescActivity;

/**
 * 事件 工作页面
 * 
 * @author Administrator
 * 
 */
public class WorkActivity extends BaseActivity implements OnClickListener {
	
	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;
	private ImageView layoutsearchimg;

	private LinearLayout announcement;// 公告
	private LinearLayout incident;// 事件
	private LinearLayout patrol;// 巡查
	private LinearLayout examine;// 审批
	private LinearLayout workmission;// 工作任务
	private LinearLayout driver;// 司机入口

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.work_activity);

		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		layoutsearchimg = (ImageView) findViewById(R.id.layoutsearchimg);

		announcement = (LinearLayout) findViewById(R.id.announcement);
		incident = (LinearLayout) findViewById(R.id.incident);
		patrol = (LinearLayout) findViewById(R.id.patrol);
		examine = (LinearLayout) findViewById(R.id.examine);
		workmission = (LinearLayout) findViewById(R.id.workmission);
		driver = (LinearLayout) findViewById(R.id.driver);

	}

	@Override
	protected void initViews() {

		topTitle.setText("工作");
		layoutback.setVisibility(View.VISIBLE);
		layoutsearchimg.setVisibility(View.VISIBLE);
		layoutsearchimg.setImageResource(R.drawable.ic_question);
		layoutbackimg.setOnClickListener(this);
		layoutsearchimg.setOnClickListener(this);
		announcement.setOnClickListener(this);
		incident.setOnClickListener(this);
		patrol.setOnClickListener(this);
		examine.setOnClickListener(this);
		workmission.setOnClickListener(this);
		driver.setOnClickListener(this);
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
		case R.id.announcement:
			// 公告通知
			startActivity(new Intent(this, NoticeActivity.class));

			// startActivity(new Intent(this, DownloadAcitivity.class));

			break;
		case R.id.incident:
			// 突发事件
			startActivity(new Intent(this, IncidentActivity.class));

			// startActivity(new Intent(this, IncidentListActivity.class));
			break;
		case R.id.patrol:
			// 现场巡查
			// startActivity(new Intent(this, DownloadAcitivity.class));
			startActivity(new Intent(this, PatrolActivity.class));
			
			break;
			
			
		case R.id.examine:
			// 审批请示
			startActivity(this, ApprovalActivity.class);
			break;
		case R.id.workmission:
			// 日常工作
			startActivity(new Intent(this, DailyWorkActivity.class));
			// Toast.makeText(this, getResources().getString(R.string.notopen),
			// Toast.LENGTH_SHORT).show();

			break;
		case R.id.driver:
			// 司机入口
			startActivity(new Intent(this, DriverlActivity.class));
			break;

		case R.id.layoutbackimg:
			this.finish();

			break;
		case R.id.layoutsearchimg:
			Intent intent = new Intent(this, FunctionDescActivity.class);
			intent.putExtra("fromWork", true);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}

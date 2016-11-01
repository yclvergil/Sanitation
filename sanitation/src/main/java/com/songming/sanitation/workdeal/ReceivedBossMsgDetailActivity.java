package com.songming.sanitation.workdeal;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.workdeal.bean.TDayJobDto;

public class ReceivedBossMsgDetailActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private TextView time;// 工单生成时间
	private TextView headline;// 工单内容标题
	private TextView creatname;// 发起人
	private TextView content; // 内容
	private TDayJobDto dto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receivedbossmsg_detail);
		dto = (TDayJobDto) getIntent().getSerializableExtra("dto");
		findViews();
		initViews();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		time = (TextView) findViewById(R.id.undisposed_time);
		headline = (TextView) findViewById(R.id.undisposed_headline);
		creatname = (TextView) findViewById(R.id.creatname);
		content = (TextView) findViewById(R.id.content);

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub

		topTitle.setText("消息详情");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		time.setText(dto.getJobDate());
		headline.setText(dto.getJobTitle());
		creatname.setText(dto.getCreateName());
		content.setText(dto.getJobContent());
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		this.hideLoading();
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.layoutbackimg:
			this.finish();
			break;

		default:
			break;
		}
	}
}

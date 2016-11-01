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
import com.songming.sanitation.workdeal.bean.TJobAuditDetailDto;

/** 收到下级汇报详情页面 */
public class ReceivedClientMsgDetailsActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private TextView received_title;
	private TextView received_content;
	private TextView received_name;
	private TextView received_time;
	private TJobAuditDetailDto dto;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.received_client_msg_details_activity);

		dto = (TJobAuditDetailDto) getIntent().getExtras().getSerializable(
				"dto");
		findViews();
		initViews();
	}

	@Override
	protected void findViews() {
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		received_title = (TextView) findViewById(R.id.received_title);
		received_content = (TextView) findViewById(R.id.received_content);
		received_name = (TextView) findViewById(R.id.received_name);
		received_time = (TextView) findViewById(R.id.received_time);

	}

	@Override
	protected void initViews() {
		topTitle.setText("下级汇报详情");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		if (dto.getJobTitle() != null) {
			received_title.setText("汇报标题：" + dto.getJobTitle().toString());
		} else {
			received_title.setText("汇报标题：");
		}

		if (dto.getJobContent() != null) {
			received_content.setText("汇报内容：" + dto.getJobContent().toString());
		} else {
			received_content.setText("汇报内容：无内容！");
		}

		if (dto.getCreateName() != null) {
			received_name.setText("汇报人：" + dto.getCreateName().toString());
		} else {
			received_name.setText("汇报人：");
		}

		if (dto.getJobDate() != null) {
			received_time.setText("汇报时间：" + dto.getJobDate().toString());
		} else {
			received_time.setText("汇报时间：");
		}
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {

	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		// TODO Auto-generated method stub

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

}

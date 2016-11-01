package com.songming.sanitation.workdeal;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;

public class SendNoticeActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private EditText send_title;// 公告标题

	private EditText send_detail;// 公告内容

	private Button send_commit;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.send_notice_activity);
		findViews();
		initViews();
	}

	@Override
	protected void findViews() {
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		send_title = (EditText) findViewById(R.id.send_title);

		send_detail = (EditText) findViewById(R.id.send_detail);

		send_commit = (Button) findViewById(R.id.send_commit);
	}

	@Override
	protected void initViews() {
		topTitle.setText("发布公告");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		send_commit.setOnClickListener(this);
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		switch (id) {
		case R.id.layoutbackimg:
			this.finish();

			break;

		case R.id.send_commit:
			// 选择人员
			if (!"".equals(send_title.getText().toString())
					&& !"".equals(send_detail.getText().toString())) {

				Intent intent = new Intent(this, DesignateActivity.class);
				intent.putExtra("title", send_title.getText().toString());
				intent.putExtra("content", send_detail.getText().toString());
				//标签跳转位置
				intent.putExtra("index", 2);
				startActivity(intent);
			} else {
				Toast.makeText(this, "请输入内容！", Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}
	}

}

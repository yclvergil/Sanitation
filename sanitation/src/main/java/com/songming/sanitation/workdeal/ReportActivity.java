package com.songming.sanitation.workdeal;

import org.json.JSONException;
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
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;

/** 我要汇报 */
public class ReportActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private EditText send_detail;// 汇报内容

	private EditText report_title;// 汇报标题

	private Button send_commit;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.report_activity);

		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		send_detail = (EditText) findViewById(R.id.report_detail);

		report_title = (EditText) findViewById(R.id.report_title);
		send_commit = (Button) findViewById(R.id.report_commit);
	}

	@Override
	protected void initViews() {
		topTitle.setText("汇报上级");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		send_commit.setOnClickListener(this);
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

		case R.id.report_commit:
			// 选择汇报上级
			if (!"".equals(send_detail.getText().toString())
					&& !"".equals(report_title.getText().toString())) {
				Intent intent = new Intent(this, DesignateActivity.class);
				intent.putExtra("index", 4);
				intent.putExtra("title", report_title.getText().toString());
				intent.putExtra("content", send_detail.getText().toString());
				startActivity(intent);
			}else{
				Toast.makeText(this, "请输入内容！", Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}
	}

}

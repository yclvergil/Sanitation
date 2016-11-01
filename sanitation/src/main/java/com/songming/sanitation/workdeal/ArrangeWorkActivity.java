package com.songming.sanitation.workdeal;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.workdeal.adapter.WorkDealAdapter;
import com.songming.sanitation.workdeal.bean.TEventDto;

public class ArrangeWorkActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private List<TEventDto> datalist = new ArrayList<TEventDto>();

	private EditText send_detail;// 汇报内容

	private EditText report_title;// 汇报标题

	private Button send_commit;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.arrange_work_activity);

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
		topTitle.setText("安排工作");
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
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();

		switch (id) {
		case R.id.layoutbackimg:
			this.finish();

			break;
		case R.id.report_commit:
			// 安排工作
			if (!"".equals(send_detail.getText().toString())
					&& !"".equals(report_title.getText().toString())) {
				Intent intent = new Intent(this, DesignateActivity.class);
				intent.putExtra("index", 3);
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

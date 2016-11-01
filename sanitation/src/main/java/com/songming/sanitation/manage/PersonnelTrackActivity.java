package com.songming.sanitation.manage;

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
/**
 * 人员轨迹详情界面
 * @author Administrator
 *
 */
public class PersonnelTrackActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private ImageView layoutbackimg;
	private TextView topTitle;

	private ImageView calendar;// 日历
	private TextView time;// 时间
	private TextView beyond;// 超出轨迹次数
	private TextView accomplish;// 清扫规定线路 次数
	private TextView state;// 出发结束地状态

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.manage_personnel_track);
		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		time = (TextView) findViewById(R.id.manage_track_time);
		beyond = (TextView) findViewById(R.id.manage_track_beyond);
		accomplish = (TextView) findViewById(R.id.manage_track_accomplish);
		state = (TextView) findViewById(R.id.manage_track_state);

		calendar = (ImageView) findViewById(R.id.image_calendar);

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		topTitle.setText("轨迹详情");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		time.setText("时间：" + "2016-3-20");
		beyond.setText("超出轨迹次数：" + "3");
		accomplish.setText("清扫规定线路：" + "10" + "条");
		state.setText("出发结束地："+"正常");

		calendar.setOnClickListener(this);

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
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.image_calendar:
			// 日历跳转

			break;

		case R.id.layoutbackimg:
			this.finish();

			break;

		default:
			break;
		}
	}

}

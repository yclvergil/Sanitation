package com.songming.sanitation.manage;

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
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.user.ContactsAcitivity;

/**
 * 各项管理主界面
 * 
 * @author Administrator
 * 
 */
public class ManageActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private LinearLayout clock;// 考勤
	private LinearLayout car;// 车辆轨迹
	private LinearLayout personnel;// 人员轨迹
	private LinearLayout data;// 数据统计

	private Long stationId;// 员工ID

	private LinearLayout car_protect_layout;// 车辆保养

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.manage_activity);

		stationId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STATIONID, -1);
		findViews();
		initViews();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		clock = (LinearLayout) findViewById(R.id.manage_layout_clock);
		data = (LinearLayout) findViewById(R.id.manage_layout_data);

		car_protect_layout = (LinearLayout) findViewById(R.id.car_protect_layout);
		car = (LinearLayout) findViewById(R.id.manage_layout_car);
		personnel = (LinearLayout) findViewById(R.id.manage_layout_personnel);
	}

	@Override
	protected void initViews() {
		topTitle.setText("管理");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		clock.setOnClickListener(this);
		car.setOnClickListener(this);
		personnel.setOnClickListener(this);
		data.setOnClickListener(this);
		car_protect_layout.setOnClickListener(this);
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {

	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.manage_layout_clock:
			// 人员
			startActivity(new Intent(this, ClockActivity.class));
			break;
		case R.id.manage_layout_car:
			// 车辆
			// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
			startActivity(new Intent(this, ManagCarActivity.class));
			break;
		case R.id.manage_layout_personnel:
			// 通讯录
			startActivity(new Intent(this, ContactsAcitivity.class));
			break;

		case R.id.manage_layout_data:
			// 数据统计
			// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
			if (stationId == 1 || stationId == 2 || stationId == 3
					|| stationId == 4 || stationId == 5 || stationId == 6) {
				startActivity(new Intent(this, DataStatisticsActivity.class));
			} else {
				Toast.makeText(this,
						getResources().getString(R.string.nopower),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.layoutbackimg:
			this.finish();

			break;

		default:
			break;
		}
	}

}

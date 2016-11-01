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
import com.songming.sanitation.user.UpKeepActivity;

/** 车辆 */
public class ManagCarActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private LinearLayout car;// 车辆轨迹

	private LinearLayout llayout_details;
	private Long stationId;// 员工ID

	private LinearLayout car_protect_layout;// 车辆保养

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.manage_car_activity);

		stationId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STATIONID, -1);
		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		car_protect_layout = (LinearLayout) findViewById(R.id.car_protect_layout);

		car = (LinearLayout) findViewById(R.id.manage_layout_car);

		llayout_details = (LinearLayout) findViewById(R.id.llayout_details);
	}

	@Override
	protected void initViews() {
		topTitle.setText("车辆");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		car.setOnClickListener(this);
		car_protect_layout.setOnClickListener(this);
		llayout_details.setOnClickListener(this);
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
		int id = v.getId();

		switch (id) {

		case R.id.llayout_details:
			// 查看车辆信息
			// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
			if (stationId == 1 || stationId == 2 || stationId == 4
					|| stationId == 6) {
				startActivity(new Intent(this, LookCarManageActivity.class));
			} else {
				Toast.makeText(this,
						getResources().getString(R.string.nopower),
						Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.layoutbackimg:
			this.finish();
			break;

		case R.id.car_protect_layout:
			// 车辆保养:项目经理、司机、车队长权限
			if (stationId == 2 || stationId == 4 || stationId == 5) {
				startActivity(new Intent(this, UpKeepActivity.class));
			} else {
				Toast.makeText(this,
						getResources().getString(R.string.nopower),
						Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.manage_layout_car:
			// 车辆轨迹
			// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部

			if (stationId == 1 || stationId == 2 || stationId == 4
					|| stationId == 6) {
				startActivity(new Intent(this, DepartActivity.class));
			} else {
				Toast.makeText(this,
						getResources().getString(R.string.nopower),
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

}

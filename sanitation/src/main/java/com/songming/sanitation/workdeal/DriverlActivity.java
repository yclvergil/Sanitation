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
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.user.CarActivity;
import com.songming.sanitation.user.CheckUpActivity;
import com.songming.sanitation.workdown.DownloadAcitivity;

/**
 * 司机入口页面
 * 
 * @author Administrator
 * 
 */
public class DriverlActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private LinearLayout announcement;// 关联车辆
	private LinearLayout incident;// 数据记录
	private Long stationId;// 员工ID

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.driver_activity);

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

		announcement = (LinearLayout) findViewById(R.id.announcement);
		incident = (LinearLayout) findViewById(R.id.incident);

	}

	@Override
	protected void initViews() {

		topTitle.setText("司机入口");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		announcement.setOnClickListener(this);
		incident.setOnClickListener(this);
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
			// 关联车辆
			// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
			if (stationId == 2 || stationId == 5) {
				startActivity(new Intent(this, CarActivity.class));
			} else {
				Toast.makeText(this,
						getResources().getString(R.string.nopower),
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.incident:
			// 数据记录
			// startActivity(new Intent(this, CheckUpActivity.class));

			Toast.makeText(this, getResources().getString(R.string.nopower),
					Toast.LENGTH_SHORT).show();

			break;

		case R.id.layoutbackimg:
			this.finish();

			break;
		default:
			break;
		}
	}

}

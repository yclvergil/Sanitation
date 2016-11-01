package com.songming.sanitation.workdeal;

import org.json.JSONObject;
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
import com.songming.sanitation.message.ApprovalOpenAcitivity;

/**
 * 审批
 * 
 * @author Administrator
 * 
 */
public class ApprovalActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private LinearLayout message_layout;// 发起审批
	private LinearLayout incident_list;// 审批结果
	private LinearLayout contacts_layout;// 我审批的
	private Long stationId;// 员工ID

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.approval_activity);
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

		message_layout = (LinearLayout) findViewById(R.id.incident_message_layout);
		incident_list = (LinearLayout) findViewById(R.id.incident_list);
		contacts_layout = (LinearLayout) findViewById(R.id.incident_contacts_layout);

	}

	@Override
	protected void initViews() {
		topTitle.setText("审批");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		message_layout.setOnClickListener(this);
		incident_list.setOnClickListener(this);
		contacts_layout.setOnClickListener(this);

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
		case R.id.layoutbackimg:
			this.finish();
			break;
		case R.id.incident_message_layout:
			// 发起审批
			startActivity(this, ApprovalOpenAcitivity.class);
			break;

		case R.id.incident_list:
			// 审批结果
			startActivity(this, ApprovalResultListAcitivity.class);
			break;

		case R.id.incident_contacts_layout:
			// 我审批的
			// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
			stationId = SharedPreferencesUtils.getLongValue(this,
					SharedPreferencesUtils.STATIONID, -1);
			if(stationId.intValue() > 6){
				Toast.makeText(this, getResources().getString(R.string.nopower), 0).show();
			}else{
				startActivity(this, ApprovalApplyListAcitivity.class);
			}
			break;

		default:
			break;
		}
	}
}

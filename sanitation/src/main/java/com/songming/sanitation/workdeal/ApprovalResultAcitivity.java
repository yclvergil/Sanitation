package com.songming.sanitation.workdeal;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.ext.ListViewExt;
import com.songming.sanitation.workdeal.adapter.ApprovalResultItemAdapter;
import com.songming.sanitation.workdeal.bean.TJobAuditDetailDto;
import com.songming.sanitation.workdeal.bean.TJobAuditDto;

/**
 * 审批结果页面
 * 
 * @author Administrator
 * 
 */
public class ApprovalResultAcitivity extends BaseActivity implements
		OnClickListener {

	private TextView topTitle;
	private TextView apply_event;
	private ImageView layoutbackimg;
	private ListViewExt listview;
	private ApprovalResultItemAdapter adapter;
	private List<TJobAuditDetailDto> list = new ArrayList<TJobAuditDetailDto>();
	private TJobAuditDto dto;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.approval_result);
		dto = (TJobAuditDto) getIntent().getSerializableExtra("dto");
		findViews();
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void findViews() {
		topTitle = (TextView) findViewById(R.id.topTitle);
		apply_event = (TextView) findViewById(R.id.apply_event);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		listview = (ListViewExt) findViewById(R.id.listview);
	}

	@Override
	protected void initViews() {
		topTitle.setText("审批结果");
		topTitle.setVisibility(View.VISIBLE);
		apply_event.setText(dto.getJobContent());
		list = dto.getJobAuditDetails();
		adapter = new ApprovalResultItemAdapter(this, list);
		listview.setAdapter(adapter);
		
		layoutbackimg.setOnClickListener(this);
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

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

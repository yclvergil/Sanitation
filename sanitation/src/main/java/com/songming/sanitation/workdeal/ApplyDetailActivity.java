package com.songming.sanitation.workdeal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.user.model.TUploadFileDto;
import com.songming.sanitation.workdeal.adapter.ApplyPictureAdapter;
import com.songming.sanitation.workdeal.bean.TJobAuditDetailDto;

/**
 * 审批申请详情页面
 * @author Administrator
 *
 */
public class ApplyDetailActivity extends BaseActivity implements OnClickListener{
	
	private ImageView layoutbackimg;
	private TextView event_type;
	private TextView name;
	private TextView event_desc;
	private TextView agree;
	private TextView refuse;
	private EditText edit_content;//批示内容
	private GridView grid;
	private List<TUploadFileDto> list = new ArrayList<TUploadFileDto>();
	private ApplyPictureAdapter adapter;
	private TJobAuditDetailDto dto;
	private static final String TAG = "ApplyDetailActivity";
	private LinearLayout ll_btn;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.applydetail);
		dto = (TJobAuditDetailDto) getIntent().getSerializableExtra("dto");
		findViews();
		initViews();
	}

	@Override
	protected void findViews() {
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		event_type = (TextView) findViewById(R.id.event_type);
		name = (TextView) findViewById(R.id.name);
		event_desc = (TextView) findViewById(R.id.event_desc);
		agree = (TextView) findViewById(R.id.agree);
		refuse = (TextView) findViewById(R.id.refuse);
		edit_content = (EditText) findViewById(R.id.edit_content);
		grid = (GridView) findViewById(R.id.grid);
		ll_btn = (LinearLayout) findViewById(R.id.ll_btn);
	}

	@Override
	protected void initViews() {
		
		int status = dto.getStatus();
		if(status == 0){
			//未审批的
			ll_btn.setVisibility(View.VISIBLE);
		}else{
			//已审批的
			ll_btn.setVisibility(View.GONE);
			edit_content.setText(dto.getJobContent());
		}
		name.setText(dto.getApplyBy());
		event_type.setText(dto.getRemark());
		event_desc.setText(dto.getAuditContent());
		list = dto.getFiles();
		adapter = new ApplyPictureAdapter(this, list);
		grid.setAdapter(adapter);
		setGrid();
		
		layoutbackimg.setOnClickListener(this);
		agree.setOnClickListener(this);
		refuse.setOnClickListener(this);
	}

	/**
	 * 设置GirdView属性
	 */
	public void setGrid() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		int itemWidth = (int) (90 * density);
		int divider = (int) (5*density);
		int size = list.size();
		int gridviewWidth = (int) (size * (itemWidth + divider) - divider);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				gridviewWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
		grid.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
		grid.setColumnWidth(itemWidth); // 设置列表项宽
		grid.setStretchMode(GridView.NO_STRETCH);
		grid.setNumColumns(size); // 设置列数量=列表集合数
	}
	
	/**
	 * 审批
	 * @param status:1-同意，2-不同意
	 */
	 
	private void sendResult(int status) {

		long id = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("status", status);
			jsonObject.put("jobContent", edit_content.getText().toString().trim());
			jsonObject.put("auditId", dto.getAuditId());
			jsonObject.put("executeId", id);
			this.showLoading("提交中...", TAG);
			requestHttp(jsonObject, TAG, Constants.APPROVAL,
					Constants.SERVER_URL);
		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if (TAG.equals(requestStr)) {
			Toast.makeText(this, "提交失败！", Toast.LENGTH_SHORT).show();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i(TAG, volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if (TAG.equals(requestStr)) {
			boolean flag = jsonObject.optBoolean("flag");
			if(flag){
				Toast.makeText(this, "提交审批成功", Toast.LENGTH_SHORT).show();
				this.finish();
			}else{
				Toast.makeText(this, "提交审批失败", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layoutbackimg:
			this.finish();
			break;
		case R.id.agree:
			sendResult(1);
			break;
		case R.id.refuse:
			sendResult(2);
			break;
		default:
			break;
		}
	}
	
}

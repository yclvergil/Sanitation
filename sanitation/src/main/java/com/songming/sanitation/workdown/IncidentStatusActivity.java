package com.songming.sanitation.workdown;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.workdeal.bean.TEventDetailDto;
import com.songming.sanitation.workdeal.bean.TEventDto;

/**
 * 事件列表界面
 * 
 * @author Administrator
 * 
 */
//
public class IncidentStatusActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private TEventDto detail;
	private List<TEventDetailDto> detailList;

	private long stationId;
	private long id = -1;// 传入的事件id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.incident_status);
		// id = getIntent().getLongExtra("id", -1);
		stationId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STATIONID, -1);
		stationId = 4;

		findViews();
		initViews();
	}

	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

	}

	protected void initViews() {
		// TODO Auto-generated method stub
		// String loginName = SharedPreferencesUtils.getStringValue(this,
		// SharedPreferencesUtils.LOGIN_NAME, "wangwu");
		topTitle.setText("查看事件状态");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		// TODO Auto-generated method stub

		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("worklistdetail".equals(requestStr)) {

			Gson gson = new Gson();
			detail = gson.fromJson(jsonObject.optString("data", "{}"),
					TEventDto.class);

			String flag = jsonObject.optString("flag", "");
			String msg = jsonObject.optString("msg", "获取数据成功");

			if (flag.equals("true")) {

				detailList = detail.getDetails();

			} else {
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void requestData() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", id);
			requestHttp(jsonObject, "worklistdetail", Constants.WORKLISTDETAIL,
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

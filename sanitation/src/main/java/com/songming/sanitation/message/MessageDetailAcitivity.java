package com.songming.sanitation.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.songming.sanitation.Main;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.MD5;
import com.songming.sanitation.frameset.utils.RefreshUtils;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.map.model.MapModel;
import com.songming.sanitation.message.adapter.MessageActivityListAdapter;
import com.songming.sanitation.message.adapter.MessageListAdapter;
import com.songming.sanitation.message.model.MessagePushDTO;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

@SuppressLint("NewApi")
public class MessageDetailAcitivity extends BaseActivity implements
		OnClickListener {

	private TextView topTitle;
	private LinearLayout layoutback;

	private String id = "";
	private MessagePushDTO data;

	private TextView name;
	private ImageView user_image;
	private TextView time;
	private TextView time2;
	private TextView content;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_message_detail);
		id = getIntent().getStringExtra("id");
		findViews();
		initViews();
		requestData();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutback = (LinearLayout) findViewById(R.id.layoutback);

		name = (TextView) findViewById(R.id.name);
		user_image = (ImageView) findViewById(R.id.user_image);
		time = (TextView) findViewById(R.id.time);
		time2 = (TextView) findViewById(R.id.time2);
		content = (TextView) findViewById(R.id.content);
	}

	private void initdata() {
		// TODO Auto-generated method stub
		name.setText(applications.getUser().getName());
		String timestr = data.getCreateDate();
		String timeS1 = "";
		String timeS2 = "";
		try {

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date datet = df.parse(timestr);
			df = new SimpleDateFormat("MM月dd日");
			timeS1 = df.format(datet);
			int weekday = getWeekdayOfDateTime(datet);
			switch (weekday) {
			case 1:
				timeS1 += "周一";
				break;
			case 2:
				timeS1 += "周二";
				break;
			case 3:
				timeS1 += "周三";
				break;
			case 4:
				timeS1 += "周四";
				break;
			case 5:
				timeS1 += "周五";
				break;
			case 6:
				timeS1 += "周六";
				break;
			case 7:
				timeS1 += "周日";
				break;
			default:
				break;
			}

			df = new SimpleDateFormat("HH:mm");
			timeS2 = df.format(datet);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			time.setText("" + timeS1);
			time2.setText("" + timeS2);
		}

		content.setText(data.getContent());
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		topTitle.setText("消息");
		topTitle.setVisibility(View.VISIBLE);
		layoutback.setVisibility(View.VISIBLE);
		layoutback.setOnClickListener(this);
	}

	public static int getWeekdayOfDateTime(Date datet) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(datet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int weekday = c.get(Calendar.DAY_OF_WEEK) - 1;
		return weekday;
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("findbyidmessagepushapp".equals(requestStr)) {
			Toast.makeText(this, "数据获取失败!", Toast.LENGTH_SHORT).show();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("findbyidmessagepushapp", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("findbyidmessagepushapp".equals(requestStr)) {

			Gson gson = new Gson();
			data = gson.fromJson(jsonObject.optString("data", "{}"),
					MessagePushDTO.class);
			if (data != null) {
				initdata();
			}

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layoutback:
			finish();
			break;

		default:
			break;
		}
	}

	// 请求数据
	private void requestData() {

		this.showLoading("请稍后...", "refresh");

		JSONObject jsonObject = new JSONObject();
		// mCurIndex++;
		try {
			// 设置页码
			jsonObject.put("id", id);
			requestHttp(jsonObject, "findbyidmessagepushapp",
					Constants.FINDBYIDMESSAGEPUSHAPP, Constants.SERVER_URL);

		} catch (JSONException e) {
			Toast.makeText(this, "json参数出错:" + e.getMessage(),
					Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this, "请重新启动", Toast.LENGTH_SHORT).show();
		}
	}
}

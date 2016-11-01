package com.songming.sanitation.manage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.songming.sanitation.manage.adapter.LookCarDetailsAdapter;
import com.songming.sanitation.user.model.TCarDto;

/** 查看车辆详情界面 */
public class LookCarDetailsActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;
	private int mCurIndex = 1;

	private LinearLayout llayout_wait;// 维修
	private LinearLayout llayout_doing;// 保养
	private LinearLayout llayout_finishit;// 加油

	private TextView tv_wait;// 维修
	private TextView tv_doing;// 保养
	private TextView tv_finishit;// 加油
	private PullToRefreshListView mListView;

	private List<TCarDto> list = new ArrayList<TCarDto>();

	private LookCarDetailsAdapter adapter;
	private int type = 1;
	private long carId;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.look_car_details_activity);

		carId = getIntent().getLongExtra("id", -1);

		findViews();
		initViews();
		getCarDetailsList();
	}

	@Override
	protected void findViews() {
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		mListView = (PullToRefreshListView) findViewById(R.id.car_details_mListView);

		llayout_wait = (LinearLayout) findViewById(R.id.llayout_wait);
		llayout_doing = (LinearLayout) findViewById(R.id.llayout_doing);
		llayout_finishit = (LinearLayout) findViewById(R.id.llayout_finishit);

		tv_wait = (TextView) findViewById(R.id.tv_wait);
		tv_doing = (TextView) findViewById(R.id.tv_doing);
		tv_finishit = (TextView) findViewById(R.id.tv_finishit);
	}

	@Override
	protected void initViews() {
		topTitle.setText("车辆信息详情");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		llayout_wait.setOnClickListener(this);
		llayout_doing.setOnClickListener(this);
		llayout_finishit.setOnClickListener(this);

		adapter = new LookCarDetailsAdapter(this, list);
		mListView.setAdapter(adapter);
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurIndex = 1;
				getCarDetailsList();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurIndex++;
				getCarDetailsList();
			}
		});

	}

	/**
	 * 车辆详情
	 */
	private void getCarDetailsList() {

		long staffId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();

		try {

			jsonObject.put("page", mCurIndex);
			jsonObject.put("rows", 10);

			jsonObject.put("paramsMap", jsonObject2);
			jsonObject2.put("staffId", staffId);
			jsonObject2.put("carId", carId);
			jsonObject2.put("maintainType", type);
			this.showLoading("正在查询……", "getCarDetailsList");
			requestHttp(jsonObject, "getCarDetailsList",
					Constants.CAR_MAINTAIN_LIST, Constants.SERVER_URL);
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
		mListView.onRefreshComplete();
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {

		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("getCarDetailsList".equals(requestStr)) {
			Gson gson = new Gson();
			String data = jsonObject.optString("data", "{}");

			Log.i("aa", "data:" + data);
			List<TCarDto> mList = gson.fromJson(data,
					new TypeToken<List<TCarDto>>() {
					}.getType());
			if (mCurIndex == 1) {
				list = mList;
			} else {
				if (mList != null) {
					list.addAll(mList);
				}
			}

			adapter.setData(list);
			adapter.notifyDataSetChanged();

			mListView.onRefreshComplete();
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.layoutbackimg:
			this.finish();

			break;
		case R.id.llayout_wait:
			// 维修
			llayout_wait.setBackgroundResource(R.drawable.workdeal02);
			llayout_doing.setBackgroundResource(R.color.color_00000000);
			llayout_finishit.setBackgroundResource(R.color.color_00000000);

			tv_wait.setTextColor(getResources().getColor(R.color.color_ffffff));
			tv_doing.setTextColor(getResources().getColor(R.color.color_000000));
			tv_finishit.setTextColor(getResources().getColor(
					R.color.color_000000));
			type = 1;
			getCarDetailsList();
			break;
		case R.id.llayout_doing:
			// 保养
			llayout_wait.setBackgroundResource(R.color.color_00000000);
			llayout_doing.setBackgroundResource(R.drawable.workdeal02);
			llayout_finishit.setBackgroundResource(R.color.color_00000000);

			tv_wait.setTextColor(getResources().getColor(R.color.color_000000));
			tv_doing.setTextColor(getResources().getColor(R.color.color_ffffff));
			tv_finishit.setTextColor(getResources().getColor(
					R.color.color_000000));

			type = 3;
			getCarDetailsList();
			break;
		case R.id.llayout_finishit:
			// 加油
			llayout_finishit.setBackgroundResource(R.drawable.workdeal02);
			llayout_doing.setBackgroundResource(R.color.color_00000000);
			llayout_wait.setBackgroundResource(R.color.color_00000000);

			tv_wait.setTextColor(getResources().getColor(R.color.color_000000));
			tv_doing.setTextColor(getResources().getColor(R.color.color_000000));
			tv_finishit.setTextColor(getResources().getColor(
					R.color.color_ffffff));
			type = 4;
			getCarDetailsList();
			break;
		default:
			break;
		}
	}

}

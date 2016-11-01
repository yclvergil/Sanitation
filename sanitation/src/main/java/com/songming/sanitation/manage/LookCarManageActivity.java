package com.songming.sanitation.manage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
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
import com.songming.sanitation.frameset.utils.RefreshUtils;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.user.CarActivity;
import com.songming.sanitation.user.model.TCarDto;

/** 查看车辆信息 */
public class LookCarManageActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private Long stationId;// 员工ID
	private PullToRefreshListView mListView;
	private int mCurIndex = 1;

	private MyAdapter adapter;
	private List<TCarDto> list = new ArrayList<TCarDto>();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.look_car_manage_activity);

		stationId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STATIONID, -1);
		findViews();
		initViews();
		getCarDetailsList();

	}

	@Override
	protected void findViews() {
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		mListView = (PullToRefreshListView) findViewById(R.id.mListView);

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		topTitle.setText("查看车辆信息");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		adapter = new MyAdapter(this);
		mListView.setAdapter(adapter);

		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurIndex = 1;

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurIndex++;

			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

	}

	/**
	 * 车辆列表
	 */
	private void getCarDetailsList() {

		long staffId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("staffId", staffId);
			this.showLoading("正在查询……", "getCarDetailsList");
			requestHttp(jsonObject, "getCarDetailsList", Constants.CARLIST,
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// llayput_maintain.setVisibility(View.VISIBLE);
		// llayput_refuel.setVisibility(View.VISIBLE);
		// llayput_keep.setVisibility(View.VISIBLE);
		// mListView.setVisibility(View.GONE);

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
			list = gson.fromJson(data, new TypeToken<List<TCarDto>>() {
			}.getType());

			adapter.notifyDataSetChanged();
			mListView.setVisibility(View.VISIBLE);
			mListView.onRefreshComplete();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.layoutbackimg:
			this.finish();
			break;

		default:
			break;
		}
	}

	class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private Context context;
		private ViewHolder holder;

		public MyAdapter(Context context) {
			this.context = context;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (list != null) {
				return list.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			if (list != null) {
				return list.get(position);
			} else {

				return 0;
			}
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.look_car_item, null);
				holder = new ViewHolder();
				holder.tv = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final TCarDto dto = list.get(position);

			String carCode;
			if (dto.getCarCode() != null) {
				carCode = dto.getCarCode();
			} else {
				carCode = "";
			}
			String carNo;
			if (dto.getCarNo() != null) {
				carNo = dto.getCarNo();
			} else {
				carNo = "";
			}

			String carTypeName;
			if (dto.getCarTypeName() != null) {
				carTypeName = dto.getCarTypeName();
			} else {
				carTypeName = "";
			}

			holder.tv.setText(carCode + "-" + carNo + "-" + carTypeName);

			if (dto.getId() != null) {

				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(LookCarManageActivity.this,
								LookCarDetailsActivity.class);
						intent.putExtra("id", dto.getId());
						Log.i("aa", "dto.getId():" + dto.getId());
						startActivity(intent);
					}
				});
			}

			return convertView;
		}

		class ViewHolder {
			TextView tv;
		}

	}

}

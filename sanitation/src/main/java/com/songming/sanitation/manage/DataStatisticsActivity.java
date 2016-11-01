package com.songming.sanitation.manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.songming.sanitation.manage.bean.StaffListDot;
import com.songming.sanitation.manage.bean.TStaffJobSignDto;
import com.squareup.timessquare.CalendarPickerView;

public class DataStatisticsActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;
	private ImageView calendar;// 右侧日期选择

	private CalendarPickerView calendarView;
	private Long stationId;// 员工ID
	private String seletedDate = "";// 选中的日期.

	private PullToRefreshListView mListView;
	private int mCurIndex = 1;
	private MyAdapter adapter;

	private List<TStaffJobSignDto> dataList = new ArrayList<TStaffJobSignDto>();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.data_statistics_activity);

		stationId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STATIONID, -1);
		findViews();
		initViews();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		seletedDate = sdf.format(new Date());
		getMySignList(seletedDate);
	}

	@Override
	protected void findViews() {
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		calendar = (ImageView) findViewById(R.id.checkup_calendar);

		mListView = (PullToRefreshListView) findViewById(R.id.mListView);

	}

	@Override
	protected void initViews() {
		topTitle.setText("数据统计");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		calendar.setOnClickListener(this);

		adapter = new MyAdapter(this, dataList);

		mListView.setAdapter(adapter);

		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurIndex = 1;
				getMySignList(seletedDate);

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurIndex++;
				getMySignList(seletedDate);
			}
		});
	}

	/**
	 * 显示日历
	 */
	private void showCalendar() {
		// 明年
		final Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.YEAR, 1);
		// 去年
		final Calendar lastYear = Calendar.getInstance();
		lastYear.add(Calendar.YEAR, -1);

		final Dialog dialog = new Dialog(this, R.style.dialog);
		dialog.setContentView(R.layout.calendar);
		// 日历返回
		ImageView backImg = (ImageView) dialog.findViewById(R.id.calendar_back);
		backImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		// 日历控件
		calendarView = (CalendarPickerView) dialog
				.findViewById(R.id.calendar_view);
		calendarView.init(lastYear.getTime(), nextYear.getTime())
				.withSelectedDate(new Date());
		calendarView
				.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {

					@Override
					public void onDateUnselected(Date date) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onDateSelected(Date date) {
						if (calendarView.getSelectedDate().getTime() > System
								.currentTimeMillis()) {
							Toast.makeText(DataStatisticsActivity.this,
									"未来无迹可寻，请选择回顾过去!", 0).show();
							return;
						}
						dialog.dismiss();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						seletedDate = sdf.format(calendarView.getSelectedDate());
						getMySignList(seletedDate);
					}
				});
		dialog.show();

	}

	/**
	 * 我的考勤列表表
	 */
	private void getMySignList(String day) {

		long staffId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		try {
			jsonObject.put("page", mCurIndex);
			jsonObject.put("rows", 10);
			jsonObject.put("paramsMap", jsonObject2);

			jsonObject2.put("staffId", staffId);
			jsonObject2.put("createDate", seletedDate);

			this.showLoading("正在查询……", "getMySignList");
			requestHttp(jsonObject, "getMySignList", Constants.MYSIGN_LIST,
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
		mListView.onRefreshComplete();
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("getMySignList".equals(requestStr)) {
			Gson gson = new Gson();
			String data = jsonObject.optString("data");
			List<TStaffJobSignDto> list = gson.fromJson(data,
					new TypeToken<List<TStaffJobSignDto>>() {
					}.getType());

			if (mCurIndex == 1) {
				dataList = list;
			} else {
				if (list != null) {
					dataList.addAll(list);
				}
			}
			adapter.setData(dataList);
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
		case R.id.checkup_calendar:
			//日期选择
			showCalendar();
			break;
		default:
			break;
		}

	}

	class MyAdapter extends BaseAdapter {

		private Context context;
		private LayoutInflater mInflater;
		private ViewHolder holder;
		private List<TStaffJobSignDto> list;

		public MyAdapter(Context context, List<TStaffJobSignDto> list) {
			this.context = context;
			this.list = list;
			mInflater = LayoutInflater.from(context);
		}

		public void setData(List<TStaffJobSignDto> list) {
			this.list = list;
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
				convertView = mInflater.inflate(R.layout.data_statistics_item,
						null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			TStaffJobSignDto dto = list.get(position);

			if (dto.getStaffName() != null) {
				holder.name.setText(dto.getStaffName());
			} else {
				holder.name.setText("姓名");
			}
			holder.name.setVisibility(View.GONE);
			String type;
			if (dto.getSignType() != null) {
				if (dto.getSignType() == 1) {
					type = "上班";
				} else if (dto.getSignType() == 2) {
					type = "下班";
				} else {
					type = "";
				}
			} else {
				type = "";
			}

			String time;
			if (dto.getSignTime() != null) {
				time = dto.getSignTime();
			} else {
				time = "";
			}

			holder.time.setText(type + "打卡时间:" + time);

			return convertView;
		}

		class ViewHolder {
			TextView name;
			TextView time;
		}

	}

}

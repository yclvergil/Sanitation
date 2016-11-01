package com.songming.sanitation.user;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.songming.sanitation.user.adapter.CheckUpDetailsAdapter;
import com.songming.sanitation.user.model.TPatrolDto;
import com.songming.sanitation.user.model.TUploadFileDto;
import com.squareup.timessquare.CalendarPickerView;

/**
 * 巡查详情界面
 * 
 * @author Administrator
 * 
 */
public class CheckUpDetailsActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private LinearLayout today;// 今日点击
	private LinearLayout month;// 当月点击
	private LinearLayout choice;// 日期选择器
	private ImageView today_image;// 今日下划线
	private ImageView month_image;// 当月下划线
	private TextView today_tv;// “当日”文字颜色修改
	private TextView month_tv;// “当月”文字颜色修改

	private ImageView calendar;
	private PullToRefreshListView prlistview;// 下拉列表
	private ListView mListView;// 下拉列表ListView

	private int mCurIndex = 1;
	private List<TPatrolDto> list = new ArrayList<TPatrolDto>();// 巡查情况列表数据
	private List<TUploadFileDto> files = new ArrayList<TUploadFileDto>();
	private CheckUpDetailsAdapter adapter;// 数据列表适配器
	private long staffId;
	private CalendarPickerView calendarView;
	private String seletedDate = "";// 选中的日期

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.checkup_details);

		staffId = getIntent().getLongExtra("id", -1);
		findViews();
		initViews();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		seletedDate = sdf.format(new Date());
		// 默认查询今天的数据
		requestJobCheckList(seletedDate);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		files.clear();
		super.onResume();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		// today = (LinearLayout) findViewById(R.id.details_layout_today);
		// today_image = (ImageView) findViewById(R.id.details_today_image);
		//
		// month = (LinearLayout) findViewById(R.id.details_layout_month);
		// month_image = (ImageView) findViewById(R.id.details_month_image);
		//
		// today_tv = (TextView) findViewById(R.id.details_today);
		// month_tv = (TextView) findViewById(R.id.details_month);
		//
		// choice = (LinearLayout) findViewById(R.id.details_choice);

		prlistview = (PullToRefreshListView) findViewById(R.id.checkup_details_prlistview);
		calendar = (ImageView) findViewById(R.id.checkup_calendar);

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		topTitle.setText("详情");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		calendar.setOnClickListener(this);
		// today.setOnClickListener(this);
		// month.setOnClickListener(this);
		// choice.setVisibility(choice.GONE);

		mListView = prlistview.getRefreshableView();
		adapter = new CheckUpDetailsAdapter(this, list);
		mListView.setAdapter(adapter);

		prlistview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				mCurIndex = 1;
				requestJobCheckList(seletedDate);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				mCurIndex++;
				requestJobCheckList(seletedDate);
			}
		});
		RefreshUtils.setRefreshPrompt(prlistview);

		prlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CheckUpDetailsActivity.this,
						CheckUpPictureActivity.class);
				files.addAll(list.get(position - 1).getFiles());

				intent.putExtra("files", (Serializable) files);

				startActivity(intent);
			}
		});

	}

	/**
	 * 巡查情况列表查询
	 * 
	 */
	private void requestJobCheckList(String day) {

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		try {
			jsonObject2.put("staffId", staffId);
			jsonObject2.put("createDate", day);
			jsonObject.put("paramsMap", jsonObject2);
			jsonObject.put("page", mCurIndex);
			jsonObject.put("rows", 20);
			this.showLoading("正在查询……", "contacts");
			requestHttp(jsonObject, "contacts", Constants.JOBCHECKLIST,
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
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("contacts".equals(requestStr)) {
			Toast.makeText(this, "获取失败！", Toast.LENGTH_SHORT).show();
			prlistview.onRefreshComplete();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("contacts", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("contacts".equals(requestStr)) {

			Gson gson = new Gson();
			String data = jsonObject.optString("data", "{}");
			List<TPatrolDto> newList = gson.fromJson(data,
					new TypeToken<List<TPatrolDto>>() {
					}.getType());
			if (mCurIndex <= 1) {
				if (newList == null || newList.size() == 0) {
					Toast.makeText(CheckUpDetailsActivity.this, "暂无任何信息！",
							Toast.LENGTH_SHORT).show();
				}
				list = newList;
			} else {
				list.addAll(newList);
			}
			adapter.setData(list);
			adapter.notifyDataSetChanged();
			prlistview.onRefreshComplete();

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {

		// case R.id.details_layout_today:
		// // 点击当日 隐藏日期选择界面
		// today_image.setVisibility(today_image.VISIBLE);
		// month_image.setVisibility(month_image.INVISIBLE);
		// today_tv.setTextColor(getResources().getColor(
		// R.color.title_bar_28c3b1));
		// month_tv.setTextColor(getResources().getColor(R.color.color_333333));
		// choice.setVisibility(choice.GONE);
		// break;

		// case R.id.details_layout_month:
		// // 点击当月 显示日期选择界面
		// today_image.setVisibility(today_image.INVISIBLE);
		// month_image.setVisibility(month_image.VISIBLE);
		// today_tv.setTextColor(getResources().getColor(R.color.color_333333));
		// month_tv.setTextColor(getResources().getColor(
		// R.color.title_bar_28c3b1));
		//
		// choice.setVisibility(choice.VISIBLE);
		// break;

		case R.id.checkup_calendar:
			showCalendar();
			break;

		case R.id.layoutbackimg:
			this.finish();
			break;

		default:
			break;
		}
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
							Toast.makeText(CheckUpDetailsActivity.this,
									"未来无迹可寻，请选择回顾过去!", 0).show();
							return;
						}
						dialog.dismiss();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						seletedDate = sdf.format(calendarView.getSelectedDate());
						mCurIndex = 1;
						requestJobCheckList(seletedDate);
					}
				});
		dialog.show();

	}

}

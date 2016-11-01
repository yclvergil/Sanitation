package com.songming.sanitation.manage;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.manage.bean.StaffDto;
import com.songming.sanitation.manage.bean.StaffListDot;

/**
 * 查看车辆轨迹
 * 
 * @author Administrator
 * 
 */
public class DepartActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private ImageView layoutbackimg;
	private TextView topTitle;
	private ImageView layoutsearchimg;
	/** 机构列表 */
	private List<StaffListDot> detail;

	private ExpandableListView listview;// 区域分区单选按钮
	private BaseExpandableListAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.manage_car);
		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		layoutsearchimg = (ImageView) findViewById(R.id.layoutsearchimg);
		listview = (ExpandableListView) findViewById(R.id.expandablelist_view);
	}

	@Override
	protected void initViews() {
		topTitle.setText("查看车辆轨迹");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		layoutsearchimg.setVisibility(View.VISIBLE);
		layoutsearchimg.setOnClickListener(this);
		requestData();
	}

	private void initlistView() {
		listview.setAdapter(adapter);
		// 去掉默认箭头
		listview.setGroupIndicator(null);

		listview.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				long staffId = detail.get(groupPosition).getStaffs()
						.get(childPosition).getId();
				Intent intent = new Intent(DepartActivity.this,
						CarTrackActivity.class);
				intent.putExtra("staffId", staffId);
				startActivity(intent);
				return true;
			}
		});
	}

	/**
	 * 分区适配器
	 * */
	public void initAdapter() {
		adapter = new BaseExpandableListAdapter() {

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				return true;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}

			// 父层视图
			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				convertView = LayoutInflater.from(DepartActivity.this).inflate(
						R.layout.manage_car__item, null);

				ImageView Img = (ImageView) convertView
						.findViewById(R.id.image);
				// 区域分布
				TextView ctsuper = (TextView) convertView
						.findViewById(R.id.ctsuper);
				// 人数详情
				TextView personnel = (TextView) convertView
						.findViewById(R.id.contacts_item_personnel);

				ctsuper.setText(detail.get(groupPosition).getOrgName()
						.toString());

				personnel.setText(detail.get(groupPosition).getStaffs().size()
						+ "人");

				if (isExpanded) {
					Img.setBackgroundResource(R.drawable.personnel_image1);

				} else {
					Img.setBackgroundResource(R.drawable.manage_car_image2);

				}

				return convertView;
			}

			@Override
			public long getGroupId(int groupPosition) {
				return groupPosition;
			}

			@Override
			public int getGroupCount() {

				return detail.size();
			}

			@Override
			public Object getGroup(int groupPosition) {
				return detail.get(groupPosition);
			}

			@Override
			public int getChildrenCount(int groupPosition) {

				return detail.get(groupPosition).getStaffs().size();
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				convertView = LayoutInflater.from(DepartActivity.this).inflate(
						R.layout.manage_car_item_subitem, null);

				ImageView ic = (ImageView) convertView
						.findViewById(R.id.item_ic);

				TextView item_name = (TextView) convertView
						.findViewById(R.id.item_name);
				TextView item_duty = (TextView) convertView
						.findViewById(R.id.item_duty);

				item_name.setText(detail.get(groupPosition).getStaffs()
						.get(childPosition).getName().toString());
				item_duty.setText("");
				return convertView;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return childPosition;
			}

			// getChild相当于getItem,不过它是获取展开后的项，所以参数有2个索引
			// 第1个索引是父项的，第2个是子项的
			@Override
			public Object getChild(int groupPosition, int childPosition) {
				return detail.get(groupPosition).getStaffs();
			}
		};
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("organization".equals(requestStr)) {
			Toast.makeText(this, "数据获取错误", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("organization".equals(requestStr)) {
			Gson gson = new Gson();

			boolean flag = jsonObject.optBoolean("flag");
			String msg = jsonObject.optString("msg", "获取数据成功");
			
			if(!flag){
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				return;
			}

			String data = jsonObject.optString("data");
			detail = gson.fromJson(data, new TypeToken<List<StaffListDot>>() {
			}.getType());

			if (detail == null || detail.size() == 0) {
				return;
			}

			for (int j = 0; j < detail.size(); j++) {

				for (int i = 0; i < detail.get(j).getStaffs().size(); i++) {
					//筛选司机、车队长   //移除时下标要发生变化
					if (detail.get(j).getStaffs().get(i).getStationId() != 5
							&& detail.get(j).getStaffs().get(i).getStationId() != 2) {
						detail.get(j).getStaffs().remove(i);
						i--;
					}
				}
			}

			initAdapter();
			initlistView();
		}
	}

	/**
	 * 描述：获取机构选项
	 * */
	private void requestData() {
		int orgId = SharedPreferencesUtils.getIntValue(this,
				SharedPreferencesUtils.ORG_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", orgId);
			this.showLoading("正在查询中...", "organization");
			requestHttp(jsonObject, "organization",
					Constants.ORGANIZATION_DATA, Constants.SERVER_URL);
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
		case R.id.layoutsearchimg:
			// 搜索按钮
			startActivity(new Intent(DepartActivity.this,
					CarSearchActivity.class));
			break;
		default:
			break;
		}
	}

}

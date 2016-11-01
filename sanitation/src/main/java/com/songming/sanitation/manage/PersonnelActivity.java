package com.songming.sanitation.manage;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.manage.adapter.PersonnelAdapter;

/**
 * 人员轨迹界面
 * 
 * @author Administrator
 * 
 */
public class PersonnelActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private ImageView layoutbackimg;
	private TextView topTitle;
	private ImageView layoutsearchimg;

	private LinearLayout duty;// 职务按键
	private LinearLayout region;// 区域按键；
	
	
	
	private TextView duty_tv;// 职务字体 颜色修改；
	private TextView region_tv;// 区域字体修改;

	private ImageView duty_image;// 职务下划线状态；
	private ImageView region_image;// 区域下划线状态

	private ListView mListView;// 数据显示列表

	private PersonnelAdapter adapter;// 人员轨迹列表
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.manage_personnel);
		findViews();
		initViews();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		layoutsearchimg = (ImageView) findViewById(R.id.layoutsearchimg);

		duty = (LinearLayout) findViewById(R.id.layout_duty);
		region = (LinearLayout) findViewById(R.id.layout_region);

		duty_tv = (TextView) findViewById(R.id.duty_tv);
		region_tv = (TextView) findViewById(R.id.region_tv);

		duty_image = (ImageView) findViewById(R.id.duty_image);
		region_image = (ImageView) findViewById(R.id.region_image);

		mListView = (ListView) findViewById(R.id.personnel_listview);

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		topTitle.setText("人员轨迹");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		layoutsearchimg.setVisibility(View.VISIBLE);
		layoutsearchimg.setOnClickListener(this);

		duty.setOnClickListener(this);
		region.setOnClickListener(this);

		adapter = new PersonnelAdapter(this, null);

		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				startActivity(new Intent(PersonnelActivity.this,
						PersonnelTrackActivity.class));
			}
		});

	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layout_duty:
			// 职务点击事件
			duty_tv.setTextColor(getResources().getColor(
					R.color.title_bar_28c3b1));
			duty_image.setVisibility(duty_image.VISIBLE);

			region_tv.setTextColor(getResources()
					.getColor(R.color.color_333333));
			region_image.setVisibility(duty_image.INVISIBLE);
			break;
		case R.id.layout_region:
			// 区域点击事件
			region_tv.setTextColor(getResources().getColor(
					R.color.title_bar_28c3b1));
			region_image.setVisibility(region_image.VISIBLE);

			duty_tv.setTextColor(getResources().getColor(R.color.color_333333));
			duty_image.setVisibility(duty_image.INVISIBLE);
			break;

		case R.id.layoutbackimg:
			this.finish();
			break;

		case R.id.layoutsearchimg:
			// 搜索界面
			startActivity(new Intent(this, PersonnelSearchActivity.class));
			break;

		default:
			break;
		}
	}

}

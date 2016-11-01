package com.songming.sanitation.manage;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.manage.adapter.PersonnelSearchAdapter;

/**
 * 
 * 人员轨迹搜索界面
 * 
 * @author Administrator
 * 
 */
public class PersonnelSearchActivity extends BaseActivity implements
		OnClickListener {

	private TextView cancel;// 搜索取消按键
	private TextView category;// 类别
	private EditText seek;// 搜索输入框
	private ImageView image;// 搜索框清空按钮；
	private ListView mListView;// 搜索展示列表

	private PersonnelSearchAdapter adapter;// 搜索显示列表适配器

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.manage_personnel_search);
		findViews();
		initViews();

	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		cancel = (TextView) findViewById(R.id.scearch_finish);
		category = (TextView) findViewById(R.id.scearch_category);
		seek = (EditText) findViewById(R.id.searchtext);
		image = (ImageView) findViewById(R.id.scearch_cancel);
		mListView = (ListView) findViewById(R.id.scearch_listview);

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		category.setText("项目人员");
		cancel.setOnClickListener(this);
		image.setOnClickListener(this);

		adapter = new PersonnelSearchAdapter(this, null);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				startActivity(new Intent(PersonnelSearchActivity.this,
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

		case R.id.scearch_cancel:
			//搜索文字清空
			seek.setText("");
			break;
		case R.id.scearch_finish:
			this.finish();

			break;

		default:
			break;
		}
	}

}

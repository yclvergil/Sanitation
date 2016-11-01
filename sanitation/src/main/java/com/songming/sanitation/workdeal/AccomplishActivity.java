package com.songming.sanitation.workdeal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.ext.ListViewExt;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.workdeal.adapter.DetailImageAdapter;
import com.songming.sanitation.workdeal.adapter.WorkDealDetailAdapter;
import com.songming.sanitation.workdeal.bean.ImageDto;
import com.songming.sanitation.workdeal.bean.TEventDetailDto;
import com.songming.sanitation.workdeal.bean.TEventDto;

/**
 * 工单完成界面
 * 
 * @author Administrator
 * 
 */
public class AccomplishActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private TextView headline;// 标题
	private TextView headline_time;// 标题栏下的时间;
	private TextView headline_site;// 标题栏下的地址;

	private long stationId;

	private TEventDto detail;
	private List<TEventDetailDto> detailList;
	private long id = -1;// 传入的事件id
	private TextView creatname;// 发起人
	private ImageView phone;// 发起人电话

	private ListViewExt listviewext;
	private WorkDealDetailAdapter adapter;
	private int whatsup;

	private TextView statusname;
	private LinearLayout workdealtype;

	private List<ImageDto> files = new ArrayList<ImageDto>();// 账片放大
	private List<ImageDto> dipicture;// 照片
	private GridView gridview;
	private DetailImageAdapter diadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accomplish);
		id = getIntent().getLongExtra("id", -1);
		stationId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STATIONID, -1);
		whatsup = getIntent().getIntExtra("whatsup", -1);

		findViews();
		initViews();

		requestData();

	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		listviewext = (ListViewExt) findViewById(R.id.listviewext);

		// 标题,事件,地点
		headline = (TextView) findViewById(R.id.headline_headline);
		headline_time = (TextView) findViewById(R.id.headline_time);
		headline_site = (TextView) findViewById(R.id.headline_site);
		creatname = (TextView) findViewById(R.id.creatname);
		phone = (ImageView) findViewById(R.id.phone);

		workdealtype = (LinearLayout) findViewById(R.id.workdealtype);
		statusname = (TextView) findViewById(R.id.statusname);// 进度条描述文字

		gridview = (GridView) findViewById(R.id.gridview);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		files.clear();
		super.onResume();
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		if (whatsup == 888) {
			topTitle.setText("查看事件状态");
			statusname.setText("事件状态");
			// workdealtype.setVisibility(View.GONE);
		} else {
			topTitle.setText("工单详情");
			statusname.setText("处理进度");
		}
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		adapter = new WorkDealDetailAdapter(this, detailList, stationId,
				whatsup);
		listviewext.setAdapter(adapter);

		// 更具职位是否显示电话

		gridview.setVisibility(View.VISIBLE);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(AccomplishActivity.this,
						CheckUpPictureActivity.class);
				files.addAll(dipicture);

				intent.putExtra("files", (Serializable) files);
				intent.putExtra("index", position);
				startActivity(intent);

			}
		});
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		this.hideLoading();
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

				headline.setText(detail.getEventTitle());// 内容标题
				headline_time.setText(detail.getEventDate());// 时间
				headline_site.setText(detail.getAddress());// 事发地址
				creatname.setText(detail.getCreateName());
				phone.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent it = new Intent(Intent.ACTION_CALL, Uri
								.parse("tel:" + detail.getPhone()));
						startActivity(it);

					}
				});
				detailList = detail.getDetails();

				adapter.setData(detailList);
				adapter.notifyDataSetChanged();

			} else {
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

			}
			dipicture = detail.getFiles();
			diadapter = new DetailImageAdapter(this, detail.getFiles());
			gridview.setAdapter(diadapter);
		}
	}

	/**
	 * 描述： 申请当页数据
	 * 
	 * */
	private void requestData() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", id);
			
			showLoading("数据加载中，请稍候...", "worklistdetail");
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
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.layoutbackimg:
			this.finish();

			break;

		default:
			break;
		}
	}

}
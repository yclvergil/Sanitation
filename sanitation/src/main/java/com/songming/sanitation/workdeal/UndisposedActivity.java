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
import android.widget.Button;
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
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.workdeal.adapter.DetailImageAdapter;
import com.songming.sanitation.workdeal.bean.ImageDto;
import com.songming.sanitation.workdeal.bean.TEventDto;

/**
 * 待受理详情界面
 * 
 * @author Administrator
 * 
 */
public class UndisposedActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private TextView time;// 工单生成时间
	private TextView headline;// 工单内容标题
	private TextView site;// 事发详细地址;

	private TextView creatname;// 发起人
	private ImageView phone;// 发起人电话

	private Button designate;// 指派按键;

	private TEventDto detail;
	private long id = -1;
	private long stationId;

	private List<ImageDto> files = new ArrayList<ImageDto>();// 账片放大
	private List<ImageDto> picture;// 照片

	/** 跳转页面判断 888：查看事件状态 1000：安排工作； 不填写：工单详情 */
	private int whatsup;// 从查看事件页面跳过来的
	private LinearLayout incidenttype;
	private LinearLayout workdealtype;
	private TextView worktime;// 事发详细地址;
	private String creatdate;

	private GridView gridview;
	private DetailImageAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.undisposed);
		whatsup = getIntent().getIntExtra("whatsup", -1);
		creatdate = getIntent().getStringExtra("creatdate");
		id = getIntent().getLongExtra("id", -1);
		stationId = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STATIONID, -1);
		findViews();
		initViews();
		requestData();
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
		incidenttype = (LinearLayout) findViewById(R.id.incidenttype);
		workdealtype = (LinearLayout) findViewById(R.id.workdealtype);
		worktime = (TextView) findViewById(R.id.worktime);
		creatname = (TextView) findViewById(R.id.creatname);
		phone = (ImageView) findViewById(R.id.phone);
		// 时间
		time = (TextView) findViewById(R.id.undisposed_time);
		// 内容标题
		headline = (TextView) findViewById(R.id.undisposed_headline);
		// 事发地址
		site = (TextView) findViewById(R.id.undisposed_site);
		// 指派按键
		designate = (Button) findViewById(R.id.undisposed_designate);

		gridview = (GridView) findViewById(R.id.gridview);

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
		if (stationId == 2 || stationId == 3 || stationId == 5
				|| whatsup == 888) {
			designate.setVisibility(View.GONE);
		}

		if (whatsup == 888) {
			topTitle.setText("查看事件状态");
			// incidenttype.setVisibility(View.VISIBLE);
			// workdealtype.setVisibility(View.GONE);
			// worktime.setText(creatdate);
		} else if (whatsup == 1000) {
			topTitle.setText("安排工作");
			designate.setText("工作安排");
			// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
			if (stationId == 1 || stationId == 2 || stationId == 3
					|| stationId == 4 || stationId == 5 || stationId == 6) {
				designate.setVisibility(View.VISIBLE);
			}

		} else {
			topTitle.setText("工单详情");
		}

		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		designate.setOnClickListener(this);
		gridview.setVisibility(View.VISIBLE);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(UndisposedActivity.this,
						CheckUpPictureActivity.class);
				files.addAll(picture);

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

				time.setText(detail.getEventDate());// 时间
				headline.setText(detail.getEventTitle());// 内容标题
				site.setText(detail.getAddress());// 事发地址
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
			} else {
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

			}
			picture = detail.getFiles();
			adapter = new DetailImageAdapter(this, detail.getFiles());
			gridview.setAdapter(adapter);
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

		case R.id.undisposed_designate:
			// 指派按键点击事件;
			Intent intent = new Intent(UndisposedActivity.this,
					DesignateActivity.class);
			intent.putExtra("taskId", id);
			// 跳转标签
			if (whatsup == 1000) {
				// 安排工作
				intent.putExtra("title", detail.getEventTitle());
				intent.putExtra("index", 3);
			} else {
				// 工单详情
				intent.putExtra("index", 1);
			}
			startActivity(intent);
			// this.finish();
			break;
		case R.id.layoutbackimg:
			this.finish();
			break;

		default:
			break;
		}
	}
}

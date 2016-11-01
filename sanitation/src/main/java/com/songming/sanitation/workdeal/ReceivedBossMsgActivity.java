package com.songming.sanitation.workdeal;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
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
import com.songming.sanitation.workdeal.bean.TDayJobDto;

/** 收到上级指令 */
public class ReceivedBossMsgActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private PullToRefreshListView prlistview;// 下拉列表
	private int mCurIndex = 1;
	private ReceivedBossAdapter adapter;
	private List<TDayJobDto> datalist = new ArrayList<TDayJobDto>();

	private Long staffid;// 员工ID

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.receivedbossmsg);

		staffid = SharedPreferencesUtils.getLongValue(this,
				SharedPreferencesUtils.STAFF_ID, -1);

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

		prlistview = (PullToRefreshListView) findViewById(R.id.prlistview);

	}

	@Override
	protected void initViews() {

		topTitle.setText("收到的上级指令");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		adapter = new ReceivedBossAdapter(this, datalist);
		prlistview.setAdapter(adapter);
		prlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ReceivedBossMsgActivity.this,
						ReceivedBossMsgDetailActivity.class);
				intent.putExtra("dto", datalist.get(position - 1));
				startActivity(intent);

			}
		});

		prlistview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				mCurIndex = 1;
				requestData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				mCurIndex++;
				requestData();
			}
		});
		RefreshUtils.setRefreshPrompt(prlistview);
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("worklist".equals(requestStr)) {
			Toast.makeText(this, "数据申请失败", Toast.LENGTH_SHORT).show();
			prlistview.onRefreshComplete();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("worklist", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("worklist".equals(requestStr)) {
			String data = jsonObject.optString("data");
			Gson gson = new Gson();

			List<TDayJobDto> bean = gson.fromJson(data,
					new TypeToken<List<TDayJobDto>>() {
					}.getType());
			// 判断是否有数据可供加载
			if (mCurIndex <= 1) {
				if (bean == null || bean.size() == 0) {
					Toast.makeText(this, "无相关信息！", Toast.LENGTH_SHORT).show();
				}
				datalist = bean;
			} else {
				if (bean != null)
					datalist.addAll(bean);
			}
			adapter.setData(datalist);
			adapter.notifyDataSetChanged();
			prlistview.onRefreshComplete();
		}
	}

	/**
	 * 描述：获取数据列表
	 * 
	 * */
	private void requestData() {

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		try {
			jsonObject2.put("executeId", staffid);
			jsonObject.put("paramsMap", jsonObject2);
			jsonObject.put("page", mCurIndex);
			jsonObject.put("rows", 10);

			this.showLoading("正在请求数据……", "worklist");
			requestHttp(jsonObject, "worklist", Constants.TDAYJOB_BOSSLIST,
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

	public class ReceivedBossAdapter extends BaseAdapter {

		private List<TDayJobDto> datalist;
		private Context context;

		public ReceivedBossAdapter(Context context, List<TDayJobDto> datalist) {
			// TODO Auto-generated constructor stub
			this.context = context;
			this.datalist = datalist;

		}

		public void setData(List<TDayJobDto> datalist) {
			
			
			this.datalist = datalist;
		}

		@Override
		public int getCount() {

			if (datalist != null) {
				return datalist.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			// return datalist.get(position);
			return datalist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.received_adapter, null);
				holder = new ViewHolder();

				holder.msgtitle = (TextView) convertView
						.findViewById(R.id.msgtitle);

				holder.msgdate = (TextView) convertView
						.findViewById(R.id.msgdate);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			TDayJobDto model = datalist.get(position);

			holder.msgtitle.setText(model.getJobTitle());
			holder.msgdate.setText(model.getJobDate());

			return convertView;
		}

		class ViewHolder {

			TextView msgtitle;// 标题
			TextView msgdate;// 日期
		}

	}

}
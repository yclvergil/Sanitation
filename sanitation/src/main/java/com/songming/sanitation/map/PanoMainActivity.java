package com.songming.sanitation.map;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.baidu.mapapi.model.LatLng;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.ApplicationExt;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;

/**
 * 全景
 */
public class PanoMainActivity extends BaseActivity implements OnClickListener {

	protected static final String TAG = "PanoMainActivity";

	private TextView topTitle;

	private LinearLayout top_left;

	private PanoramaView mPanoView;
	private LatLng ll = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBMapManager();
		setContentView(R.layout.pano_main_activity);

		ll = (LatLng) getIntent().getParcelableExtra("LatLng");
		findViews();
		initViews();
		if (ll != null) {
			// 先初始化BMapManager

			testPanoByType();
		}
	}

	@Override
	protected void findViews() {

		top_left = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		mPanoView = (PanoramaView) findViewById(R.id.panorama);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 查询围栏
	}

	public void initData() {

	}

	@Override
	protected void initViews() {

		top_left.setVisibility(View.VISIBLE);

		topTitle.setVisibility(View.VISIBLE);

		topTitle.setText("全景");

		top_left.setOnClickListener(this);
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";
		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
	}

	private void initBMapManager() {
		ApplicationExt app = (ApplicationExt) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(app);
			app.mBMapManager.init(new ApplicationExt.MyGeneralListener());
		}
	}

	private void testPanoByType() {

		// 设置全景图的俯仰角
		// mPanoView.setPanoramaPitch(pitch);
		// 获取当前全景图的俯仰角
		// 更新俯仰角的取值范围：室外景[-15, 90], 室内景[-25, 90],
		// 90为垂直朝上方向，0为水平方向
		// mPanoView.getPanoramaPitch();
		// 设置全景图的偏航角
		// mPanoView.setPanoramaHeading(heading);
		// 获取当前全景图的偏航角
		// mPanoView.getPanoramaHeading();
		// 设置全景图的缩放级别
		// level分为1-5级
		// mPanoView.setPanoramaLevel(level);
		// 获取当前全景图的缩放级别
		// mPanoView.getPanoramaLevel();
		// 是否显示邻接街景箭头（有邻接全景的时候）
		mPanoView.setShowTopoLink(true);
		// 设置全景图片的显示级别
		// 根据枚举类ImageDefinition来设置清晰级别
		// 较低清晰度 ImageDefinationLow
		// 中等清晰度 ImageDefinationMiddle
		// 较高清晰度 ImageDefinationHigh
		mPanoView
				.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionHigh);

		// 根据bitmap设置箭头的纹理(2.0.0新增)
		// mPanoView.setArrowTextureByBitmap(bitmap);
		// 根据url设置箭头的纹理(2.0.0新增)
		// mPanoView.setArrowTextureByUrl(url);

		mPanoView.setPanorama(ll.longitude, ll.latitude);
		// 测试回调函数,需要注意的是回调函数要在setPanorama()之前调用，否则回调函数可能执行异常
		mPanoView.setPanoramaViewListener(new PanoramaViewListener() {

			@Override
			public void onLoadPanoramaBegin() {
				Log.i(TAG, "onLoadPanoramaStart...");
			}

			@Override
			public void onLoadPanoramaEnd(String json) {
				Log.i(TAG, "onLoadPanoramaEnd : " + json);
			}

			@Override
			public void onLoadPanoramaError(String error) {
				Log.i(TAG, "onLoadPanoramaError : " + error);
			}
		});
	}

	@Override
	protected void onDestroy() {
		mPanoView.destroy();
		super.onDestroy();
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {

		case R.id.layoutback:
			finish();
			break;
		}
	}
}

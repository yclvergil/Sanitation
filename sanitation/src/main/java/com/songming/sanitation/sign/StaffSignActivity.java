package com.songming.sanitation.sign;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Bimp;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.HttpRequestTool;
import com.songming.sanitation.frameset.utils.ImageUtil;
import com.songming.sanitation.manage.bean.StaffDto;
import com.songming.sanitation.map.tool.BdMapLocationUtils;
import com.songming.sanitation.map.tool.BdMapLocationUtils.BdLocationSuccessListenner;
import com.songming.sanitation.sign.db.DBUtils;
import com.songming.sanitation.sign.db.SignBean;

/**
 * 签到界面
 * 
 * @author Administrator
 * 
 */
public class StaffSignActivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private TextView topTitle;
	private ImageView layoutbackimg;

	private TextView sname; // 名称
	private TextView sday;// 星期
	private TextView sdate;// 日期
	private TextView sduration;// 时长
	private TextView stime;// 当前时间
	private TextView splacename;// 地点名称
	private TextView splaceroad;// 地址路段

	private ImageView signin;// 签到
	private ImageView signout;// 签退
	private LinearLayout sign_layout;// 本人签到，员工签到栏状态

	private MapView mMapView; // MapView 是地图主控件
	private BaiduMap mBaiduMap;
	private LatLng llE;// 定位个人位置经纬度
	private BitmapDescriptor bdE = BitmapDescriptorFactory
			.fromResource(R.drawable.sign04);

	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			ImageUtil.getPhotoFileName());// 文件存储

	private int index = 0;
	private long fileId;
	private static final int UPLOAD_SUCCESS = 555;
	private static final int UPLOAD_FAIL = 444;
	private StaffDto bean;
	private int btnFlag = 0;
	private String curTime;
	private String date;
	private String city;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		setContentView(R.layout.signactivity);
		bean = (StaffDto) getIntent().getSerializableExtra("bean");

		findViews();
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		mMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();
		super.onDestroy();
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

		sname = (TextView) findViewById(R.id.sname);
		sday = (TextView) findViewById(R.id.sday);
		sdate = (TextView) findViewById(R.id.sdate);
		sduration = (TextView) findViewById(R.id.sduration);
		stime = (TextView) findViewById(R.id.stime);
		splacename = (TextView) findViewById(R.id.splacename);
		splaceroad = (TextView) findViewById(R.id.splaceroad);

		signin = (ImageView) findViewById(R.id.signin);
		signout = (ImageView) findViewById(R.id.signout);
		sign_layout = (LinearLayout) findViewById(R.id.sign_layout);

	}

	@Override
	protected void initViews() {

		topTitle.setText("员工签到");
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);

		sign_layout.setVisibility(View.GONE);

		sname.setText(bean.getName());
		sday.setText(getWeek());
		date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
		sdate.setText(date);
		sduration.setText("08:30:05");
		curTime = new SimpleDateFormat("HH:mm").format(new Date());
		stime.setText(curTime);

		signin.setOnClickListener(this);
		signout.setOnClickListener(this);

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.removeViewAt(2);// 移除百度地图右下角缩放图标
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setTrafficEnabled(false);
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);// 设置地图类型
		getLocation(index);
	}

	private void getLocation(final int index) {
		// 获取客户端当前的位置 坐标
		BdMapLocationUtils.getInstance().startLocation(
				new BdLocationSuccessListenner() {

					@Override
					public void locationResult(double _latitude,
							double _longitude, String _city,
							String _locationAddr, String _locationType) {
						llE = new LatLng(_latitude, _longitude);
						city = _locationAddr;
						splacename.setText(_city);
						splaceroad.setText(_locationAddr);

						if (mBaiduMap != null) {
							mBaiduMap.clear();
						}
						// 设置地图以动画方式切换,让target居中
						MapStatusUpdate u = MapStatusUpdateFactory
								.newMapStatus(new MapStatus.Builder()
										.target(llE).zoom(16).build());

						// 构建MarkerOption，用于在地图上添加Marker
						OverlayOptions option = new MarkerOptions()
								.position(llE).perspective(true)
								.anchor(0.5f, 1).icon(bdE);
						// 在地图上添加Marker，并显示
						mBaiduMap.addOverlay(option);
						mBaiduMap.animateMapStatus(u);

						if (index > 0) {
							//showDialog();
							takePhoto();
						}

					}
				});
	}

	private String getWeek() {
		final Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "日";
		} else if ("2".equals(mWay)) {
			mWay = "一";
		} else if ("3".equals(mWay)) {
			mWay = "二";
		} else if ("4".equals(mWay)) {
			mWay = "三";
		} else if ("5".equals(mWay)) {
			mWay = "四";
		} else if ("6".equals(mWay)) {
			mWay = "五";
		} else if ("7".equals(mWay)) {
			mWay = "六";
		}

		return "星期" + mWay;
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("stafflist".equals(requestStr)) {
			Toast.makeText(this, "获取失败", Toast.LENGTH_SHORT).show();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("stafflist", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("sign".equals(requestStr)) {
			String flag = jsonObject.optString("flag", "");
			if (flag.equals("false")) {
				if (index == 1) {
					Toast.makeText(this, "签到失败！", Toast.LENGTH_SHORT).show();

				} else if (index == 2) {
					Toast.makeText(this, "签退失败！", Toast.LENGTH_SHORT).show();

				}
			} else {
				if (index == 1) {
					btnFlag = 1;
					Toast.makeText(this, "签到成功!", Toast.LENGTH_SHORT).show();
					save2DB();
					signin.setImageResource(R.drawable.sign06);
					signout.setImageResource(R.drawable.sign07);

				} else if (index == 2) {
					btnFlag = 2;
					Toast.makeText(this, "签退成功！", Toast.LENGTH_SHORT).show();
					signin.setImageResource(R.drawable.sign05);
					signout.setImageResource(R.drawable.sign08);

				}

			}
		}

	}

	/**
	 * 描述：提交坐标数据，上班签到
	 * 
	 * */
	private void requestData() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("staffId", bean.getId());
			jsonObject.put("signLongitude", llE.longitude);
			jsonObject.put("signLatitude", llE.latitude);
			// 当index为1即为签到，2为签退
			if (index == 1) {
				jsonObject.put("signType", 1);
				this.showLoading("正在签到……", "signin");
			} else if (index == 2) {
				jsonObject.put("signType", 2);
				this.showLoading("正在签退……", "signin");
			}
			jsonObject.put("createOrgId", bean.getOrgId());
			List<JSONObject> jsons = new ArrayList<JSONObject>();
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("id", fileId);
			jsons.add(jsonObject2);
			jsonObject.put("fileKeys", jsons.toString());

			requestHttp(jsonObject, "sign", Constants.SIGN,
					Constants.SERVER_URL);
		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 签到照相对话框
	 */
	private void showDialog() {

		final Dialog dialog = new Dialog(this, R.style.dialog);
		dialog.setContentView(R.layout.cameradialog);
		ImageView cancle = (ImageView) dialog.findViewById(R.id.cancle);
		LinearLayout takephone = (LinearLayout) dialog
				.findViewById(R.id.takephoto);
		// 取消
		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}

		});
		// 拍照
		takephone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				takePhoto();
				dialog.dismiss();
			}
		});
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();

	}

	private void takePhoto() {
		Intent intentFromCapture = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
		intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(tempFile));
		startActivityForResult(intentFromCapture, 102);
	}
	/**
	 * 保存签到数据到数据库
	 */
	private void save2DB() {
		SignBean signBean = new SignBean(bean.getName(),city,getWeek() + " " + date,curTime,llE.longitude,llE.latitude,2);
		DBUtils.instance(this).save(signBean);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			final Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 102:
				StaffSignActivity.this.showLoading("请稍后…", "signin");
				new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							Bitmap bm = Bimp.revitionImageSize(tempFile
									.getPath());
							saveFile(bm);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
				break;
			default:
				break;
			}
		}
	}

	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
			case UPLOAD_SUCCESS:
				// 签到
				requestData();
				break;
			case UPLOAD_FAIL:
				// 上传照片失败
				Toast.makeText(StaffSignActivity.this, "上传图片失败！",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	};

	private void saveFile(Bitmap bmp) throws IOException {

		String fileName = ImageUtil.getPhotoFileName();
		File myCaptureFile = new File(getApplicationContext().getFilesDir()
				+ "/" + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bmp.compress(Bitmap.CompressFormat.PNG, 80, bos);
		bos.flush();
		bos.close();

		Map<String, String> mapParams = new HashMap<String, String>();
		Map<String, File> mapFiles = new HashMap<String, File>();
		mapFiles.put(fileName, myCaptureFile);

		try {
			JSONObject json = new JSONObject();
			json.put("fileType", "3");
			String url = Constants.SERVER_URL + Constants.UPLOADFILE
					+ "?param=" + json.toString();
			String data = HttpRequestTool.post(url, mapParams, mapFiles);

			JSONObject object = new JSONObject(data);
			boolean flag = object.optBoolean("flag");
			if (flag) {

				String datastr = object.optString("data");
				JSONObject object2 = new JSONObject(datastr);
				fileId = object2.optLong("id");
				// 签到
				handler.sendEmptyMessage(UPLOAD_SUCCESS);
			} else {
				handler.sendEmptyMessage(UPLOAD_FAIL);
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(UPLOAD_FAIL);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.signin:
			// 签到
			if (btnFlag == 1) {
				Toast.makeText(this, "请勿重复签到！", Toast.LENGTH_SHORT).show();
				return;
			}

			index = 1;
			getLocation(index);

			break;
		case R.id.signout:
			// 签退
			if (btnFlag == 2) {
				Toast.makeText(this, "请勿重复签退！", Toast.LENGTH_SHORT).show();
				return;
			}
			index = 2;
			getLocation(index);

			break;

		case R.id.layoutbackimg:
			this.finish();

			break;
		default:
			break;
		}
	}
}

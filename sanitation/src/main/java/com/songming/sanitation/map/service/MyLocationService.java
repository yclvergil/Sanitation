package com.songming.sanitation.map.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.songming.sanitation.Main;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.ApplicationExt;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.HttpRequestTool;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.manage.RecordLineActivity;
import com.songming.sanitation.map.model.LineItemModel;
import com.songming.sanitation.map.tool.BdMapLocationUtils;
import com.songming.sanitation.map.tool.BdMapLocationUtils.BdLocationSuccessListenner;

public class MyLocationService extends Service {
	// 经纬度范围 米
	public static final int LATITUDEL_RANGE = 100;
	public static final int HANDLERTYPE = 100;

	// 正在运行的id
	public static final String MYLOCATIONSERVICE_STRING = "MyLocationService_id";

	// 意图名称
	public static final String MYLOCATIONSERVICE = "com.songming.sanitation.map.service.MyLocationService";

	// 服务标示
	public static final String MYLOCATIONSERVICE_TYPE = "com.songming.sanitation.map.service.MyLocationService";

	// 开始服务
	public static final int MYLOCATIONSERVICE_START = 0x01;
	// 暂停服务
	public static final int MYLOCATIONSERVICE_STOP = 0x02;

	private ApplicationExt app;
	private double latitude;// 纬度
	private double longitude;// 经度
	private long createId;
	private int orgId;
	private long carId; // 车辆ID

	private Timer timer;

	private ArrayList<LineItemModel> datalist = new ArrayList<LineItemModel>();
	private static final int NOTIFICATION_ID = 0X88;

	/*
	 * 提供binder通信 (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		app = (ApplicationExt) getApplication();
	}

	private boolean isApplicationBroughtToBackground() {
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(getPackageName())) {
				return true;
			}
		}
		return false;
	}

	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			if (isApplicationBroughtToBackground()) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_MAIN);
				intent.setClass(getApplicationContext(),
						Main.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				startActivity(intent);
			}

			BdMapLocationUtils.getInstance().startLocation(
					new BdLocationSuccessListenner() {
						@Override
						public void locationResult(double _latitude,
								double _longitude, String _city,
								String _locationAddr, String _locationType) {

							if (latitude == 0.0 || longitude == 0.0) {
								latitude = _latitude;
								longitude = _longitude;
								execute();
							} else {
								double distance = DistanceUtil.getDistance(
										new LatLng(_latitude, _longitude),
										new LatLng(latitude, longitude));

								if (Math.abs(distance) >= 10) {

									latitude = _latitude;
									longitude = _longitude;
									execute();
								}
							}

							// 初始化覆盖物
							LatLng ll = new LatLng(_latitude, _longitude);
							RecordLineActivity.moveMapCenter(ll);
							RecordLineActivity.initMyLoaction(ll);
						}
					});

		}
	};

	@SuppressWarnings("unchecked")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			int type = intent.getIntExtra(MYLOCATIONSERVICE_TYPE,
					MYLOCATIONSERVICE_START);
			switch (type) {
			// 开始
			case MYLOCATIONSERVICE_START:
				BdMapLocationUtils.getInstance().stopLocation();
				createId = SharedPreferencesUtils.getLongValue(
						getApplicationContext(),
						SharedPreferencesUtils.STAFF_ID, -1);
				orgId = SharedPreferencesUtils.getIntValue(
						getApplicationContext(), SharedPreferencesUtils.ORG_ID,
						-1);
				carId = intent.getLongExtra("carId", -1); // 车辆ID
				Log.d("aa", "service--" + createId + "~~" + orgId + "~~"
						+ carId);
				runTimer();
				showNotification();
				break;

			default:
				break;
			}
		}

		return START_REDELIVER_INTENT;
	}

	private void execute() {
		if (createId == -1 || orgId == -1 || carId == -1) {
			return;
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("createId", createId);
					jsonObject.put("carId", carId);
					jsonObject.put("createOrgId", orgId);
					jsonObject.put("longitude", longitude);
					jsonObject.put("latitude", latitude);

					String dataPost = HttpRequestTool.sendPost(
							Constants.SERVER_URL + Constants.CARLOCATION,
							"param=" + jsonObject.toString());

					// Log.i("aa", "execute~~~: " + dataPost);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void runTimer() {
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				myHandler.sendEmptyMessage(0);
			}
		}, 500, 15 * 1000); // 每15秒获取一次位置坐标
	}

	private void showNotification() {
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.app_logo,
				getText(R.string.app_name), System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, RecordLineActivity.class);
		notificationIntent.putExtra("carId", carId);
		notificationIntent.setAction(Intent.ACTION_MAIN);
		notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(this, "车辆轨迹上传",
				getText(R.string.app_name), pendingIntent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		// nm.notify(NOTIFICATION_ID, notification);
		startForeground(NOTIFICATION_ID, notification);

	}

	@Override
	public void onDestroy() {

		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		stopForeground(true);
		super.onDestroy();
	}
}

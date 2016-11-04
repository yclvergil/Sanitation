package com.songming.sanitation.sign;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.songming.sanitation.Main;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.HttpRequestTool;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.frameset.utils.ThreadPoolUtils;
import com.songming.sanitation.frameset.utils.WifiNetUtils;
import com.songming.sanitation.map.tool.BdMapLocationUtils;
import com.songming.sanitation.map.tool.BdMapLocationUtils.BdLocationSuccessListenner;

/**
 * 员工实时定位坐标上传服务
 *
 * @author Administrator
 */
public class PersonalLocationService extends Service {
    // 经纬度范围 米
    public static final int LATITUDEL_RANGE = 100;
    public static final int HANDLERTYPE = 100;

    // 正在运行的id
    public static final String MYLOCATIONSERVICE_STRING = "PersonalLocationService_id";

    // 意图名称
    public static final String MYLOCATIONSERVICE = "com.songming.sanitation.sign.PersonalLocationService";

    // 服务标示
    public static final String MYLOCATIONSERVICE_TYPE = "com.songming.sanitation.sign.PersonalLocationService";

    // 开始服务
    public static final int MYLOCATIONSERVICE_START = 0x01;
    private static final int GRAY_SERVICE_ID = 0x02;

    private double latitude;// 纬度
    private double longitude;// 经度
    private long createId;
    private int orgId;

    private static final long MIN_10 = 10 * 60 * 1000;//10分钟
    private static final long MIN_1 = 1 * 60 * 1000;//1分钟
    private AlarmManager manager;
    private AlarmReceiver receiver;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //注册广播
        receiver = new AlarmReceiver();
        IntentFilter intentFilter = new IntentFilter(AlarmReceiver.class.getSimpleName());
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
        int type = MYLOCATIONSERVICE_START;
        boolean flag = SharedPreferencesUtils.getBooleanValue(this.getApplicationContext(), Constants.SIGNFLAG, false);
        if (intent != null) {
            type = intent.getIntExtra(MYLOCATIONSERVICE_TYPE,
                    MYLOCATIONSERVICE_START);
        }
        Log.d("PersonalLocationService", "flag:" + flag);


        boolean hasNet = false;
        //上班时段才上传
        if (flag) {
            BdMapLocationUtils.getInstance().stopLocation();
            createId = SharedPreferencesUtils.getLongValue(
                    getApplicationContext(),
                    SharedPreferencesUtils.STAFF_ID, -1);
            orgId = SharedPreferencesUtils.getIntValue(
                    getApplicationContext(), SharedPreferencesUtils.ORG_ID,
                    -1);
            Log.d("aa", "service--" + createId + "~~" + orgId);
            BdMapLocationUtils.getInstance().startLocation(
                    new BdLocationSuccessListenner() {
                        @Override
                        public void locationResult(double _latitude,
                                                   double _longitude, String _city,
                                                   String _locationAddr, String _locationType) {
                            latitude = _latitude;
                            longitude = _longitude;
                            execute();
                        }
                    });
            hasNet = WifiNetUtils
                    .isNetworkConnected(getApplicationContext());

            if (!hasNet) {
                runAlarm(MIN_1);
            } else {
                runAlarm(MIN_10);
            }
        }
                /*if (isApplicationBroughtToBackground()) {
                    Intent i = new Intent();
					intent.setAction(Intent.ACTION_MAIN);
					intent.setClass(getApplicationContext(), Main.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
					startActivity(intent);
				}*/


        return START_STICKY;
    }

    /**
     * 上传GPS
     */
    private void execute() {
        if (createId == -1 || orgId == -1) {
            return;
        }
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("createId", createId);
                    jsonObject.put("staffId", createId);
                    jsonObject.put("createOrgId", orgId);
                    jsonObject.put("longitude", longitude);
                    jsonObject.put("latitude", latitude);

                    String dataPost = HttpRequestTool.sendPost(
                            Constants.SERVER_URL + Constants.STAFFLOCATION,
                            "param=" + jsonObject.toString());
                    Log.d("PersonalLocationService", "经纬度：" + longitude + "::::" + latitude);
//					Log.i("aa", "execute~~~: " + dataPost);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Alarm定时器
     */
    private void runAlarm(long delay) {
        if (manager == null) {
            manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        long triggerAtTime = SystemClock.elapsedRealtime() + delay;
        Intent intent = new Intent(AlarmReceiver.class.getSimpleName());
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
    }

    class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(context, PersonalLocationService.class);
            context.startService(i);
        }
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    }

    @Override
    public void onDestroy() {
        //发送启动服务广播
        this.sendBroadcast(new Intent(AlarmReceiver.class.getSimpleName()));
        unregisterReceiver(receiver);
        super.onDestroy();
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

}

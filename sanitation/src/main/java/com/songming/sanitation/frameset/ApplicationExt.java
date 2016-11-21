package com.songming.sanitation.frameset;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.mapapi.SDKInitializer;
import com.songming.sanitation.frameset.utils.Tools;
import com.songming.sanitation.user.LoginAcitivity;
import com.songming.sanitation.user.model.UserDto;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.List;

public class ApplicationExt extends Application {
    // user your appid the key.
    private static final String APP_ID = "2882303761517524984";
    // user your appid the key.
    private static final String APP_KEY = "5161752469984";

    // 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep
    // com.xiaomi.mipushdemo
    public static final String TAG = "com.test.mipushdemo";

    private static ApplicationExt application;

    private final static String userLocal = "userinfo";

    // 缓存失效时间
    private static final int CACHE_TIME = 60 * 60000;

    public String cookie;

    // 登录状态
    private boolean login = false;

    // 绑定手机到百度云推送
    public boolean isBind = false;

    // 百度推送手机channelId
    public String channelId = "";

    private UserDto user;

    private String httpUrl;

    public BMapManager mBMapManager = null;
    public static UserDto nowUser = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext 百度地图
        SDKInitializer.initialize(this);

        VolleyQueue.init(this);
        application = this;
        // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
        // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
        channelId = Tools.getIMEI(this);
    }


    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(new MyGeneralListener())) {
            Toast.makeText(ApplicationExt.application, "BMapManager  初始化错误!",
                    Toast.LENGTH_LONG).show();
        }
        Log.d("ljx", "initEngineManager");
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public static class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
            if (iError != 0) {
                // 授权Key错误：
                // Toast.makeText(
                // App.getInstance().getApplicationContext(),
                // "请在AndoridManifest.xml中输入正确的授权Key,并检查您的网络连接是否正常！error: "
                // + iError, Toast.LENGTH_LONG).show();
            } else {
                // Toast.makeText(App.getInstance().getApplicationContext(),
                // "key认证成功", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        VolleyQueue.cancelAllQueue();
    }

    /**
     * 检测当前系统声音是否为正常模式
     *
     * @return boolean
     */
    public boolean isAudioNormal() {
        AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
    }

    /**
     * 应用程序是否发出提示音
     *
     * @return boolean
     */
    public boolean isAppSound() {
        return isAudioNormal();
    }

    public PackageInfo getPackInfo() {
        PackageInfo pkg = null;
        try {
            pkg = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return pkg;
    }

    public static ApplicationExt getApplication() {
        return application;
    }

    public String getKey(String orginkey) {
        return URLEncoder.encode("key_" + orginkey);
    }

    /**
     * 判断缓存是否存在
     *
     * @param cachefile
     * @return boolean
     */
    private boolean isExistDataCache(String cachefile) {
        boolean exist = false;
        File data = getFileStreamPath(cachefile);
        if (data.exists())
            exist = true;
        return exist;
    }

    /**
     * 判断缓存是否失效
     *
     * @param cachefile
     * @return
     */
    public boolean isCacheDataFailure(String cachefile) {
        boolean failure = false;
        File data = getFileStreamPath(cachefile);
        if (data.exists()
                && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME)
            failure = true;
        else if (!data.exists())
            failure = true;
        return failure;
    }

    /**
     * 读取对象
     *
     * @param file
     * @return
     * @throws IOException
     */
    public Serializable readObject(String file) {
        if (!isExistDataCache(file))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 保存对象
     *
     * @param ser
     * @param file
     * @throws IOException
     */
    public boolean saveObject(Serializable ser, String file) {
        if (ser == null) {
            return false;
        }
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput(file, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 用户注销退出
     */
    public void clearUserData() {
        File data = getFileStreamPath(getKey(userLocal));
        if (data.exists())
            data.delete();
    }

    /**
     * 用户是否登录
     *
     * @return
     */
    public boolean isLogin() {
        login = user != null && user.getLoginName() != null;
        // if (!login) {
        // login = (readObject(getKey(userLocal)) != null);
        // }
        return login;
    }

    public boolean goLogin(Context context) {
        if (!isLogin()) {
            context.startActivity(new Intent(context, LoginAcitivity.class));
            return false;
        }
        return true;

    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /*MiPush*/
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

}

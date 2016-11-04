package com.songming.sanitation.sign;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;

/**
 * Created by Administrator on 2016/11/1.
 */

public class ProtectServiceReceiver extends BroadcastReceiver {
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        boolean flag = SharedPreferencesUtils.getBooleanValue(context, Constants.SIGNFLAG, false);
        if (!flag) {
            return;
        }
        if (!isMyServiceRunning(PersonalLocationService.class)) {
            Intent i = new Intent(context, PersonalLocationService.class);
            context.startService(i);
        }

    }

    /**
     * 判断服务是否在运行
     * @param serviceClass
     * @return
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(100)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

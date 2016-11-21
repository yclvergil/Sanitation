package com.songming.sanitation.pushservice;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.sign.SignActivity;
import com.songming.sanitation.user.SystemMsgAcitivity;
import com.songming.sanitation.workdeal.WorkDealActivity;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class MiPushMessageReceiver extends PushMessageReceiver {

    public static String mAlias = "";
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = arguments.get(0);
            }
        }
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        updateContent(context,miPushMessage.toString());
        super.onNotificationMessageClicked(context, miPushMessage);
    }

    private void updateContent(Context context, String content) {
        String logText = "" + Utils.logStringCache;

        if (!logText.equals("")) {
            logText += "\n";
        }

        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH-mm-ss");
        logText += sDateFormat.format(new Date()) + ": ";
        logText += content;

        Utils.logStringCache = logText;

        if (isMyAppAlive(context.getApplicationContext())) {
            //如果APP处于活动中，直接打开工单处理页面
            Intent intent = new Intent();
            if (content.contains("签到提醒")) {
                intent.setClass(context.getApplicationContext(),
                        SignActivity.class);
            } else if (content.contains("事件")) {
                intent.setClass(context.getApplicationContext(),
                        WorkDealActivity.class);
            } else {
                intent.setClass(context.getApplicationContext(),
                        SystemMsgAcitivity.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intent);
        } else {
            //如果APP未启动，则先启动APP
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.songming.sanitation");
            if (content.contains("签到提醒")) {
                launchIntent.putExtra("signNotification", true);
            } else if (content.contains("事件")) {
                launchIntent.putExtra("fromNotification", true);
            }
            launchIntent.setAction(Intent.ACTION_MAIN);
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(launchIntent);

        }
    }

    /**
     * 判断我的app是否正在运行
     */
    private boolean isMyAppAlive(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(Integer.MAX_VALUE);
        String MY_PKG_NAME = "com.songming.sanitation";
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME)
                    || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                return true;
            }
        }

        return false;
    }
}

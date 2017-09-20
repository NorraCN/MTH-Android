package com.norra.cloud.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.norra.cloud.CloudApp;
import com.norra.cloud.R;
import com.norra.cloud.activity.AlertActivity;
import com.norra.cloud.utils.Logger;

import java.io.Serializable;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private static final String STOP_RING_ACTION = "cn.jpush.android.intent.STOP_RING";

    private static final int notifyId = 15782;
    private static final int RING_TIME = 1000 * 10; // 5秒钟


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Logger.d(TAG, "JPush, Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "JPush, Customer Msg: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            if (bundle.getBoolean("from_customer_notification", false)) {
                onNotificationOpened(context, bundle.getString(JPushInterface.EXTRA_MESSAGE));
            }
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Logger.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else if (STOP_RING_ACTION.equals(intent.getAction())) {
            //停止播放消息提示音
            Logger.d(TAG, "stop");
            stopRing();
        }
    }

    /**
     * 停止闹铃
     */
    public static void stopRing() {
        if (player != null) {
            player.stop();
            player = null;
        }
    }

    private static SoundPlayer player;

    // send msg to MainActivity
    private void processCustomMessage(final Context context, Bundle bundle) {

        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        PushInfo pushInfo = null;
        try {
            Gson gson = new Gson();
            pushInfo = gson.fromJson(message, PushInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
//        User user = CloudApp.getApp().getUser();
//        if (pushInfo.user == null || !user.isAuthorized())
//            return;
//        if (!pushInfo.user.equals(user.getUserName()))
//            return;

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify = new Notification();
        notify.icon = R.drawable.warn_notify;
        notify.tickerText = context.getString(R.string.data_error);

        notify.when = System.currentTimeMillis();
        notify.flags = Notification.FLAG_AUTO_CANCEL;

        Intent openNotificationIntent = new Intent(JPushInterface.ACTION_NOTIFICATION_OPENED);
        openNotificationIntent.putExtras(bundle);
        openNotificationIntent.putExtra("from_customer_notification", true);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, R.string.app_name, openNotificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notify.flags = Notification.FLAG_AUTO_CANCEL;
        notify.setLatestEventInfo(context, context.getString(R.string.data_error), pushInfo.tips, contentIntent);

        Intent deleteIntent = new Intent(STOP_RING_ACTION);
        PendingIntent deleteBroadcast = PendingIntent.getBroadcast(context, R.string.app_name, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notify.deleteIntent = deleteBroadcast;
        nm.notify(notifyId, notify);

        playRing(context);

    }

    private void playRing(Context context) {
        if (player == null || !player.isPlaying()) {
            player = new SoundPlayer(context);
            player.play();
            CloudApp.getApp().postDelay(new Runnable() {
                @Override
                public void run() {
                    stopRing();
                }
            }, RING_TIME);
        }
    }

    /**
     * 用户点击通知栏时促发
     *
     * @param context
     * @param msg
     */
    private void onNotificationOpened(Context context, String msg) {
        stopRing();
        if (msg == null) {
            return;
        }
        PushInfo pushInfo = null;
        try {
            Gson gson = new Gson();
            pushInfo = gson.fromJson(msg, PushInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //if (pushInfo.msgType == 0) {
            Intent intent = new Intent(context, AlertActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("push_info", pushInfo);
            context.startActivity(intent);
        //}

    }

    public class PushInfo implements Serializable {
        public String id;
        public String title;
        public String tips;
        public String name;
        public Long time;
        public String state1;
        public String state2;
        public String state3;
        public String state4;
        public Byte stateE;
    }

}

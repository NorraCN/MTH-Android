package com.norra.cloud;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.norra.cloud.entity.User;
import com.norra.cloud.utils.ActivityStack;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by liurenhui on 15/1/20.
 */
public class CloudApp extends Application {
    public static final boolean isDebug = false;
    static CloudApp mApp;
    static Handler mainHandler;
    private User mUser;
    private ActivityStack activityStack;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mainHandler = new Handler(Looper.getMainLooper());
        mUser = new User();
        activityStack = new ActivityStack();
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    public User getUser() {
        return mUser;
    }

    public void post(Runnable r) {
        mainHandler.post(r);
    }

    public void postDelay(Runnable r, int delayMillis) {
        mainHandler.postDelayed(r, delayMillis);
    }

    public static CloudApp getApp() {
        return mApp;
    }

    public ActivityStack getActivityStack() {
        return activityStack;
    }
}

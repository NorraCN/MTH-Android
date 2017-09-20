package com.norra.cloud.http.frame;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.util.Log;

public abstract class ResponseListener<T> {

	public boolean showToast = true;

	public abstract void onSuccess(T response);

	public void onComplete(T response) {
	}
    private static long lastShowErrorToastTime = 0;

	public void onError(HError error) {
	}
	
	/**
	 * 是否在后台运行
	 * @param context
	 * @return
	 */
	public static boolean isBackground(Context context) {
		
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					Log.d("Background App:", "API:" + appProcess.processName);
					return true;
				}else{
					Log.d("Foreground App:", "API:" + appProcess.processName);
					return false;
				}
			}
		}
		return false;
	} 
}
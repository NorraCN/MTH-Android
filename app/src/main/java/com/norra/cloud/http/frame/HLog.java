package com.norra.cloud.http.frame;

import com.norra.cloud.CloudApp;

import android.util.Log;

/**
 * 自定义log类
 * @author CharLiu
 *
 */
public class HLog {
	
	public static class Config {
		public static final String HTTP_TAG = "Android-Http";
		public static final boolean LOG_V = CloudApp.isDebug;
		public static final boolean LOG_D = CloudApp.isDebug;
		public static final boolean LOG_I = CloudApp.isDebug;
		public static final boolean LOG_E = CloudApp.isDebug;
		public static final boolean LOG_W = CloudApp.isDebug;
	}

	public static void d(String msg) {
		if (Config.LOG_D) {
			Log.d(Config.HTTP_TAG, msg);
		}
	}
	public static void w(String msg) {
		if (Config.LOG_W) {
			Log.w(Config.HTTP_TAG, msg);
		}
	}

	public static void v(String msg) {
		if (Config.LOG_V) {
			Log.v(Config.HTTP_TAG, msg);
		}
	}

	public static void e(String msg) {
		if (Config.LOG_E) {
			Log.e(Config.HTTP_TAG, msg);
		}
	}
	
	public static void i(String msg) {
		if (Config.LOG_I) {
			Log.i(Config.HTTP_TAG, msg);
		}
	}
	
	public static void printThrowable(Throwable t) {
		if (Config.LOG_E) {
			t.printStackTrace();
		}
	}

}

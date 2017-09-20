package com.norra.cloud.utils;

import com.norra.cloud.CloudApp;
import android.util.Log;

/**
 * Created by liurenhui on 15/1/20.
 */
public class Logger {

    private final static String TAG = "Cloud";
    public final static boolean debug = CloudApp.isDebug;

    /**
     * log.i
     */
    public static void i(String msg) {
        if (debug) {
            String msgs[] = divideMsg(msg);
            for (String str : msgs) {
                Log.d(TAG, str);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (debug) {
            String msgs[] = divideMsg(msg);
            for (String str : msgs) {
                Log.i(tag, str);
            }
        }
    }


    public static void d(String msg) {
        if (debug) {
            String msgs[] = divideMsg(msg);
            for (String str : msgs) {
                Log.d(TAG, str);
            }
        }
    }

    private static final int LOG_MAX_SIZE = 500;

    private static String[] divideMsg(String msg) {
        if (msg.length() > LOG_MAX_SIZE) {
            int size = msg.length() / LOG_MAX_SIZE;
            int left = msg.length() % LOG_MAX_SIZE;
            int length = size;
            if (left > 0) {
                length += 1;
            }
            String[] result = new String[length];
            for (int i = 0; i < length - 1; i++) {
                result[i] = msg.substring(i * LOG_MAX_SIZE, (i + 1) * LOG_MAX_SIZE);
            }
            result[length - 1] = msg.substring((length - 1) * LOG_MAX_SIZE);
            return result;
        }
        return new String[]{msg};
    }

    public static void d(String tag, String msg) {
        if (debug) {
            String msgs[] = divideMsg(msg);
            for (String str : msgs) {
                Log.d(tag, str);
            }
        }
    }

    public static void e(String msg) {
        if (debug) {
            String msgs[] = divideMsg(msg);
            for (String str : msgs) {
                Log.e(TAG, str);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (debug) {
            String msgs[] = divideMsg(msg);
            for (String str : msgs) {
                Log.d(tag, str);
            }
        }
    }
}


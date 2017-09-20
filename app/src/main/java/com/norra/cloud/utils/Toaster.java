package com.norra.cloud.utils;

import android.widget.Toast;

import com.norra.cloud.CloudApp;

/**
 * Created by liurenhui on 15/1/21.
 */
public class Toaster {

    public static void shortToast(String msg) {
        Toast.makeText(CloudApp.getApp(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void shortToast(int msg) {
        Toast.makeText(CloudApp.getApp(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(String msg) {
        Toast.makeText(CloudApp.getApp(), msg, Toast.LENGTH_LONG).show();
    }

    public static void longToast(int msg) {
        Toast.makeText(CloudApp.getApp(), msg, Toast.LENGTH_LONG).show();
    }
}

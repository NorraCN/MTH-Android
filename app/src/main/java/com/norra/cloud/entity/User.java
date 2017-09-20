package com.norra.cloud.entity;

import android.content.Context;
import android.content.SharedPreferences;

import com.norra.cloud.CloudApp;

/**
 * Created by liurenhui on 15/1/20.
 */
public class User {
    private final String APP_TOKEN_KEY = "APP_TOKEN_KEY";
    private final String USER_PREFERENCE = "USER_PREFERENCE";
    private final String USER_NAME_KEY = "USER_NAME_KEY";
    private SharedPreferences sp;
    private String appToken;
    private String userName;

    public User() {
        init();
    }

    private void init() {
        sp = CloudApp.getApp().getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
        appToken = sp.getString(APP_TOKEN_KEY, null);
        userName = sp.getString(USER_NAME_KEY, null);
    }

    public void saveAppToken(String appToken) {
        sp.edit().putString(APP_TOKEN_KEY, appToken).commit();
        this.appToken = appToken;
    }

    public String getAppToken() {
        return this.appToken;
    }

    public boolean isAuthorized() {
        return appToken != null;
    }

    public void logout() {
        sp.edit().remove(APP_TOKEN_KEY).commit();
        this.appToken = null;
    }

    public String getUserName() {
        return userName;
    }

    public void saveUserName(String userName) {
        this.userName = userName;
        sp.edit().putString(USER_NAME_KEY, userName).commit();
    }
}

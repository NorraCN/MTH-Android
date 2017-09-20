package com.norra.cloud.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

import com.norra.cloud.CloudApp;
import com.norra.cloud.R;
import com.norra.cloud.entity.User;
import com.norra.cloud.utils.ActivityStack;
import com.norra.cloud.utils.Toaster;

/**
 * Created by liurenhui on 15/1/20.
 */
public class BaseActivity extends FragmentActivity {
    protected User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = CloudApp.getApp().getUser();
        getActivityStack().push(this);
    }

    protected void onTokenExpired() {
        mUser.logout();
        getActivityStack().finishAll();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Toaster.longToast(R.string.login_state_expired);
        finish();
    }

    public ActivityStack getActivityStack() {
        return CloudApp.getApp().getActivityStack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getActivityStack().remove(this);
    }
}

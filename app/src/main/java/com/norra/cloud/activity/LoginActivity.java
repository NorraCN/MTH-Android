package com.norra.cloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.norra.cloud.R;
import com.norra.cloud.api.API;
import com.norra.cloud.api.response.LoginBaseResponse;
import com.norra.cloud.api.response.SessionIdResponse;
import com.norra.cloud.http.frame.HError;
import com.norra.cloud.http.frame.ResponseListener;
import com.norra.cloud.utils.Toaster;

import cn.jpush.android.api.JPushInterface;

import static android.view.View.OnClickListener;


public class LoginActivity extends BaseActivity implements OnClickListener {
    private EditText userNameView;
    private EditText passwordView;
    private TextView errorTips;
    private Button loginButton;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI() {
        userNameView = (EditText) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login_btn);
        errorTips = (TextView) findViewById(R.id.error_tips);

        loginButton.setOnClickListener(this);

        if (mUser.getUserName() != null) {
            userNameView.setText(mUser.getUserName());
        }
        getSessionIdForLogin();
    }

    private void getSessionIdForLogin() {

        API.getSessionId(null, new ResponseListener<SessionIdResponse>() {
            @Override
            public void onSuccess(SessionIdResponse response) {
                sessionId = response.getJsessionid();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == loginButton.getId()) {
            login();
        }
    }

    private void login() {
        errorTips.setText("");
        final String userName = userNameView.getText().toString().trim();
        String password = passwordView.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toaster.shortToast(R.string.input_username);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toaster.shortToast(R.string.input_password);
            return;
        }


        String registrationId = JPushInterface.getRegistrationID(this);
        API.login(this, userName, password, sessionId, registrationId, new ResponseListener<LoginBaseResponse>() {
            @Override
            public void onSuccess(LoginBaseResponse response) {
                mUser.saveAppToken(response.getAppToken());
                mUser.saveUserName(userName);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(HError error) {
                errorTips.setText(R.string.login_fail);
            }
        });

    }

//    private void getValidateCode() {
//        BitmapRequest request = new BitmapRequest(API.DOMAIN + API.LOGIN_VALIDATE_CODE + ";jsessionid=" + sessionId);
//        request.setResponseListener(new ResponseListener<Bitmap>() {
//            @Override
//            public void onSuccess(Bitmap response) {
//                validateImageView.setBackgroundDrawable(new BitmapDrawable(response));
//            }
//
//            @Override
//            public void onError(HError error) {
//                super.onError(error);
//            }
//        });
//        HttpExecutor.execute(request);
//    }
}

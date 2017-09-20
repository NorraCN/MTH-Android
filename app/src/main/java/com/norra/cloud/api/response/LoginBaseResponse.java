package com.norra.cloud.api.response;

/**
 * Created by liurenhui on 15/1/20.
 */
public class LoginBaseResponse extends BaseResponse {
    private String appToken;

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }
}

package com.norra.cloud.api.response;

/**
 * Created by liurenhui on 15/1/20.
 */
public class SessionIdResponse extends BaseResponse {
    private String jsessionid;

    public String getJsessionid() {
        return jsessionid;
    }

    public void setJsessionid(String jsessionid) {
        this.jsessionid = jsessionid;
    }
}

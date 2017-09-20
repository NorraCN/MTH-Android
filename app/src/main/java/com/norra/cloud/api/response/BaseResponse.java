package com.norra.cloud.api.response;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by liurenhui on 15/1/20.
 */
public class BaseResponse {
    private int result;
    private String message;

    public BaseResponse parse(String json, Type cls) {
        Gson gson = new Gson();
        return gson.fromJson(json, cls);
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

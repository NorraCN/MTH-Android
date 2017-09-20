package com.norra.cloud.http;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.norra.cloud.CloudApp;
import com.norra.cloud.api.API;
import com.norra.cloud.api.response.BaseResponse;
import com.norra.cloud.http.frame.HError;
import com.norra.cloud.http.frame.NetworkResponse;
import com.norra.cloud.http.frame.Request;
import com.norra.cloud.http.frame.Response;
import com.norra.cloud.utils.Logger;

import java.io.UnsupportedEncodingException;

/**
 * Created by liurenhui on 15/1/20.
 */
public class ObjectRequest<T extends BaseResponse> extends Request<T> {
    private final static String API_TAG = "API";
    private static String defaultDomain = API.DOMAIN;
    Class<T> cls;
    private String method;// 后台接口名称

    public String getMethod() {
        return this.method;
    }

    public ObjectRequest(String method, Class<T> responseCls) {
        super(defaultDomain + method);
        this.method = method;
        this.cls = responseCls;
        addParam("appToken", CloudApp.getApp().getUser().getAppToken());
    }


    @Override
    public Response parseResponse(NetworkResponse response) {
        String parsedString;
        try {
            parsedString = new String(response.data, response.getCharset());
        } catch (UnsupportedEncodingException e) {
            parsedString = new String(response.data);
        }
        try {
            if (parsedString != null) {
                Logger.d(API_TAG, "Response:" + method + "\n" + parsedString);
                T mCls = cls.newInstance();
                BaseResponse result = mCls.parse(parsedString, cls);
                if (result.getResult() == 0) {
                    return Response.success((T) result);
                } else {
                    return Response.error(new HError(result.getResult(), result.getMessage()));
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.e(API_TAG, "Parse Error:" + parsedString);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.error(new HError(HError.PARSE_ERROR, "parse error"));
    }
}

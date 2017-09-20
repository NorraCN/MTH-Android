package com.norra.cloud.api;

import android.app.Activity;

import com.norra.cloud.api.response.BaseResponse;
import com.norra.cloud.api.response.DeviceDetailResponse;
import com.norra.cloud.api.response.DeviceGroupResponse;
import com.norra.cloud.api.response.HistoryDataResponse;
import com.norra.cloud.api.response.LoginBaseResponse;
import com.norra.cloud.api.response.SessionIdResponse;
import com.norra.cloud.http.ObjectRequest;
import com.norra.cloud.http.frame.HttpExecutor;
import com.norra.cloud.http.frame.ResponseListener;

/**
 * Created by liurenhui on 15/1/20.
 * Modified by shenletao after 16/7/1
 */
public class API {

    public static final String DOMAIN = "http://api.norra.cn/";
    public static final String SESSION_ID = "json/jsessionid";
    //public static final String LOGIN_VALIDATE_CODE = "front/codingImage";
    public static final String LOGIN = "json/login";
    public static final String LOGOUT = "json/logout";
    public static final String DEVICE_LIST = "json/deviceList2";
    public static final String DEVICE_DETAIL = "json/devDetails2";
    //public static final String DEVICE_DYNAMIC_DATA = "json/dynamic";
    public static final String HISTORY_DATA = "json/history2";
    public static final String OPEN_CLOSE_ALARM = "json/alarmCtrl";
    //public static final String DEVICE_TYPE = "json/deviceType";

    public static void getSessionId(Activity context, ResponseListener<SessionIdResponse> listener) {
        ObjectRequest<SessionIdResponse> request = new ObjectRequest<SessionIdResponse>(SESSION_ID, SessionIdResponse.class);
        request.setResponseListener(listener);
        HttpExecutor.execute(request, context);
    }

    public static void login(Activity context, String userName, String password, String jsessionid, String jpushToken, ResponseListener<LoginBaseResponse> listener) {
        String loginUrl = LOGIN + ";jsessionid=" + jsessionid;
        ObjectRequest<LoginBaseResponse> request = new ObjectRequest<LoginBaseResponse>(loginUrl, LoginBaseResponse.class);
        request.addParam("userName", userName);
        request.addParam("ticket", password);
        request.addParam("securityCode", "app");
        request.addParam("appTarget", jpushToken);
        request.addParam("appPlatform", "android");
        request.setResponseListener(listener);
        HttpExecutor.execute(request, context);
    }

    public static void logout(Activity context, String appToken, ResponseListener<BaseResponse> listener) {
        ObjectRequest<BaseResponse> request = new ObjectRequest<>(LOGOUT, BaseResponse.class);
        request.addParam("appToken", appToken);
        HttpExecutor.execute(request, context);
    }

    public static void getDeviceList(Activity context, ResponseListener<DeviceGroupResponse> listener) {
        ObjectRequest<DeviceGroupResponse> request = new ObjectRequest<DeviceGroupResponse>(DEVICE_LIST, DeviceGroupResponse.class);
        request.setResponseListener(listener);
        HttpExecutor.execute(request, context);
    }

//    public static void getDeviceList(Activity context, int pageNum, int pageSize, ResponseListener<DeviceListResponse> listener) {
//        ObjectRequest<DeviceListResponse> request = new ObjectRequest<DeviceListResponse>(DEVICE_DYNAMIC_DATA, DeviceListResponse.class);
//        request.addParam("pageNum", pageNum);
//        request.addParam("pageSize", pageSize);
//        request.setResponseListener(listener);
//        HttpExecutor.execute(request, context);
//    }

    public static void getDeviceDetail(Activity context, String deviceId, ResponseListener<DeviceDetailResponse> listener) {
        ObjectRequest<DeviceDetailResponse> request = new ObjectRequest<DeviceDetailResponse>(DEVICE_DETAIL, DeviceDetailResponse.class);
        request.addParam("deviceId", deviceId);
        request.setResponseListener(listener);
        HttpExecutor.execute(request, context);
    }

//    public static void getDeviceData(Activity context, int pageNum, int pageSize, ResponseListener<DeviceDataResponse> listener) {
//        ObjectRequest<DeviceDataResponse> request = new ObjectRequest<DeviceDataResponse>(DEVICE_DYNAMIC_DATA, DeviceDataResponse.class);
//        request.addParam("pageNum", pageNum);
//        request.addParam("pageSize", pageSize);
//        request.setResponseListener(listener);
//        HttpExecutor.execute(request, context);
//    }

    public static void getDeviceDataByTime(Activity context, int pageNum, int pageSize, String deviceId, String start, String end, ResponseListener<HistoryDataResponse> listener) {
        ObjectRequest<HistoryDataResponse> request = new ObjectRequest<HistoryDataResponse>(HISTORY_DATA, HistoryDataResponse.class);
        request.addParam("pageNum", pageNum);
        request.addParam("pageSize", pageSize);
        request.addParam("start", start);
        request.addParam("end", end);
        request.addParam("deviceId", deviceId);
        request.setResponseListener(listener);
        HttpExecutor.execute(request, context);
    }

    public static void openCloseAlarm(Activity context, boolean closeAlarm, String deviceId, ResponseListener<BaseResponse> listener) {
        ObjectRequest<BaseResponse> request = new ObjectRequest<BaseResponse>(OPEN_CLOSE_ALARM, BaseResponse.class);
        request.addParam("closeAlarm", closeAlarm ? "true" : "false");
        request.addParam("deviceId", deviceId);
        request.setResponseListener(listener);
        HttpExecutor.execute(request, context);
    }

//    public static void closeAllAlarm(Activity context, boolean closeAllAlarm, ResponseListener<BaseResponse> listener) {
//        ObjectRequest<BaseResponse> request = new ObjectRequest<BaseResponse>(OPEN_CLOSE_ALARM, BaseResponse.class);
//        request.addParam("closeAlarm", closeAllAlarm ? "true" : "false");
//        request.setResponseListener(listener);
//        HttpExecutor.execute(request, context);
//    }



}

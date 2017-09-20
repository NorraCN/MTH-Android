package com.norra.cloud.api.response;

import com.norra.cloud.entity.DeviceType;

import java.util.List;

/**
 * Created by realted on 10/13/16.
 */

public class DeviceTypeResponse extends BaseResponse {
    private List<DeviceType> deviceTypes;

    public List<DeviceType> getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(List<DeviceType> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }
}

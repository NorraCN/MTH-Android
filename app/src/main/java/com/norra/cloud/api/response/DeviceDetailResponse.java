package com.norra.cloud.api.response;

import com.norra.cloud.entity.DataItem2;
import com.norra.cloud.entity.Device;

import java.util.List;

/**
 * Created by liurenhui on 15/1/22.
 * Modified by shenletao after 16/7/1
 */
public class DeviceDetailResponse extends BaseResponse {

    private Device device;

    private List<DataItem2> lastFrame;

    public List<DataItem2> getLastFrame() {
        return lastFrame;
    }

    public void setLastFrame(List<DataItem2> lastFrame) {
        this.lastFrame = lastFrame;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }


}

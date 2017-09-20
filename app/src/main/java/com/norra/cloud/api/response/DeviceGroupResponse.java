package com.norra.cloud.api.response;

import com.norra.cloud.entity.Device;
import com.norra.cloud.entity.DeviceItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charliu on 2016/1/18.
 */
public class DeviceGroupResponse extends BaseResponse {
    public ArrayList<DeviceItem> ungroup;
    public ArrayList<DeviceGroup> groups;
    public ArrayList<DeviceItem> shared;

    public class DeviceGroup {
        public ArrayList<DeviceItem> devices;
        public String groupId;
        public String name;
    }
}

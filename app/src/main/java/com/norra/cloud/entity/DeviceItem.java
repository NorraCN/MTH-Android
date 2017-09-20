package com.norra.cloud.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Charliu on 2016/1/19.
 * Modified by shenletao after 16/7/1
 */
public class DeviceItem implements Serializable {
    private Integer identifier;
    private Integer deviceId;
    private String name;
    private byte eAlert;
    private byte alert;
    private Float energy;
    private Long time;
    private List<DataItem> data;

    public List<DataItem> getData() {
        return data;
    }
    public void setData(List<DataItem> data) {
        this.data = data;
    }
    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public byte geteAlert() {
        return eAlert;
    }

    public void seteAlert(byte eAlert) {
        this.eAlert = eAlert;
    }

    public byte getAlert() {
        return alert;
    }

    public void setAlert(byte alert) {
        this.alert = alert;
    }

    public void setEnergy(Float energy) {
        this.energy = energy;
    }

    public Integer getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

}

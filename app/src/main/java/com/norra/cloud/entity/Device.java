package com.norra.cloud.entity;

import java.io.Serializable;

/**
 * Created by liurenhui on 15/1/22.
 */
public class Device implements Serializable {
    private Integer deviceId;
    private Integer identifier;
    private String name;
    private Float energy;
    private Float minEnergy;
    private Boolean alarmSwitch = true;
    private Long time;

    public Float getEnergy() {
        return energy;
    }

    public void setEnergy(Float energy) {
        this.energy = energy;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Float getMinEnergy() {
        return minEnergy;
    }

    public void setMinEnergy(Float minEnergy) {
        this.minEnergy = minEnergy;
    }

    public Boolean getAlarmSwitch() {
        return alarmSwitch;
    }

    public void setAlarmSwitch(Boolean alarmSwitch) {
        this.alarmSwitch = alarmSwitch;
    }
}
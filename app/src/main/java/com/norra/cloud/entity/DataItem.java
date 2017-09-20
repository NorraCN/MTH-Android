package com.norra.cloud.entity;

import java.io.Serializable;

/**
 * Created by liurenhui on 15/1/26.
 * Modified by shenletao after 16/7/1
 */
public class DataItem implements Serializable {
    protected String name;
    protected String unit;
    protected Float value;
    protected Short alert;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Short getAlert() {
        return alert;
    }

    public void setAlert(Short alert) {
        this.alert = alert;
    }
}

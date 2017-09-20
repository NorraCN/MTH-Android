package com.norra.cloud.entity;

/**
 * Created by realted on 17/01/2017.
 */

public class DataItem2 {
    protected String name;
    protected String unit;
    protected Float value;
    protected Short alert;
    protected Float max;
    protected Float min;

    public Float getMax() {
        return max;
    }

    public void setMax(Float max) {
        this.max = max;
    }

    public Float getMin() {
        return min;
    }

    public void setMin(Float min) {
        this.min = min;
    }

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

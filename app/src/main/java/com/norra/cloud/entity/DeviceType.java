package com.norra.cloud.entity;

import java.io.Serializable;

/**
 * Created by realted on 10/12/16.
 */

public class DeviceType implements Serializable {
    private Integer typeId;
    private String typeName;
    private String dataName1;
    private String unit1;
    private String dataName2;
    private String unit2;
    private String dataName3;
    private String unit3;
    private String dataName4;
    private String unit4;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDataName1() {
        return dataName1;
    }

    public void setDataName1(String dataName1) {
        this.dataName1 = dataName1;
    }

    public String getUnit1() {
        return unit1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public String getDataName2() {
        return dataName2;
    }

    public void setDataName2(String dataName2) {
        this.dataName2 = dataName2;
    }

    public String getUnit2() {
        return unit2;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2;
    }

    public String getDataName3() {
        return dataName3;
    }

    public void setDataName3(String dataName3) {
        this.dataName3 = dataName3;
    }

    public String getUnit3() {
        return unit3;
    }

    public void setUnit3(String unit3) {
        this.unit3 = unit3;
    }

    public String getDataName4() {
        return dataName4;
    }

    public void setDataName4(String dataName4) {
        this.dataName4 = dataName4;
    }

    public String getUnit4() {
        return unit4;
    }

    public void setUnit4(String unit4) {
        this.unit4 = unit4;
    }
}

package com.norra.cloud.api.response;

import com.norra.cloud.entity.DeviceItem;

import java.util.List;

/**
 * Created by shenletao on 15/1/22.
 */
public class DeviceListResponse extends BaseResponse {
    private int total;
    private int pageCount;
    private boolean hasPrior;
    private boolean hasNext;
    private List<DeviceItem> data;
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getPageCount() {
        return pageCount;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public boolean isHasPrior() {
        return hasPrior;
    }
    public void setHasPrior(boolean hasPrior) {
        this.hasPrior = hasPrior;
    }
    public boolean isHasNext() {
        return hasNext;
    }
    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
    public List<DeviceItem> getData() {
        return data;
    }
    public void setData(List<DeviceItem> data) {
        this.data = data;
    }

}
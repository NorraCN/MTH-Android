package com.norra.cloud.api.response;

import com.norra.cloud.entity.DataItem;

import java.util.List;

/**
 * Created by liurenhui on 15/1/22.
 */
public class DeviceDataResponse extends BaseResponse {

    private long total;
    private int pageCount;
    private int pageSize;
    private int pageNum;
    private boolean hasPrior;
    private boolean hasNext;

    private List<DynamicItem> data;

    public boolean isHasPrior() {
        return hasPrior;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public List<DynamicItem> getData() {
        return data;
    }

    public void setData(List<DynamicItem> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public boolean hasPrior() {
        return hasPrior;
    }

    public void setHasPrior(boolean hasPrior) {
        this.hasPrior = hasPrior;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public class DynamicItem extends DataItem {
        private boolean alarm;
        private String name;
        private String identifier;

        public boolean isAlarm() {
            return alarm;
        }

        public void setAlarm(boolean alarm) {
            this.alarm = alarm;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }
    }
}

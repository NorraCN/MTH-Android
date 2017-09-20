package com.norra.cloud.api.response;

import java.util.List;
import java.util.Map;

/**
 * Modified by shenletao on 2017-01-17.
 */
public class HistoryDataResponse extends BaseResponse {
    private int total;
    private int pageCount;
    private boolean hasPrior;
    private boolean hasNext;

    private List<Map<String, Object>> data;
    private List<String> title;
    private List<String> unit;

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

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getUnit() {
        return unit;
    }

    public void setUnit(List<String> unit) {
        this.unit = unit;
    }
}

package com.ctrip.framework.apollo.portal.spi.zts.portal.domain;

import java.util.List;

/**
 * @author roc
 * @since 2020/6/10 14:01
 */
public class ApiResponse<T> {

    private int total;
    private int count;
    private List<T> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}

package com.github.kongpf8848.extablayout.demo.base;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    protected int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}

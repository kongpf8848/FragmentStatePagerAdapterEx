package com.github.kongpf8848.extablayout.demo.bean;

import java.io.Serializable;

/**
 * 栏目信息
 */
public class Channel implements Serializable {

    private String channelId;
    private String channelName;
    private int channelType; //0:固定 1:可动态添加删除

    public Channel(String channelId, String channelName, int channelType) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelType=channelType;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

}

package com.github.kongpf8848.extablayout.demo.bean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.kongpf8848.extablayout.demo.base.BaseEntity;

import java.io.Serializable;

/**
 * 栏目信息
 */
public class Channel extends BaseEntity {

    private String channelId;
    private String channelName;
    private int channelType; //0:固定 1:可动态添加删除

    public Channel(String channelId, String channelName, int channelType) {
        this(channelId,channelName,channelType,0);
    }

    public Channel(String channelId, String channelName, int channelType,int viewType) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelType=channelType;
        this.viewType=viewType;
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

    @Override
    public String toString() {
        return "Channel{" +
                "channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", channelType=" + channelType +
                ", viewType=" + viewType +
                '}';
    }

    public boolean canDrag(){
        return channelType==1;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(! (obj instanceof Channel)){
            return false;
        }
        return this.channelId.equals(((Channel) obj).channelId);
    }
}

package com.github.kongpf8848.extablayout.demo.channel;

import com.github.kongpf8848.extablayout.demo.bean.Channel;

import java.util.List;

public interface IChannelManage {

    /**
     * 选中一个Channel
     * @param channel
     */
    void onSelectedChannel(Channel channel);


    /**
     * Channel添加,刪除,排序完成
     */
    void onFinish(List<Channel> selectedChannelList,List<Channel>unSelectedChannelList);
}

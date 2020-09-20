package com.github.kongpf8848.extablayout.demo.channel;

import com.github.kongpf8848.extablayout.demo.bean.Channel;

public interface IChannelManage {

    /**
     * 选中一个Channel
     * @param channel
     */
    void onSelectedChannel(Channel channel);


    void onDragChannelFinish();
}

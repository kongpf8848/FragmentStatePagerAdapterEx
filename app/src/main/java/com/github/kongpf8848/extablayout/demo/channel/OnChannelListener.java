package com.github.kongpf8848.extablayout.demo.channel;


import com.github.kongpf8848.extablayout.demo.bean.Channel;
import com.github.kongpf8848.extablayout.demo.touchhelper.OnItemTouchHelperListener;

public interface OnChannelListener extends OnItemTouchHelperListener {
    void onSelected(Channel channel);
}

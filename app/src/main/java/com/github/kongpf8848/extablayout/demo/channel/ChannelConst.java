package com.github.kongpf8848.extablayout.demo.channel;

import com.github.kongpf8848.extablayout.demo.bean.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelConst {

    public static final int TYPE_MY_CHANNEL = 0x00;
    public static final int TYPE_MORE_TITLE = 0x01;
    public static final int TYPE_MORE_CHANNEL = 0x02;
    public static final String KEY_CURRENT_CHANNEL = "current_channel";

    public static List<Channel> getDefaultChannleData(){
        List<Channel>list=new ArrayList<>();
        list.add(new Channel("01", "关注", 0, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("02", "推荐", 0, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("03", "热榜", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("04", "快讯", 1, ChannelConst.TYPE_MY_CHANNEL));

        list.add(new Channel("05", "Markets", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("06", "浙江", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("07", "直播", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("08", "举报", 1, ChannelConst.TYPE_MY_CHANNEL));

        list.add(new Channel("09", "视频", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("10", "汽车", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("11", "5G", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("12", "科技", 1, ChannelConst.TYPE_MY_CHANNEL));

        list.add(new Channel("13", "生活", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("14", "职场", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("15", "创投", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("16", "金融", 1, ChannelConst.TYPE_MY_CHANNEL));

        list.add(new Channel("17", "企服", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("18", "创新", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("19", "人物", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("20", "深度", 1, ChannelConst.TYPE_MY_CHANNEL));

        list.add(new Channel("21", "音频", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("22", "出海", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("23", "城市", 1, ChannelConst.TYPE_MY_CHANNEL));
        list.add(new Channel("24", "区块链", 1, ChannelConst.TYPE_MY_CHANNEL));

        list.add(new Channel("25", "会员", 1, ChannelConst.TYPE_MY_CHANNEL));

        return list;
    }
}

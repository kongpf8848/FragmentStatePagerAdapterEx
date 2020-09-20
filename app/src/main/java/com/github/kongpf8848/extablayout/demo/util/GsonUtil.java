package com.github.kongpf8848.extablayout.demo.util;

import com.github.kongpf8848.extablayout.demo.bean.Channel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GsonUtil {

    private static Gson gson=new Gson();

    public static List<Channel> toChannelList(String data){
        List<Channel>list=  gson.fromJson(data, new TypeToken<List<Channel>>() {}.getType());
        return list;
    }

}

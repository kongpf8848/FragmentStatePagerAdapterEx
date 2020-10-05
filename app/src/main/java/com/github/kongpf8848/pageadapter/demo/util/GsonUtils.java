package com.github.kongpf8848.pageadapter.demo.util;

import com.github.kongpf8848.pageadapter.demo.bean.Channel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GsonUtils {

    private static Gson gson=new Gson();

    public static List<Channel> toChannelList(String data){
        List<Channel>list=  gson.fromJson(data, new TypeToken<List<Channel>>() {}.getType());
        return list;
    }

    public static String fromChannelList(List<Channel>list){
        String str=gson.toJson(list);
        return str;
    }

}

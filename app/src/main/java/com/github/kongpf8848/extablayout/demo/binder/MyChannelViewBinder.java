package com.github.kongpf8848.extablayout.demo.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kongpf8848.extablayout.demo.R;
import com.github.kongpf8848.extablayout.demo.adapter.ChannelAdapter;
import com.github.kongpf8848.extablayout.demo.bean.Channel;

import me.drakeet.multitype.ItemViewBinder;

public class MyChannelViewBinder extends ItemViewBinder<Channel,MyChannelViewBinder.MyChannelViewHolder> {


    @NonNull
    @Override
    protected MyChannelViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new MyChannelViewHolder(inflater.inflate(R.layout.item_channel,parent,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull MyChannelViewHolder viewHolder, @NonNull Channel channel) {
    }

    class MyChannelViewHolder extends RecyclerView.ViewHolder{

        public MyChannelViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

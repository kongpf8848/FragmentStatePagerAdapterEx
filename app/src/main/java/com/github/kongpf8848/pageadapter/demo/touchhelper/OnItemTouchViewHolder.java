package com.github.kongpf8848.pageadapter.demo.touchhelper;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kongpf8848.pageadapter.demo.base.BaseRecyclerViewAdapter;
import com.github.kongpf8848.pageadapter.demo.bean.Channel;

public abstract class OnItemTouchViewHolder extends BaseRecyclerViewAdapter.BaseRecyclerViewHolder<Channel> {


    public OnItemTouchViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * 是否可拖拽
     * @return
     */
    public abstract boolean canDrag();


    /**
     * 选中的时候触发
     * @param viewHolder
     */
    public abstract void onItemSelected(RecyclerView.ViewHolder viewHolder);

    /**
     * 拖拽释放的时候触发
     * @param viewHolder
     */
    public abstract void onItemClear(RecyclerView.ViewHolder viewHolder);
}

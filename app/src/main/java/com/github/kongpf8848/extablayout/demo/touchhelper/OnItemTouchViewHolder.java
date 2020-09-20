package com.github.kongpf8848.extablayout.demo.touchhelper;


import androidx.recyclerview.widget.RecyclerView;

public interface OnItemTouchViewHolder {

    void onItemSelected(RecyclerView.ViewHolder viewHolder);
    void onItemClear(RecyclerView.ViewHolder viewHolder);
}

package com.github.kongpf8848.extablayout.demo.touchhelper;


import androidx.recyclerview.widget.RecyclerView;

public interface OnItemTouchViewHolder {
    /**
     * 是否可拖拽
     * @return
     */
    boolean canDrag();
    /**
     * 选中的时候触发
     * @param viewHolder
     */
    void onItemSelected(RecyclerView.ViewHolder viewHolder);

    /**
     * 拖拽释放的时候触发
     * @param viewHolder
     */
    void onItemClear(RecyclerView.ViewHolder viewHolder);
}

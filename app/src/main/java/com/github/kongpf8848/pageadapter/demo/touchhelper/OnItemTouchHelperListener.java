package com.github.kongpf8848.pageadapter.demo.touchhelper;

public interface OnItemTouchHelperListener {

    void onItemClick(int position);

    void onItemDragStart(int postion);

    void onItemMove(int starPosition, int endPosition);

    void onItemDismiss(int position);
}

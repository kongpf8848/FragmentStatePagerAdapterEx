package com.github.kongpf8848.extablayout.demo.touchhelper;

public interface OnItemTouchHelperListener {
    void onItemDragStart(int position);
    void onItemMove(int starPosition, int endPosition);
    void onItemDismiss(int position);
}

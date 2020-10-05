package com.github.kongpf8848.pageadapter.demo.touchhelper;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeItemHelperCallback extends BaseItemHelperCallback {

    public SwipeItemHelperCallback(OnItemTouchHelperListener listener) {
        super(listener);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder holder) {
        int dragFlag = 0;
        int swipeFlag = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            swipeFlag= ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder srcViewHolder, @NonNull RecyclerView.ViewHolder targetViewHolder) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder holder, int direction) {
        if(listener!=null){
            listener.onItemDismiss(holder.getAdapterPosition());
        }
    }

}

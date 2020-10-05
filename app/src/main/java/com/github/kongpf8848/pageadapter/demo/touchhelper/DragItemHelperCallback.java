package com.github.kongpf8848.pageadapter.demo.touchhelper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DragItemHelperCallback extends BaseItemHelperCallback {

    public DragItemHelperCallback(OnItemTouchHelperListener listener) {
        super(listener);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder holder) {
        Log.d("JACK8", "getMovementFlags() called with: recyclerView = [" + recyclerView + "], holder = [" + holder + "]");
        int dragFlag = 0;
        int swipeFlag = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        } else if (layoutManager instanceof LinearLayoutManager) {
            dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder srcViewHolder, @NonNull RecyclerView.ViewHolder targetViewHolder) {
        Log.d("JACK8", "onMove() called with: recyclerView = [" + recyclerView + "], srcViewHolder = [" + srcViewHolder + "], targetViewHolder = [" + targetViewHolder + "]");
        if (srcViewHolder.getItemViewType() != targetViewHolder.getItemViewType()) {
            return false;
        }
        if(! (srcViewHolder instanceof OnItemTouchViewHolder) || !(targetViewHolder instanceof OnItemTouchViewHolder)){
            return false;
        }
        if(!((OnItemTouchViewHolder)srcViewHolder).canDrag() || !((OnItemTouchViewHolder)targetViewHolder).canDrag()){
            return false;
        }
        if (listener != null) {
            listener.onItemMove(srcViewHolder.getAdapterPosition(), targetViewHolder.getAdapterPosition());
            return true;
        }
        return false;
    }



    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder holder, int position) {

    }



}

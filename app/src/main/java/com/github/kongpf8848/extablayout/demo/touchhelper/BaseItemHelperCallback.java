package com.github.kongpf8848.extablayout.demo.touchhelper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseItemHelperCallback extends ItemTouchHelper.Callback {

    protected OnItemTouchHelperListener listener;
    public BaseItemHelperCallback(OnItemTouchHelperListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        Log.d("JACK8", "onSelectedChanged() called with: viewHolder = [" + viewHolder + "], actionState = [" + actionState + "]");
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof OnItemTouchViewHolder) {
                OnItemTouchViewHolder itemViewHolder = (OnItemTouchViewHolder) viewHolder;
                itemViewHolder.onItemSelected(viewHolder);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        Log.d("JACK8", "clearView() called with: recyclerView = [" + recyclerView + "], viewHolder = [" + viewHolder + "]");
        if (viewHolder instanceof OnItemTouchViewHolder) {
            OnItemTouchViewHolder itemViewHolder = (OnItemTouchViewHolder) viewHolder;
            itemViewHolder.onItemClear(viewHolder);
        }
        super.clearView(recyclerView, viewHolder);
    }



}

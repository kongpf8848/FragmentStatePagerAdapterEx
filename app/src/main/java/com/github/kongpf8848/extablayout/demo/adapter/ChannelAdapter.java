package com.github.kongpf8848.extablayout.demo.adapter;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kongpf8848.extablayout.demo.R;
import com.github.kongpf8848.extablayout.demo.base.BaseRecyclerViewAdapter;
import com.github.kongpf8848.extablayout.demo.bean.Channel;
import com.github.kongpf8848.extablayout.demo.channel.ChannelConst;
import com.github.kongpf8848.extablayout.demo.touchhelper.DragItemHelperCallback;
import com.github.kongpf8848.extablayout.demo.touchhelper.OnItemTouchHelperListener;
import com.github.kongpf8848.extablayout.demo.touchhelper.OnItemTouchViewHolder;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChannelAdapter extends BaseRecyclerViewAdapter<Channel> {

    private Channel currentChannel;
    private boolean editMode = false;
    private OnItemTouchHelperListener onItemTouchHelperListener;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView mRecyclerView;


    public ChannelAdapter(Context context, List<Channel> list) {
        super(context, list);
    }

    public void setOnItemTouchHelperListener(OnItemTouchHelperListener onItemTouchHelperListener) {
        this.onItemTouchHelperListener = onItemTouchHelperListener;
        DragItemHelperCallback callback = new DragItemHelperCallback(onItemTouchHelperListener);
        this.mItemTouchHelper = new ItemTouchHelper(callback);
    }

    public void setCurrentChannel(Channel channel) {
        this.currentChannel = channel;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
        if (this.mItemTouchHelper != null) {
            mItemTouchHelper.attachToRecyclerView(recyclerView);
        }
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void toogleEditMode() {
        startEditMode(!this.editMode);
    }

    public void startEditMode(boolean isEdit) {
        this.editMode = isEdit;

        int childCount = mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mRecyclerView.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(view);
            if (viewHolder instanceof MyChannelViewHolder) {
                int position = mRecyclerView.getChildAdapterPosition(view);
                Channel channel = getItem(position);
                ((MyChannelViewHolder) viewHolder).bindView(position, channel);
            }
        }
    }

    @Override
    public int getItemViewType(int position, Channel channel) {
        return channel.getViewType();
    }

    @Override
    public void initViewType() {
        addViewType(ChannelConst.TYPE_MY_CHANNEL, R.layout.holder_item_menu, MyChannelViewHolder.class);
        addViewType(ChannelConst.TYPE_MORE_TITLE, R.layout.holder_title_menu, MoreTitleViewHolder.class);
        addViewType(ChannelConst.TYPE_MORE_CHANNEL, R.layout.holder_item_menu, MoreChannelViewHolder.class);
    }

    /**
     * 我的频道
     */
    public class MyChannelViewHolder extends OnItemTouchViewHolder {

        @BindView(R.id.tv_channel_name)
        TextView tv_channel_name;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;

        private Channel channel;


        public MyChannelViewHolder(View view) {
            super(view);
        }

        @Override
        public void bindView(int position, Channel channel) {
            this.channel = channel;
            tv_channel_name.setText(channel.getChannelName());

            if (!canDrag()) {
                tv_channel_name.setBackgroundResource(R.drawable.shape_bg_channel_name_no_drag);
            } else {
                tv_channel_name.setBackgroundResource(R.drawable.shape_bg_channel_name_can_drag);
            }
            tv_channel_name.setTextColor(channel == currentChannel ? itemView.getResources().getColor(R.color.app_blue) : itemView.getResources().getColor(R.color.text_color_primary));


            if (editMode && canDrag()) {
                iv_delete.setVisibility(View.VISIBLE);
            } else {
                iv_delete.setVisibility(View.GONE);
            }

            if (canDrag()) {
                itemView.setOnLongClickListener(v -> {
                    if (!isEditMode()) {
                        if (onItemTouchHelperListener != null) {
                            onItemTouchHelperListener.onItemDragStart(position);
                        }
                    }
                    mItemTouchHelper.startDrag(MyChannelViewHolder.this);
                    return true;
                });
            }
            itemView.setOnClickListener(v -> {
                if (isEditMode()) {
                    if (!channel.canDrag()) {
                        return;
                    }
                    int currentPosition = getAdapterPosition();
                    int moreFirstPosition = getMoreFirstPosition();
                    channel.setViewType(ChannelConst.TYPE_MORE_CHANNEL);//改为更多频道类型
                    if (moreFirstPosition == RecyclerView.NO_POSITION) {
                        add(ChannelConst.getMoreChannelBean());
                        moreFirstPosition = getItemCount();
                    }
                    if (onItemTouchHelperListener != null) {
                        onItemTouchHelperListener.onItemMove(currentPosition, moreFirstPosition - 1);
                    }
                } else {
                    if (onItemTouchHelperListener != null) {
                        onItemTouchHelperListener.onItemClick(position);
                    }
                }
            });

        }

        @Override
        public boolean canDrag() {
            return channel != null && channel.canDrag();
        }

        @Override
        public void onItemSelected(RecyclerView.ViewHolder viewHolder) {
            Log.d("JACK8", "onItemSelected,position:" + viewHolder.getAdapterPosition());
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(100);
            }
            //tv_channel_name.setBackgroundColor(Color.RED);
            //tv_channel_name.animate().scaleXBy(0.2f).scaleYBy(0.2f).setDuration(200).start();
        }

        @Override
        public void onItemClear(RecyclerView.ViewHolder viewHolder) {
            Log.d("JACK8", "onItemClear,position:" + viewHolder.getAdapterPosition());
            //tv_channel_name.setBackgroundColor(Color.parseColor("#f3f5f4"));
            // tv_channel_name.animate().scaleXBy(-0.2f).scaleYBy(-0.2f).setDuration(200).start();
        }
    }

    /**
     * 更多频道标题
     */
    public static class MoreTitleViewHolder extends BaseRecyclerViewHolder<Channel> {

        public MoreTitleViewHolder(View view) {
            super(view);
        }

        @Override
        public void bindView(int position, Channel channel) {
        }
    }

    /**
     * 更多频道
     */
    public class MoreChannelViewHolder extends BaseRecyclerViewHolder<Channel> {

        @BindView(R.id.tv_channel_name)
        TextView tv_channel_name;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;

        public MoreChannelViewHolder(View view) {
            super(view);
        }

        @Override
        public void bindView(int position, Channel channel) {
            tv_channel_name.setText(String.format("+%s", channel.getChannelName()));
            iv_delete.setVisibility(View.GONE);
        }

        @OnClick(R.id.tv_channel_name)
        public void onClickChannel() {
            int currentPosition = getAdapterPosition();
            int myLastPosition = getMyLastPosition();
            Channel channel = getItem(currentPosition);
            channel.setViewType(ChannelConst.TYPE_MY_CHANNEL);//改为我的频道类型

            if (onItemTouchHelperListener != null) {
                onItemTouchHelperListener.onItemMove(currentPosition, myLastPosition + 1);
            }
            if (getItemCount(ChannelConst.TYPE_MORE_CHANNEL) == 0) {
                int index = getMoreTitlePosition();
                if (index != RecyclerView.NO_POSITION) {
                    remove(index);
                }
            }

        }

    }

    /**
     * 获取我的频道最后一个位置
     *
     * @return
     */
    private int getMyLastPosition() {
        for (int i = getItemCount() - 1; i >= 0; i--) {
            if (getItemViewType(i) == ChannelConst.TYPE_MY_CHANNEL) {
                return i;
            }
        }
        return RecyclerView.NO_POSITION;
    }

    /**
     * 获取更多频道Title位置
     *
     * @return
     */
    private int getMoreTitlePosition() {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemViewType(i) == ChannelConst.TYPE_MORE_TITLE) {
                return i;
            }
        }
        return RecyclerView.NO_POSITION;
    }

    /**
     * 获取更多频道第一个位置
     *
     * @return
     */
    private int getMoreFirstPosition() {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemViewType(i) == ChannelConst.TYPE_MORE_CHANNEL) {
                return i;
            }
        }
        return RecyclerView.NO_POSITION;
    }


}

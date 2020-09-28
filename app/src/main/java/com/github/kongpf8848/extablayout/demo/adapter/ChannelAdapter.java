package com.github.kongpf8848.extablayout.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kongpf8848.extablayout.demo.R;
import com.github.kongpf8848.extablayout.demo.base.BaseEntity;
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



    private boolean editMode = false;
    private OnItemTouchHelperListener onItemTouchHelperListener;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView mRecyclerView;


    public ChannelAdapter(Context context, List<Channel> list) {
        super(context, list);
    }

    public void setOnItemTouchHelperListener(OnItemTouchHelperListener onItemTouchHelperListener){
        this.onItemTouchHelperListener = onItemTouchHelperListener;
        DragItemHelperCallback callback = new DragItemHelperCallback(onItemTouchHelperListener);
        this.mItemTouchHelper = new ItemTouchHelper(callback);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
        if(this.mItemTouchHelper!=null) {
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
            RecyclerView.ViewHolder viewHolder=mRecyclerView.getChildViewHolder(view);
            if(viewHolder instanceof MyChannelViewHolder){
                int position=mRecyclerView.getChildAdapterPosition(view);
                Channel channel=getItem(position);
                ((MyChannelViewHolder)viewHolder).bindView(position,channel);
            }
        }
    }

    @Override
    public int getItemViewType(int position, Channel channel) {
        return channel.getViewType();
    }

    @Override
    public void initViewType() {
        addViewType(ChannelConst.TYPE_MY_CHANNEL, R.layout.item_channel, MyChannelViewHolder.class);
        addViewType(ChannelConst.TYPE_MORE_TITLE, R.layout.item_more_title, RecommendTitleViewHolder.class);
        addViewType(ChannelConst.TYPE_MORE_CHANNEL, R.layout.item_channel, RecommendChannelViewHolder.class);
    }

    public class MyChannelViewHolder extends OnItemTouchViewHolder  {

        @BindView(R.id.rl_channel)
        RelativeLayout rl_channel;
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
            this.channel=channel;
            tv_channel_name.setText(channel.getChannelName());

            if (!canDrag()) {
                tv_channel_name.setBackgroundResource(R.drawable.shape_bg_channel_name_no_drag);
            } else {
                tv_channel_name.setBackgroundResource(R.drawable.shape_bg_channel_name_can_drag);
            }

            if (editMode && canDrag()) {
                iv_delete.setVisibility(View.VISIBLE);
            } else {
                iv_delete.setVisibility(View.GONE);
            }

            if (canDrag()) {
                itemView.setOnLongClickListener(v -> {
                    if (!isEditMode()) {
                        if(onItemTouchHelperListener!=null){
                            onItemTouchHelperListener.onItemDragStart(position);
                        }
                    }
                    mItemTouchHelper.startDrag(MyChannelViewHolder.this);
                    return true;
                });
            }
            rl_channel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEditMode()) {
                        if (channel.getChannelType() == 0) return;
                        int currentPosition = getAdapterPosition();
                        int recommendFirstPosition = getRecommendFirstPosition();
                        View currentView = mRecyclerView.getLayoutManager().findViewByPosition(currentPosition);
                        View targetView = mRecyclerView.getLayoutManager().findViewByPosition(recommendFirstPosition);
                        if (mRecyclerView.indexOfChild(targetView) >= 0 && recommendFirstPosition != RecyclerView.NO_POSITION) {
                            RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
                            int spanCount = ((GridLayoutManager) manager).getSpanCount();
                            int targetX = targetView.getLeft();
                            int targetY = targetView.getTop();
                            int myChannelSize = getItemCount(ChannelConst.TYPE_MY_CHANNEL);
                            if (myChannelSize % spanCount == 1) {
                                targetY -= targetView.getHeight();
                            }
                            channel.setViewType(ChannelConst.TYPE_MORE_CHANNEL);//改为推荐频道类型

                            if (onItemTouchHelperListener != null) {
                                onItemTouchHelperListener.onItemMove(currentPosition, recommendFirstPosition - 1);
                            }
                            startAnimation(currentView, targetX, targetY);
                        } else {
                            channel.setViewType(ChannelConst.TYPE_MORE_CHANNEL);
                            if (recommendFirstPosition == RecyclerView.NO_POSITION) {
                                recommendFirstPosition = getItemCount();
                            }
                            if (onItemTouchHelperListener != null) {
                                onItemTouchHelperListener.onItemMove(currentPosition, recommendFirstPosition - 1);
                            }
                        }
                    } else {
                        if (onItemTouchHelperListener != null) {
                            //onItemTouchHelperListener.onSelected(channel);
                        }
                    }
                }
            });

        }

        @Override
        public boolean canDrag() {
            return channel!=null && channel.canDrag();
        }

        @Override
        public void onItemSelected(RecyclerView.ViewHolder viewHolder) {
            Log.d("JACK8", "onItemSelected,position:" + viewHolder.getAdapterPosition());
            tv_channel_name.setBackgroundColor(Color.RED);
            tv_channel_name.animate().scaleXBy(0.2f).scaleYBy(0.2f).setDuration(200).start();
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(100);
            }
        }

        @Override
        public void onItemClear(RecyclerView.ViewHolder viewHolder) {
            Log.d("JACK8", "onItemFinish,position");
            tv_channel_name.setBackgroundColor(Color.parseColor("#f3f5f4"));
            tv_channel_name.animate().scaleXBy(-0.2f).scaleYBy(-0.2f).setDuration(200).start();
        }
    }



    public static class RecommendTitleViewHolder extends BaseRecyclerViewHolder<Channel> {

        public RecommendTitleViewHolder(View view) {
            super(view);
        }

        @Override
        public void bindView(int position, Channel channel) {
        }
    }


    public class RecommendChannelViewHolder extends BaseRecyclerViewHolder<Channel> {

        @BindView(R.id.rl_channel)
        RelativeLayout rl_channel;
        @BindView(R.id.tv_channel_name)
        TextView tv_channel_name;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;

        public RecommendChannelViewHolder(View view) {
            super(view);
        }

        @Override
        public void bindView(int position, Channel channel) {
            tv_channel_name.setText(channel.getChannelName());
            iv_delete.setTag(null);
            iv_delete.setVisibility(View.GONE);
        }

        @OnClick(R.id.tv_channel_name)
        public void onChannel() {
            int currentPosition = getAdapterPosition();
            Channel channel = getItem(currentPosition);

            int myLastPosition = getMyLastPosition();
            View currentView = mRecyclerView.getLayoutManager().findViewByPosition(currentPosition);
            View targetView = mRecyclerView.getLayoutManager().findViewByPosition(myLastPosition);
            if (mRecyclerView.indexOfChild(targetView) >= 0 && myLastPosition != RecyclerView.NO_POSITION) {
                RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
                int spanCount = ((GridLayoutManager) manager).getSpanCount();
                int targetX = targetView.getLeft() + targetView.getWidth();
                int targetY = targetView.getTop();

                int myChannelSize = getItemCount(ChannelConst.TYPE_MY_CHANNEL);
                if (myChannelSize % spanCount == 0) {
                    View lastFourthView = mRecyclerView.getLayoutManager().findViewByPosition(getMyLastPosition() - 3);
                    targetX = lastFourthView.getLeft();
                    targetY = lastFourthView.getTop() + lastFourthView.getHeight();
                }
                channel.setViewType(ChannelConst.TYPE_MY_CHANNEL);//改为推荐频道类型
                if (onItemTouchHelperListener != null) {
                    onItemTouchHelperListener.onItemMove(currentPosition, myLastPosition + 1);
                }
                startAnimation(currentView, targetX, targetY);
            } else {
                channel.setViewType(ChannelConst.TYPE_MY_CHANNEL);//改为推荐频道类型
                if (myLastPosition == RecyclerView.NO_POSITION) {
                    myLastPosition = 0;
                }
                if (onItemTouchHelperListener != null) {
                    onItemTouchHelperListener.onItemMove(currentPosition, myLastPosition + 1);
                }
            }
        }

    }


    private int getMyLastPosition() {
        for (int i = getItemCount() - 1; i >= 0; i--) {
            if (getItemViewType(i) == ChannelConst.TYPE_MY_CHANNEL) {
                return i;
            }
        }
        return RecyclerView.NO_POSITION;
    }

    private int getRecommendFirstPosition() {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemViewType(i) == ChannelConst.TYPE_MORE_CHANNEL) {
                return i;
            }
        }
        return RecyclerView.NO_POSITION;
    }


    private void startAnimation(final View currentView, int targetX, int targetY) {
        final ViewGroup parent = (ViewGroup) mRecyclerView.getParent();
        final ImageView mirrorView = addMirrorView(parent, currentView);
        TranslateAnimation animator = getTranslateAnimator(targetX - currentView.getLeft(), targetY - currentView.getTop());
        currentView.setVisibility(View.INVISIBLE);//暂时隐藏
        mirrorView.startAnimation(animator);
        animator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                parent.removeView(mirrorView);//删除添加的镜像View
                if (currentView.getVisibility() == View.INVISIBLE) {
                    currentView.setVisibility(View.VISIBLE);//显示隐藏的View
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 添加需要移动的 镜像View
     */
    private ImageView addMirrorView(ViewGroup parent, View view) {
        view.destroyDrawingCache();
        //首先开启Cache图片 ，然后调用view.getDrawingCache()就可以获取Cache图片
        view.setDrawingCacheEnabled(true);
        ImageView mirrorView = new ImageView(view.getContext());
        //获取该view的Cache图片
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        mirrorView.setImageBitmap(bitmap);
        //销毁掉cache图片
        view.setDrawingCacheEnabled(false);
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);//获取当前View的坐标
        int[] parenLocations = new int[2];
        mRecyclerView.getLocationOnScreen(parenLocations);//获取RecyclerView所在坐标
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        params.setMargins(locations[0], locations[1] - parenLocations[1], 0, 0);
        parent.addView(mirrorView, params);//在RecyclerView的Parent添加我们的镜像View，parent要是FrameLayout这样才可以放到那个坐标点
        return mirrorView;
    }

    private TranslateAnimation getTranslateAnimator(float targetX, float targetY) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetX,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetY);
        // RecyclerView默认移动动画250ms 这里设置360ms 是为了防止在位移动画结束后 remove(view)过早 导致闪烁
        translateAnimation.setDuration(360);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }
}

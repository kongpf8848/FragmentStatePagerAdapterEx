package com.github.kongpf8848.extablayout.demo.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kongpf8848.extablayout.demo.CommonPreferenceManager;
import com.github.kongpf8848.extablayout.demo.channel.IChannelManage;
import com.github.kongpf8848.extablayout.demo.channel.OnChannelListener;
import com.github.kongpf8848.extablayout.demo.R;
import com.github.kongpf8848.extablayout.demo.adapter.ChannelAdapter;
import com.github.kongpf8848.extablayout.demo.bean.Channel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChannelDialogFragment extends DialogFragment implements OnChannelListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<Channel> selectedChannelList = new ArrayList<>();
    private List<Channel> unselectedChannelList = new ArrayList<>();
    private List<Channel> allData = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;
    private ChannelAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setWindowAnimations(R.style.DialogAnim);
        }
        return inflater.inflate(R.layout.dialog_channel, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        selectedChannelList=( List<Channel>)getArguments().getSerializable(CommonPreferenceManager.SELECTED_CHANNEL_DATA);
        unselectedChannelList=( List<Channel>)getArguments().getSerializable(CommonPreferenceManager.UNSELECTED_CHANNEL_DATA);

        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType=adapter.getItemViewType(position);
                if(viewType== ChannelAdapter.TYPE_MY_TITLE || viewType== ChannelAdapter.TYPE_RECOMMEND_TITLE ){
                    return 4;
                }
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        allData.clear();
        allData.add(new Channel("","",0, ChannelAdapter.TYPE_MY_TITLE));
        if(selectedChannelList!=null && selectedChannelList.size()>0){
            for(Channel channel:selectedChannelList){
                allData.add(new Channel(channel.getChannelId(),channel.getChannelName(),channel.getChannelType(),ChannelAdapter.TYPE_MY_CHANNEL));
            }
        }
        allData.add(new Channel("","", 0,ChannelAdapter.TYPE_RECOMMEND_TITLE));
        if(unselectedChannelList!=null && unselectedChannelList.size()>0){

            for(Channel channel:unselectedChannelList){
                allData.add(new Channel(channel.getChannelId(),channel.getChannelName(),channel.getChannelType(),ChannelAdapter.TYPE_MY_CHANNEL));
            }
        }
        adapter = new ChannelAdapter(getActivity(), allData,this);
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.iv_close)
    public void onClose() {
        dismiss();
    }


    @Override
    public void onItemMove(int starPosition, int endPosition) {
        if (starPosition < 0 || endPosition < 0) return;
        if (adapter.getItem(endPosition).getChannelName().equals("头条")) {
            return;
        }
        Channel channel = allData.get(starPosition);
        allData.remove(starPosition);
        allData.add(endPosition, channel);
        adapter.notifyItemMoved(starPosition, endPosition);
    }

    @Override
    public void onItemDismiss(int position) {

    }

    @Override
    public void onSelected(Channel channel) {
        Log.d("JACK8","onFinish:"+channel.getChannelName());
        if(getActivity() instanceof IChannelManage){
            ((IChannelManage)getActivity()).onSelectedChannel(channel);
        }
        dismiss();
    }
}




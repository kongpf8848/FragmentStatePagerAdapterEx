package com.github.kongpf8848.extablayout.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.kongpf8848.extablayout.demo.R;
import com.github.kongpf8848.extablayout.demo.base.BaseFragment;
import com.github.kongpf8848.extablayout.demo.bean.Channel;


public class ChannelFragment extends BaseFragment {

    private Channel channel;

    @Override
    protected String getLogTag() {
        if(channel!=null){
            return "ChannelFragment:"+channel.getChannelName();
        }
        else{
            return super.getLogTag();
        }

    }

    public static ChannelFragment newInstance(Channel channel) {
        ChannelFragment fragment = new ChannelFragment();
        Bundle args = new Bundle();
        args.putSerializable("channel", channel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            channel = (Channel)getArguments().getSerializable("channel");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel, container, false);
        TextView tv = view.findViewById(R.id.tv_title);
        tv.setText(channel.getChannelName());
        return view;
    }

}

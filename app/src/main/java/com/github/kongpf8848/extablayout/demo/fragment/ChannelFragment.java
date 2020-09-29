package com.github.kongpf8848.extablayout.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.kongpf8848.extablayout.demo.R;
import com.github.kongpf8848.extablayout.demo.base.BaseFragment;
import com.github.kongpf8848.extablayout.demo.base.BaseLazyFragment;
import com.github.kongpf8848.extablayout.demo.bean.Channel;
import com.github.kongpf8848.extablayout.demo.util.DateUtils;


public class ChannelFragment extends BaseLazyFragment {

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
    public int getLayoutId() {
        return R.layout.fragment_channel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            channel = (Channel)getArguments().getSerializable("channel");
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        TextView tv = findViewById(R.id.tv_title);
        tv.setText(channel.getChannelName()+"-"+ DateUtils.getCurrentDateTime());
    }
}

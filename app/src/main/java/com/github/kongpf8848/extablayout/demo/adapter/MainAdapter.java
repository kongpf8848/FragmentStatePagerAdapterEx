package com.github.kongpf8848.extablayout.demo.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.github.kongpf8848.extablayout.demo.base.FragmentAdapter;
import com.github.kongpf8848.extablayout.demo.bean.Channel;
import com.github.kongpf8848.extablayout.demo.fragment.ChannelFragment;

import java.util.List;

public class MainAdapter extends FragmentPagerAdapter {

    private List<Channel> list;

    public MainAdapter(FragmentManager fm, List<Channel> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return ChannelFragment.newInstance(this.list.get(position));
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.list.get(position).getChannelName();
    }

    public Channel getItemData(int position){
        return list.get(position);
    }


}

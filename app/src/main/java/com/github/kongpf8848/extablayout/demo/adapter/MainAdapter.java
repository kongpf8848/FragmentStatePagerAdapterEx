package com.github.kongpf8848.extablayout.demo.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.kongpf8848.extablayout.demo.bean.Channel;
import com.github.kongpf8848.extablayout.demo.fragment.ChannelFragment;
import com.github.kongpf8848.pageadapter.FragmentStatePagerAdapterEx;

import java.util.List;

public class MainAdapter extends FragmentStatePagerAdapterEx<Channel> {

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
        if(list!=null) {
            return this.list.size();
        }
        else{
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position>=0 && position<list.size()){
            return list.get(position).getChannelName();
        }
        else{
            return null;
        }
    }

    @Override
    public Channel getItemData(int position){
        if(position>=0 && position<list.size()){
            return list.get(position);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean dataEquals(Channel oldData, Channel newData) {
        if(oldData==null){
            return newData==null;
        }
        else {
            return oldData.equals(newData);
        }
    }

    @Override
    public int getDataPosition(Channel data) {
        if(list!=null) {
            return list.indexOf(data);
        }
        else{
            return 0;
        }
    }


}

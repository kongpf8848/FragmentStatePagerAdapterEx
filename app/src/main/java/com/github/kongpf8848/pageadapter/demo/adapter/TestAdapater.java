package com.github.kongpf8848.pageadapter.demo.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.kongpf8848.pageadapter.FragmentStatePagerAdapterEx;
import com.github.kongpf8848.pageadapter.demo.bean.Channel;

public class TestAdapater extends FragmentStatePagerAdapterEx<Channel> {

    public TestAdapater(FragmentManager fm) {
        super(fm);
    }

    /**
     * 获取指定位置的Fragment
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    /**
     * 获取Fragment数量
     * @return
     */
    @Override
    public int getCount() {
        return 0;
    }

    /**
     * 获取指定位置对应的数据
     * @param position
     * @return
     */
    @Override
    public Channel getItemData(int position) {
        return null;
    }

    /**
     * 判断新旧数据是否相等
     * @param oldData
     * @param newData
     * @return
     */
    @Override
    public boolean dataEquals(Channel oldData, Channel newData) {
        return false;
    }

    /**
     * 获取指定数据对应的位置
     * @param data
     * @return
     */
    @Override
    public int getDataPosition(Channel data) {
        return 0;
    }
}

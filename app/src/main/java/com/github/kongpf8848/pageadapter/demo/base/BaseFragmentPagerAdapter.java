package com.github.kongpf8848.pageadapter.demo.base;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by pengf on 2016/12/15.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mTitles.get(position);
    }
}

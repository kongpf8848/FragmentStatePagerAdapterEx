package com.github.kongpf8848.extablayout.demo.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import com.github.kongpf8848.extablayout.ExTabLayout;
import com.github.kongpf8848.extablayout.demo.CommonPreferenceManager;
import com.github.kongpf8848.extablayout.demo.R;
import com.github.kongpf8848.extablayout.demo.adapter.MainAdapter;
import com.github.kongpf8848.extablayout.demo.base.BaseActivity;
import com.github.kongpf8848.extablayout.demo.bean.Channel;
import com.github.kongpf8848.extablayout.demo.channel.ChannelConst;
import com.github.kongpf8848.extablayout.demo.channel.IChannelManage;
import com.github.kongpf8848.extablayout.demo.fragment.ChannelDialogFragment;
import com.github.kongpf8848.extablayout.demo.util.GsonUtils;
import com.gyf.immersionbar.ImmersionBar;

import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.Menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IChannelManage {

    private static final String TAG = "MainActivity";

    @BindView(R.id.tab_layout)
    ExTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<Channel> selectedChannelList = new ArrayList<>();
    private List<Channel> unSelectedChannelList = new ArrayList<>();
    private MainAdapter mainAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ImmersionBar.with(this).statusBarColor(R.color.white).statusBarDarkFont(true).titleBar(R.id.toolbar).init();
    }

    @Override
    protected void initData() {
        super.initData();

        initChannelData();
        initViewPager();
    }

    /**
     * 初始化频道数据
     */
    private void initChannelData() {
        String selectedChannelData= CommonPreferenceManager.getInstance(this).getSelectedChannelData();
        String unselectedChannelData= CommonPreferenceManager.getInstance(this).getUnSelectedChannelData();
        if (TextUtils.isEmpty(selectedChannelData)) {
            selectedChannelList= ChannelConst.getDefaultChannleData();
        }
        else{
            selectedChannelList= GsonUtils.toChannelList(selectedChannelData);
        }

        if (TextUtils.isEmpty(unselectedChannelData)) {
            unSelectedChannelList.clear();
        }
        else{
            unSelectedChannelList= GsonUtils.toChannelList(unselectedChannelData);
        }

    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        tabLayout.setSlidingIndicatorAnimType(ExTabLayout.AnimType.HALF_GLUE);
        tabLayout.setClickIndicatorAnimType(ExTabLayout.AnimType.NONE);
        mainAdapter=new MainAdapter(getSupportFragmentManager(),selectedChannelList);
        viewPager.setAdapter(mainAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 频道管理
     */
    @OnClick({R.id.menu_channel,R.id.iv_nav_menu})
    public void onClickChannelMenu(){
        ChannelDialogFragment fragment=new ChannelDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(CommonPreferenceManager.SELECTED_CHANNEL_DATA, (Serializable) selectedChannelList);
        bundle.putSerializable(CommonPreferenceManager.UNSELECTED_CHANNEL_DATA, (Serializable) unSelectedChannelList);
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(),ChannelDialogFragment.class.getSimpleName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public void onSelectedChannel(Channel channel) {
        int oldIndex= viewPager.getCurrentItem();
        int newIndex=-1;
        for (int i = 0; i <mainAdapter.getCount() ; i++) {
            if(mainAdapter.getItemData(i).getChannelId().equals(channel.getChannelId())){
                newIndex=i;
                break;
            }
        }
        if(newIndex>=0 && oldIndex!=newIndex){
            viewPager.setCurrentItem(newIndex);
        }
    }

    @Override
    public void onFinish(List<Channel> selected, List<Channel> unSelected) {
        this.selectedChannelList.clear();
        this.selectedChannelList.addAll(selected);
        this.unSelectedChannelList=unSelected;
        String selectedData="";
        String unSelectedData="";
        if(this.selectedChannelList!=null && this.selectedChannelList.size()>0){
            selectedData= GsonUtils.fromChannelList(selectedChannelList);
        }
        CommonPreferenceManager.getInstance(this).setSelectedChannelData(selectedData);
        if(this.unSelectedChannelList!=null && this.unSelectedChannelList.size()>0){
            unSelectedData= GsonUtils.fromChannelList(unSelectedChannelList);
        }
        CommonPreferenceManager.getInstance(this).setUnSelectedChannelData(unSelectedData);
        mainAdapter.notifyDataSetChanged();
    }

}

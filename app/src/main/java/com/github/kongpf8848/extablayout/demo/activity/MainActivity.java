package com.github.kongpf8848.extablayout.demo.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.kongpf8848.extablayout.ExTabLayout;
import com.github.kongpf8848.extablayout.demo.CommonPreferenceManager;
import com.github.kongpf8848.extablayout.demo.R;
import com.github.kongpf8848.extablayout.demo.adapter.MainAdapter;
import com.github.kongpf8848.extablayout.demo.base.BaseActivity;
import com.github.kongpf8848.extablayout.demo.bean.Channel;
import com.github.kongpf8848.extablayout.demo.channel.ChannelConst;
import com.github.kongpf8848.extablayout.demo.channel.IChannelManage;
import com.github.kongpf8848.extablayout.demo.fragment.ChannelDialogFragment;
import com.github.kongpf8848.extablayout.demo.fragment.ChannelFragment;
import com.github.kongpf8848.extablayout.demo.util.GsonUtil;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, IChannelManage {

    private static final String TAG = "MainActivity";

    private ExTabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView iv_nav_menu;

    private List<Channel> selectedChannelList = new ArrayList<>();
    private List<Channel> unSelectedChannelList = new ArrayList<>();
    private MainAdapter mainAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initData() {
        super.initData();
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.glue_tab_layout);
        iv_nav_menu=findViewById(R.id.iv_nav_menu);
        iv_nav_menu.setOnClickListener(this);

        initChannelData();
        initViewPager();
    }

    private void initChannelData() {
        String selectedChannelData= CommonPreferenceManager.getInstance(this).getSelectedChannelData();
        String unselectedChannelData= CommonPreferenceManager.getInstance(this).getUnSelectedChannelData();

        if (TextUtils.isEmpty(selectedChannelData)) {
            selectedChannelList= ChannelConst.getDefaultChannleData();
        }
        else{
            selectedChannelList= GsonUtil.toChannelList(selectedChannelData);
        }

        if (TextUtils.isEmpty(unselectedChannelData)) {
            unSelectedChannelList.clear();
        }
        else{
            unSelectedChannelList= GsonUtil.toChannelList(unselectedChannelData);
        }


    }

    private void initViewPager() {

        mainAdapter=new MainAdapter(getSupportFragmentManager(),selectedChannelList);
        mViewPager.setAdapter(mainAdapter);

        mTabLayout.setTabMode(ExTabLayout.MODE_SCROLLABLE);
        mTabLayout.setSelectedTabIndicatorHeight(dp2px(2));
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#239cfa"));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(dp2px(2));
        mTabLayout.setSelectedTabIndicator(gradientDrawable);
        mTabLayout.setTabIndicatorFullWidth(false);
        mTabLayout.setUnboundedRipple(false);
        mTabLayout.setTabRippleColor(ColorStateList.valueOf(Color.TRANSPARENT));
        mTabLayout.setSlidingIndicatorAnimType(ExTabLayout.AnimType.HALF_GLUE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }


    private int dp2px(float dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.iv_nav_menu){
            ChannelDialogFragment fragment=new ChannelDialogFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable(CommonPreferenceManager.SELECTED_CHANNEL_DATA, (Serializable) selectedChannelList);
            bundle.putSerializable(CommonPreferenceManager.UNSELECTED_CHANNEL_DATA, (Serializable) unSelectedChannelList);
            fragment.setArguments(bundle);
            fragment.show(getSupportFragmentManager(),ChannelDialogFragment.class.getSimpleName());
        }
    }


    @Override
    public void onSelectedChannel(Channel channel) {
        int oldIndex=mViewPager.getCurrentItem();
        int newIndex=-1;
        for (int i = 0; i <mainAdapter.getCount() ; i++) {
            if(mainAdapter.getItemData(i).getChannelId().equals(channel.getChannelId())){
                newIndex=i;
                break;
            }
        }
        if(newIndex>=0 && oldIndex!=newIndex){
            mViewPager.setCurrentItem(newIndex);
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
            selectedData=GsonUtil.fromChannelList(selectedChannelList);
        }
        CommonPreferenceManager.getInstance(this).setSelectedChannelData(selectedData);
        if(this.unSelectedChannelList!=null && this.unSelectedChannelList.size()>0){
            unSelectedData=GsonUtil.fromChannelList(unSelectedChannelList);
        }
        CommonPreferenceManager.getInstance(this).setUnSelectedChannelData(unSelectedData);
        mainAdapter.notifyDataSetChanged();
       // initViewPager();
    }

}

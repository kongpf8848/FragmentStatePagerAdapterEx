package com.github.kongpf8848.extablayout.demo.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.kongpf8848.extablayout.ExTabLayout;
import com.github.kongpf8848.extablayout.demo.R;
import com.github.kongpf8848.extablayout.demo.base.BaseActivity;
import com.github.kongpf8848.extablayout.demo.bean.Channel;
import com.github.kongpf8848.extablayout.demo.fragment.ChannelDialogFragment;
import com.github.kongpf8848.extablayout.demo.fragment.ChannelFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private ExTabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView iv_nav_menu;

    private List<Channel> channelList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initData() {
        super.initData();
        initChannelData();
        initViewPager();
    }

    private void initChannelData() {
        channelList.clear();
        channelList.add(new Channel("01", "关注", 0));
        channelList.add(new Channel("02", "推荐", 0));
        channelList.add(new Channel("03", "热榜", 1));
        channelList.add(new Channel("04", "快讯", 1));

        channelList.add(new Channel("05", "Markets", 1));
        channelList.add(new Channel("06", "浙江", 1));
        channelList.add(new Channel("07", "直播", 1));
        channelList.add(new Channel("08", "举报", 1));

        channelList.add(new Channel("09", "视频", 1));
        channelList.add(new Channel("10", "汽车", 1));
        channelList.add(new Channel("11", "5G", 1));
        channelList.add(new Channel("12", "科技", 1));

        channelList.add(new Channel("13", "生活", 1));
        channelList.add(new Channel("14", "职场", 1));
        channelList.add(new Channel("15", "创投", 1));
        channelList.add(new Channel("16", "金融", 1));

        channelList.add(new Channel("17", "企服", 1));
        channelList.add(new Channel("18", "创新", 1));
        channelList.add(new Channel("19", "人物", 1));
        channelList.add(new Channel("20", "深度", 1));

        channelList.add(new Channel("21", "音频", 1));
        channelList.add(new Channel("22", "出海", 1));
        channelList.add(new Channel("23", "城市", 1));
        channelList.add(new Channel("24", "区块链", 1));

        channelList.add(new Channel("25", "会员", 1));

    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.glue_tab_layout);
        iv_nav_menu=findViewById(R.id.iv_nav_menu);
        iv_nav_menu.setOnClickListener(this);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ChannelFragment.newInstance(channelList.get(position).getChannelName());
            }

            @Override
            public int getCount() {
                return channelList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return channelList.get(position).getChannelName();
            }
        });

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
            bundle.putSerializable("channel", (Serializable) channelList);
            fragment.setArguments(bundle);
            fragment.show(getSupportFragmentManager(),ChannelDialogFragment.class.getSimpleName());
        }
    }
}

package com.github.kongpf8848.extablayout.demo;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.github.kongpf8848.extablayout.ExTabLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;
    private ExTabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] channels = {
            "关注","推荐","科技","直播","5G","区块链",
            "举报","热榜","创投","出海","会员",
            "快讯","Markets","生活","职场","企服",
            "深度","城市","视频","浙江","创新",
            "人物","音频","金融","汽车"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return TempFragment.newInstance(channels[position]);
            }

            @Override
            public int getCount() {
                return channels.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return channels[position];
            }
        });

        mTabLayout = findViewById(R.id.glue_tab_layout);
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

}

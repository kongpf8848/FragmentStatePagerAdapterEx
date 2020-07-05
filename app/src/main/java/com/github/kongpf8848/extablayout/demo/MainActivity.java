package com.github.kongpf8848.extablayout.demo;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.style.ScaleXSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.github.kongpf8848.extablayout.ExTabLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;
    private ExTabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] weibo = {"精选", "推荐", "榜单", "故事", "综艺", "剧集", "VLOG", "明星", "电影"};

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
                return TempFragment.newInstance(weibo[position]);
            }

            @Override
            public int getCount() {
                return weibo.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return weibo[position];
            }
        });

        mTabLayout = (ExTabLayout) findViewById(R.id.glue_tab_layout);
        mTabLayout.setTabMode(ExTabLayout.MODE_SCROLLABLE);
        mTabLayout.setSelectedTabIndicatorHeight(dp2px(4));
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF6F00"));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(dp2px(2));
        mTabLayout.setSelectedTabIndicator(gradientDrawable);
        mTabLayout.setTabIndicatorFullWidth(false);
        mTabLayout.setUnboundedRipple(false);
        mTabLayout.setTabRippleColor(ColorStateList.valueOf(Color.TRANSPARENT));

        mTabLayout.addOnTabSelectedListener(new ExTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(ExTabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected() called with: tab = [" + tab + "]");
                //tab.view.getTextView().setTypeface(Typeface.DEFAULT_BOLD);
               // tab.view.getTextView().setScaleX(1.2f);
               // tab.view.getTextView().setScaleY(1.2f);
            }

            @Override
            public void onTabUnselected(ExTabLayout.Tab tab) {
                //tab.view.getTextView().setTypeface(Typeface.DEFAULT);
               // tab.view.getTextView().setScaleX(1f);
               // tab.view.getTextView().setScaleY(1f);
            }

            @Override
            public void onTabReselected(ExTabLayout.Tab tab) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.menu_slide_glue:
                mTabLayout.setSlidingIndicatorAnimType(ExTabLayout.AnimType.GLUE);
                break;
            case R.id.menu_slide_half_glue:
                mTabLayout.setSlidingIndicatorAnimType(ExTabLayout.AnimType.HALF_GLUE);
                break;
            case R.id.menu_slide_none:
                mTabLayout.setSlidingIndicatorAnimType(ExTabLayout.AnimType.NONE);
                break;
            case R.id.menu_click_glue:
                mTabLayout.setClickIndicatorAnimType(ExTabLayout.AnimType.GLUE);
                break;
            case R.id.menu_click_half_glue:
                mTabLayout.setClickIndicatorAnimType(ExTabLayout.AnimType.HALF_GLUE);
                break;
            case R.id.menu_click_none:
                mTabLayout.setClickIndicatorAnimType(ExTabLayout.AnimType.NONE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int dp2px(float dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

}

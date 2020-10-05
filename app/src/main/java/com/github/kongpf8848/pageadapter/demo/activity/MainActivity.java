package com.github.kongpf8848.pageadapter.demo.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.github.kongpf8848.extablayout.ExTabLayout;
import com.github.kongpf8848.pageadapter.demo.AppPreferencesManager;
import com.github.kongpf8848.pageadapter.demo.R;
import com.github.kongpf8848.pageadapter.demo.adapter.MainAdapter;
import com.github.kongpf8848.pageadapter.demo.base.BaseActivity;
import com.github.kongpf8848.pageadapter.demo.bean.Channel;
import com.github.kongpf8848.pageadapter.demo.channel.ChannelConst;
import com.github.kongpf8848.pageadapter.demo.channel.IChannelManage;
import com.github.kongpf8848.pageadapter.demo.fragment.ChannelDialogFragment;
import com.github.kongpf8848.pageadapter.demo.util.GsonUtils;
import com.gyf.immersionbar.ImmersionBar;

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
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimaryDark)
                .statusBarDarkFont(false)
                .supportActionBar(true)
                .init();
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
        String selectedChannelData = AppPreferencesManager.getInstance(this).getSelectedChannelData();
        String unselectedChannelData = AppPreferencesManager.getInstance(this).getUnSelectedChannelData();
        if (TextUtils.isEmpty(selectedChannelData)) {
            selectedChannelList = ChannelConst.getDefaultChannleData();
        } else {
            selectedChannelList = GsonUtils.toChannelList(selectedChannelData);
        }

        if (TextUtils.isEmpty(unselectedChannelData)) {
            unSelectedChannelList.clear();
        } else {
            unSelectedChannelList = GsonUtils.toChannelList(unselectedChannelData);
        }

    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        tabLayout.setSlidingIndicatorAnimType(ExTabLayout.AnimType.HALF_GLUE);
        tabLayout.setClickIndicatorAnimType(ExTabLayout.AnimType.NONE);
        mainAdapter = new MainAdapter(getSupportFragmentManager(), selectedChannelList);
        viewPager.setAdapter(mainAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    /**
     * 频道管理
     */
    @OnClick({R.id.menu_channel, R.id.iv_nav_menu})
    public void onClickMenu() {
        ChannelDialogFragment fragment = new ChannelDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppPreferencesManager.SELECTED_CHANNEL_DATA, (Serializable) selectedChannelList);
        bundle.putSerializable(AppPreferencesManager.UNSELECTED_CHANNEL_DATA, (Serializable) unSelectedChannelList);
        bundle.putSerializable(ChannelConst.KEY_CURRENT_CHANNEL, mainAdapter.getItemData(viewPager.getCurrentItem()));
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(), ChannelDialogFragment.class.getSimpleName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_about){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("关于");
            builder.setMessage(R.string.about_text);
            builder.setPositiveButton("确定", null);
            builder.setCancelable(true);
            AlertDialog dialog=builder.create();
            dialog.show();
            ((TextView)dialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelectedChannel(Channel channel) {
        int oldIndex = viewPager.getCurrentItem();
        int newIndex = -1;
        for (int i = 0; i < mainAdapter.getCount(); i++) {
            if (mainAdapter.dataEquals(mainAdapter.getItemData(i), channel)) {
                newIndex = i;
                break;
            }
        }
        if (newIndex >= 0 && oldIndex != newIndex) {
            viewPager.setCurrentItem(newIndex);
        }
    }

    @Override
    public void onFinish(List<Channel> selected, List<Channel> unSelected) {
        this.selectedChannelList.clear();
        this.selectedChannelList.addAll(selected);
        this.unSelectedChannelList = unSelected;
        String selectedData = "";
        String unSelectedData = "";
        if (this.selectedChannelList != null && this.selectedChannelList.size() > 0) {
            selectedData = GsonUtils.fromChannelList(selectedChannelList);
        }
        AppPreferencesManager.getInstance(this).setSelectedChannelData(selectedData);
        if (this.unSelectedChannelList != null && this.unSelectedChannelList.size() > 0) {
            unSelectedData = GsonUtils.fromChannelList(unSelectedChannelList);
        }
        AppPreferencesManager.getInstance(this).setUnSelectedChannelData(unSelectedData);
        mainAdapter.notifyDataSetChanged();
    }

}

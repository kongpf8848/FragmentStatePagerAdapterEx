

package com.github.kongpf8848.pageadapter.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 配置文件管理器
 */

public class AppPreferencesManager {
    private static SharedPreferences sPreferences;
    private static AppPreferencesManager sQDPreferenceManager = null;

    public static final String SELECTED_CHANNEL_DATA = "selected_channel_data";
    public static final String UNSELECTED_CHANNEL_DATA = "unselected_channel_data";

    private AppPreferencesManager(Context context) {
        sPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public static AppPreferencesManager getInstance(Context context) {

        if (sQDPreferenceManager == null) {
            synchronized (AppPreferencesManager.class){
                if(sQDPreferenceManager==null){
                    sQDPreferenceManager = new AppPreferencesManager(context);
                }
            }
        }
        return sQDPreferenceManager;
    }


    public void setSelectedChannelData(String data) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString(SELECTED_CHANNEL_DATA, data);
        editor.apply();
    }

    public String getSelectedChannelData() {
        return sPreferences.getString(SELECTED_CHANNEL_DATA, "");
    }

    public void setUnSelectedChannelData(String data) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString(UNSELECTED_CHANNEL_DATA, data);
        editor.apply();
    }

    public String getUnSelectedChannelData() {
        return sPreferences.getString(UNSELECTED_CHANNEL_DATA, "");
    }

}

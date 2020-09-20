/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.kongpf8848.extablayout.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class CommonPreferenceManager {
    private static SharedPreferences sPreferences;
    private static CommonPreferenceManager sQDPreferenceManager = null;

    public static final String SELECTED_CHANNEL_DATA = "selected_channel_data";
    public static final String UNSELECTED_CHANNEL_DATA = "unselected_channel_data";

    private CommonPreferenceManager(Context context) {
        sPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public static final CommonPreferenceManager getInstance(Context context) {
        if (sQDPreferenceManager == null) {
            sQDPreferenceManager = new CommonPreferenceManager(context);
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

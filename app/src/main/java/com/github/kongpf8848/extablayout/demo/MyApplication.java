package com.github.kongpf8848.extablayout.demo;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            Log.e("Crash","error:"+e.getMessage());

        });
    }
}

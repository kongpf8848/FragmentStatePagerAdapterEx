package com.github.kongpf8848.pageadapter.demo;

import android.app.Application;
import android.util.Log;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            Log.e("Crash","error:"+e.getMessage());
        });
    }
}

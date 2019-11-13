package com.abyte.wanandroid;

import android.app.Application;
import android.content.Context;

import com.abyte.wanandroid.base.DataManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by geyan on 2018
 */
public class SmartApp extends Application {

    private boolean mDebug = false;
    private static SmartApp smartApp;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        smartApp = this;
        setDebug(true);
        // 可以直接检测Activity
        refWatcher = LeakCanary.install(this);
        DataManager.getInstance().init();
    }

    // Fragment的检测需要使用该方法
    public static RefWatcher getRetWatcher(Context context) {
        SmartApp app = (SmartApp) context.getApplicationContext();
        return app.refWatcher;
    }

    public static SmartApp getInstance() {
        return smartApp;
    }

    public boolean isDebug() {
        return mDebug;
    }

    public void setDebug(boolean debug) {
        mDebug = debug;
    }
}

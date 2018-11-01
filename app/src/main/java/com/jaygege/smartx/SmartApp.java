package com.jaygege.smartx;

import android.app.Application;
import android.util.Log;

/**
 * Created by geyan on 2018
 */
public class SmartApp extends Application {

    private boolean mDebug = false;
    private static SmartApp smartApp;

    @Override
    public void onCreate() {
        super.onCreate();
        smartApp = this;
        setDebug(true);
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

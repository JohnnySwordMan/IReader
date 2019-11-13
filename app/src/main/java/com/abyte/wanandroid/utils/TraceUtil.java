package com.abyte.wanandroid.utils;

import androidx.core.os.TraceCompat;
import android.util.Log;

import com.abyte.wanandroid.SmartApp;

import java.util.ArrayDeque;

/**
 * 性能监测，在需要进行监测的代码前后，添加beginSection和endSection，可利用SysTrace工具可导出耗时数据
 * Created by geyan on 2018
 */
public class TraceUtil {

    private static final long WARN_TIME_MS = 100;
    private static ThreadLocal<ArrayDeque<Long>> mTimeThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<ArrayDeque<String>> mNameThreadLocal = new ThreadLocal<>();

    public static void beginSection(String sectionName) {
        // debug模式下，进行性能检测
        if (SmartApp.getInstance().isDebug()) {
            ArrayDeque<Long> mTimeDeque = mTimeThreadLocal.get();
            if (mTimeDeque == null) {
                mTimeDeque = new ArrayDeque<>();
                mTimeThreadLocal.set(mTimeDeque);
            }

            ArrayDeque<String> mNameDeque = mNameThreadLocal.get();
            if (mNameDeque == null) {
                mNameDeque = new ArrayDeque<>();
                mNameThreadLocal.set(mNameDeque);
            }

            sectionName = "Trace: " + sectionName;
            // 利用TraceCompat对具体活动进行追踪
            TraceCompat.beginSection(sectionName);
            mTimeDeque.push(System.currentTimeMillis());
            mNameDeque.push(sectionName);
        }
    }

    public static void endSection() {
        if (SmartApp.getInstance().isDebug()) {
            ArrayDeque<Long> mTimeDeque = mTimeThreadLocal.get();
            ArrayDeque<String> mNamDeque = mNameThreadLocal.get();
            TraceCompat.endSection();
            long time = System.currentTimeMillis() - mTimeDeque.pop();
            if (time > WARN_TIME_MS) {
                if (!mNamDeque.isEmpty()) {
                    Log.w(mNamDeque.pop(), "spend " + time + " millis");
                }
            } else {
                if (!mNamDeque.isEmpty()) {
                    Log.d(mNamDeque.pop(), "spend " + time + " millis");
                }
            }
        }
    }
}

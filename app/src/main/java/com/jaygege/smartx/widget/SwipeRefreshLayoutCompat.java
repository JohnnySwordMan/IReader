package com.jaygege.smartx.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 解决SwipeRefreshLayout与ViewPager的滑动冲突问题
 * https://github.com/JohnnySwordMan/Android-issues/blob/master/%E6%BB%91%E5%8A%A8%E5%86%B2%E7%AA%81.mdown
 * Created by geyan on 2018/10/3
 */
public class SwipeRefreshLayoutCompat extends SwipeRefreshLayout {

    private int mTouchSlop;
    // 是否是左右滑动事件
    private boolean isLeftToRight;
    private float mStartX, mStartY;

    public SwipeRefreshLayoutCompat(@NonNull Context context) {
        this(context, null);
    }

    public SwipeRefreshLayoutCompat(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                mStartX = ev.getX();
                mStartY = ev.getY();
                // 默认是上下滑动的
                isLeftToRight = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果是左右滑动事件，则返回false；表示SwipeRefreshLayout不拦截事件
                if (isLeftToRight) {
                    return false;
                }
                // 获取当前手指位置
                float currentX = ev.getX();
                float currentY = ev.getY();
                // 计算手指滑动计算
                float distanceX = Math.abs(currentX - mStartX);
                float distanceY = Math.abs(currentY - mStartY);
                // 如果在X轴方向上的移动距离大于Y轴的最小距离，同时在X轴方向上的移动距离大于在Y轴方向上的移动距离
                if (distanceX > mTouchSlop && distanceX > distanceY) {
                    isLeftToRight = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isLeftToRight = false;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}

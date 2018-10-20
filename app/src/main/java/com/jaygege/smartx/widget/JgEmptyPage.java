package com.jaygege.smartx.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaygege.smartx.R;


/**
 * 空白页
 * Created by geyan on 2018/10/12
 */
public class JgEmptyPage extends FrameLayout implements View.OnClickListener {

    private FrameLayout contentLayout;
    private ImageView mIvNoneIcon;
    private TextView mTvNoneTips;
    private Button mBtnNoneRetry;
    private LinearLayout nonePageLayout;
    private OnClickListener mOnEmptyClickListener;

    public JgEmptyPage(@NonNull Context context) {

        this(context, null);
    }

    public JgEmptyPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JgEmptyPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPage();
    }

    private void initPage() {
        LayoutInflater.from(getContext()).inflate(R.layout.jg_widget_empty_page, this, true);
        contentLayout = findViewById(R.id.jy_widget_empty_page_content);
        nonePageLayout = findViewById(R.id.ll_none_page);
        mIvNoneIcon = findViewById(R.id.empty_page_none_iv_icon);
        mTvNoneTips = findViewById(R.id.empty_page_none_tv_tips);
        mBtnNoneRetry = findViewById(R.id.empty_page_none_btn_retry);
        mBtnNoneRetry.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mOnEmptyClickListener != null) {
            mOnEmptyClickListener.onClick(view);
        }
    }

    public void setOnEmptyClickListener(OnClickListener onEmptyClickListener) {
        this.mOnEmptyClickListener = onEmptyClickListener;
    }

    /**
     * 将我们绘制的页面设置到网络错误页面的下面
     */
    public void setContentView(View view) {
        contentLayout.addView(view);
    }

    public void show() {
        if (nonePageLayout.getVisibility() == GONE) {
            nonePageLayout.setVisibility(VISIBLE);
        }
    }

    public void dismiss() {
        if (nonePageLayout.getVisibility() == VISIBLE) {
            nonePageLayout.setVisibility(GONE);
        }
    }
}

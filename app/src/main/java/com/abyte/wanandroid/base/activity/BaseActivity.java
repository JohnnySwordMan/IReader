package com.abyte.wanandroid.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.abyte.wanandroid.R;
import com.abyte.wanandroid.base.presenter.AbstractPresenter;
import com.abyte.wanandroid.base.view.AbstractView;
import com.abyte.wanandroid.widget.JgEmptyPage;

/**
 * Created by Jaygege on 2018
 */
public abstract class BaseActivity<T extends AbstractPresenter> extends AbstractSimpleActivity implements AbstractView {

    private T mPresenter;
    private boolean isNeedDefaultTitle;
    private LinearLayout mRootContentView;
    private JgEmptyPage jgEmptyPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initClickListener() {

    }

    @Override
    protected void findViews() {
        // decorView
        ViewGroup rootView = (ViewGroup) mContentView.getParent();
        rootView.removeView(mContentView);

        // 创建LinearLayout布局，专门作为title和content的父布局
        mRootContentView = new LinearLayout(this);
        mRootContentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mRootContentView.setOrientation(LinearLayout.VERTICAL);
        /**
         * 是否需要默认标题
         */
        if (isNeedDefaultTitle) {
            View titleLayout = LayoutInflater.from(this).inflate(R.layout.base_toolbar_v7, mRootContentView, false);
            mRootContentView.addView(titleLayout);
        }
        mRootContentView.addView(mContentView);
        rootView.addView(mRootContentView);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected abstract T createPresenter();

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    protected void setEmptyPage() {
        if (jgEmptyPage != null) {
            return;
        }
        mRootContentView.removeView(mContentView);
        jgEmptyPage = new JgEmptyPage(this);
        // 将正常的布局设置到网路错误的布局之下，即网络错误页面覆盖在网络正常时显示的页面之上
        jgEmptyPage.setContentView(mContentView);
        mRootContentView.addView(jgEmptyPage, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        jgEmptyPage.setOnEmptyClickListener(v -> onEmptyClickRetry(v));
    }

    protected void showEmptyPage() {
        setEmptyPage();
        jgEmptyPage.show();
    }

    protected void disEmptyPage() {
        if (jgEmptyPage != null) {
            jgEmptyPage.dismiss();
        }
    }


    protected void onEmptyClickRetry(View view) {

    }

    protected void isNeedDefaultTitle(boolean isNeedTitle) {
        this.isNeedDefaultTitle = isNeedTitle;
    }
}

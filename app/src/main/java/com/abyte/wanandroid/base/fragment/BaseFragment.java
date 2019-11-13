package com.abyte.wanandroid.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abyte.wanandroid.base.presenter.AbstractPresenter;
import com.abyte.wanandroid.base.view.AbstractView;
import com.abyte.wanandroid.widget.JgEmptyPage;

public abstract class BaseFragment<T extends AbstractPresenter> extends AbstractSimpleFragment implements AbstractView {


    protected T mPresenter;
    private LinearLayout mRootContentView;
    private boolean isNeedDefaultTitle;
    private JgEmptyPage jgEmptyPage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);
        jgEmptyPage = new JgEmptyPage(getActivity());
        jgEmptyPage.setContentView(contentView);
        jgEmptyPage.dismiss();
        jgEmptyPage.setOnEmptyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmptyClickRetry(v);
            }
        });
        return jgEmptyPage;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initData();
    }

    /**
     * 专门作为请求网络数据，不要将网络请求放到onResume方法中
     * 如果在onResume中请求数据，以HomeFragment为例，每当HomeFragment从后台回到前台，又会去重新请求数据，因此可能会造成数据的重复；
     * 如果硬是想要在onResume中请求数据，可以定义isFirstInit布尔值，只有第一次执行onResume方法的时候才会去执行网络请求。
     */
    protected void initData() {

    }


    /**
     * 这边是在onDestroy中写，还是在onDestroyView ？
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected abstract T createPresenter();


    @Override
    protected void findViews(View view) {

//        View mContentView = getView();
//        ViewGroup rootView = (ViewGroup) mContentView.getParent();
//        rootView.removeView(mContentView);
//
//        mRootContentView = new LinearLayout(getActivity());
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        mRootContentView.setLayoutParams(params);
//        mRootContentView.setOrientation(LinearLayout.VERTICAL);
//        if (isNeedDefaultTitle) {
//            View titleLayout = LayoutInflater.from(getActivity()).inflate(R.layout.base_toolbar_v7, mRootContentView, false);
//            mRootContentView.addView(titleLayout);
//        }
//        mRootContentView.addView(mContentView);
//        rootView.addView(mRootContentView);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

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
//        if (jgEmptyPage != null) {
//            return;
//        }
//        mRootContentView.removeView(mContentView);
//        jgEmptyPage = new JgEmptyPage(getActivity());
//        jgEmptyPage.setContentView(mContentView);
//        mRootContentView.addView(jgEmptyPage, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        jgEmptyPage.setOnEmptyClickListener(v -> onEmptyClickRetry(v));
    }

    public void showEmptyPage() {
//        setEmptyPage();
        // 在onCreateView中就已经设置好了
        jgEmptyPage.show();
    }

    public void disEmptyPage() {
        if (jgEmptyPage != null) {
            jgEmptyPage.dismiss();
        }
    }

    public void onEmptyClickRetry(View view) {

    }


    public void isNeedDefaultTitle(boolean isNeedTitle) {
        this.isNeedDefaultTitle = isNeedTitle;
    }
}

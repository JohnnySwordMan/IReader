package com.jaygege.smartx.base.presenter;

import com.jaygege.smartx.base.view.AbstractView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * BasePresenter
 * Created by geyan on 2018
 */
public abstract class BasePresenter<T extends AbstractView> implements AbstractPresenter<T> {

    protected Reference<T> mViewRef;
    protected T mView;

    public BasePresenter() {
    }

    @Override
    public void attachView(T view) {
        mViewRef = new WeakReference<>(view);
        // 这样写是有问题的，子类不应该直接使用mView，而是通过getView()来获取mView变量
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    @Override
    public T getView() {
        return mViewRef.get();
    }

    @Override
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

}

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

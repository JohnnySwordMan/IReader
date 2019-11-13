package com.abyte.wanandroid.base.presenter;

import com.abyte.wanandroid.base.view.AbstractView;

/**
 * created by geyan on 2018
 */
public interface AbstractPresenter<T extends AbstractView> {

    // presenter与view的绑定
    void attachView(T view);

    // presenter与view的解绑
    void detachView();

    // 获取view
    T getView();

    // 判断presenter是否与view绑定着
    boolean isViewAttached();
}

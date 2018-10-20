package com.jaygege.smartx.network;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by geyan on 2018/9/21
 */
public abstract class AbstractNetRxHttp {

    protected Subscription subscription = Subscriptions.empty();

    public void execute(Subscriber subscriber) {
        subscription = createObservable().subscribe(subscriber);
    }

    protected abstract Observable createObservable();

}

package com.abyte.wanandroid.presenter.navigation;

import com.abyte.wanandroid.base.DataManager;
import com.abyte.wanandroid.base.presenter.BasePresenter;
import com.abyte.wanandroid.contract.navigation.NavigationPageContract;
import com.abyte.wanandroid.core.bean.navigation.NavigationListEntity;
import com.abyte.wanandroid.utils.RxJavaUtils;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by geyan on 2018/9/27
 */
public class NavigationPagePresenter extends BasePresenter<NavigationPageContract.View> implements NavigationPageContract.Presenter {


    private final DataManager dataManager;

    public NavigationPagePresenter() {
        // 创建访问网络用例
        dataManager = DataManager.getInstance();
    }

    @Override
    public void loadNavigationData() {


        Disposable subcriber = dataManager.getHttpService().getNavigationList()
                .compose(RxJavaUtils.applySchedulers())
                .compose(RxJavaUtils.handleResult())
                .subscribe(new Consumer<List<NavigationListEntity>>() {
                    @Override
                    public void accept(List<NavigationListEntity> navigationListEntities) throws Exception {
                        getView().showNavigationData(navigationListEntities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().onFailure();
                    }
                });

        addSubscriber(subcriber);

//        mGetNavigationDataFromNet.execute(new Subscriber<List<NavigationListEntity>>() {
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                getView().onFailure();
//            }
//
//            @Override
//            public void onNext(List<NavigationListEntity> navigationListEntities) {
//                getView().showNavigationData(navigationListEntities);
//            }
//        });
    }
}

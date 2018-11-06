package com.jaygege.smartx.presenter.navigation;

import com.jaygege.smartx.base.presenter.BasePresenter;
import com.jaygege.smartx.contract.navigation.NavigationPageContract;
import com.jaygege.smartx.core.bean.navigation.NavigationListEntity;
import com.jaygege.smartx.core.httpUseCase.navigation.GetNavigationDataFromNet;

import java.util.List;

import rx.Subscriber;

/**
 * Created by geyan on 2018/9/27
 */
public class NavigationPagePresenter extends BasePresenter<NavigationPageContract.View> implements NavigationPageContract.Presenter {


    private final GetNavigationDataFromNet mGetNavigationDataFromNet;

    public NavigationPagePresenter() {
        // 创建访问网络用例
        mGetNavigationDataFromNet = new GetNavigationDataFromNet();
    }

    @Override
    public void loadNavigationData() {
        mGetNavigationDataFromNet.execute(new Subscriber<List<NavigationListEntity>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().onFailure();
            }

            @Override
            public void onNext(List<NavigationListEntity> navigationListEntities) {
                getView().showNavigationData(navigationListEntities);
            }
        });
    }
}

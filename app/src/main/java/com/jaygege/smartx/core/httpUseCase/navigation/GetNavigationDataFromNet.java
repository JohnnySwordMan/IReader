package com.jaygege.smartx.core.httpUseCase.navigation;

import com.jaygege.smartx.base.response.BaseResponseFunc;
import com.jaygege.smartx.core.bean.navigation.NavigationListEntity;
import com.jaygege.smartx.network.RetrofitUtils;
import com.jaygege.smartx.network.SimpleNetRxHttp;

import java.util.List;

import rx.Observable;

/**
 * Created by geyan on 2018/9/27
 */
public class GetNavigationDataFromNet extends SimpleNetRxHttp {

    @Override
    protected Observable<List<NavigationListEntity>> create() {
        Observable<List<NavigationListEntity>> entityList = RetrofitUtils.getHttpService().getNavigationList().map(new BaseResponseFunc<>());
        return entityList;
    }
}

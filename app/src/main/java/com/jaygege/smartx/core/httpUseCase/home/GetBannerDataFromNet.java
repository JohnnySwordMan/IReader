package com.jaygege.smartx.core.httpUseCase.home;

import com.jaygege.smartx.base.response.BaseResponse;
import com.jaygege.smartx.base.response.BaseResponseFunc;
import com.jaygege.smartx.core.bean.home.banner.BannerEntity;
import com.jaygege.smartx.network.RetrofitUtils;
import com.jaygege.smartx.network.SimpleNetRxHttp;


import java.util.List;

import rx.Observable;

/**
 * Created by geyan on 2018/9/22
 */
public class GetBannerDataFromNet extends SimpleNetRxHttp {

    public GetBannerDataFromNet() {
    }


    @Override
    protected Observable<List<BannerEntity>> create() {
        Observable<BaseResponse<List<BannerEntity>>> bannerData = RetrofitUtils.getHttpService().getBannerData();
        Observable<List<BannerEntity>> bannerEntityList = bannerData.map(new BaseResponseFunc<>());
        return bannerEntityList;
    }
}

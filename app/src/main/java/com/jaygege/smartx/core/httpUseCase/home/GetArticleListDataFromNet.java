package com.jaygege.smartx.core.httpUseCase.home;

import com.jaygege.smartx.base.response.BaseResponse;
import com.jaygege.smartx.base.response.BaseResponseFunc;
import com.jaygege.smartx.core.bean.home.collect.FeedArticleListEntity;
import com.jaygege.smartx.network.RetrofitUtils;
import com.jaygege.smartx.network.SimpleNetRxHttp;

import rx.Observable;

/**
 * Created by geyan on 2018/9/23
 */
public class GetArticleListDataFromNet extends SimpleNetRxHttp {

    private int pageNum;

    public GetArticleListDataFromNet() {
    }

    public void setRequest(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    protected Observable<FeedArticleListEntity> create() {
        Observable<BaseResponse<FeedArticleListEntity>> feedArticleList =
                RetrofitUtils.getHttpService().getFeedArticleList(pageNum);
        Observable<FeedArticleListEntity> feedArticleListEntity = feedArticleList.map(new BaseResponseFunc<>());
        return feedArticleListEntity;
    }
}

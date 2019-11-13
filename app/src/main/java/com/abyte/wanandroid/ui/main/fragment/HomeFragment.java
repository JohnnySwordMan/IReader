package com.abyte.wanandroid.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.abyte.wanandroid.R;
import com.abyte.wanandroid.base.fragment.BaseFragment;
import com.abyte.wanandroid.base.listener.LoadMoreOnScrollListener;
import com.abyte.wanandroid.contract.main.HomePageContract;
import com.abyte.wanandroid.core.bean.home.HomeItem;
import com.abyte.wanandroid.core.bean.home.banner.BannerEntity;
import com.abyte.wanandroid.core.bean.home.banner.BannerListEntity;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleEntity;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleListEntity;
import com.abyte.wanandroid.presenter.main.HomePagePresenter;
import com.abyte.wanandroid.ui.main.adapter.FeedArticleListAdapter;
import com.abyte.wanandroid.utils.CollectionUtils;
import com.abyte.wanandroid.utils.TraceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * 1.添加toolbar
 * Created by geyan on 2018
 */
public class HomeFragment extends BaseFragment<HomePagePresenter> implements HomePageContract.View {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private HomePagePresenter mHomePagePresenter;
    private List<HomeItem> mHomeItemList = new ArrayList<>();
    private FeedArticleListAdapter mAdapter;

    public static HomeFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected HomePagePresenter createPresenter() {
        mHomePagePresenter = new HomePagePresenter(mAdapter);
        return mHomePagePresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNeedDefaultTitle(false);
    }

    @Override
    protected void findViews(View view) {
        super.findViews(view);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.main_pager_recycler_view);

        mAdapter = new FeedArticleListAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHomeItemList.clear();
                mHomePagePresenter.setRefresh();
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        // 设置上拉加载更多
        recyclerView.addOnScrollListener(new LoadMoreOnScrollListener() {
            @Override
            public void loadMoreData() {
                TraceUtil.beginSection("loadMoreData");
                mHomePagePresenter.loadMoreData();
                TraceUtil.endSection();
            }
        });
    }

    /**
     * 不在onResume方法中请求网络数据
     */
    @Override
    protected void initData() {
        super.initData();
        TraceUtil.beginSection("initData");
        mHomePagePresenter.setCurrentPage(0);
        mHomePagePresenter.loadHomePageData();
        TraceUtil.endSection();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void setData(List<BannerEntity> bannerEntityList, FeedArticleListEntity feedArticleListEntity) {
        if (CollectionUtils.isNotEmpty(bannerEntityList)) {
            BannerListEntity bannerList = new BannerListEntity();
            bannerList.mBannerListEntity = bannerEntityList;
            mHomeItemList.add(bannerList);
        }

        List<FeedArticleEntity> dataList = feedArticleListEntity.datas;
        if (CollectionUtils.isNotEmpty(dataList)) {
            mHomeItemList.addAll(dataList);
        }
        mAdapter.setData(mHomeItemList);
    }

    @Override
    public void setData(FeedArticleListEntity feedArticleListEntity) {
        List<FeedArticleEntity> dataList = feedArticleListEntity.datas;
        if (CollectionUtils.isNotEmpty(dataList)) {
            mHomeItemList.addAll(dataList);
        }
        mAdapter.setData(mHomeItemList);
    }

    @Override
    public void onSuccess() {
        disEmptyPage();
    }

    @Override
    public void onFailure() {
        showEmptyPage();
    }

    @Override
    public void onEmptyClickRetry(View view) {
        super.onEmptyClickRetry(view);
        mHomePagePresenter.setCurrentPage(0);
        mHomePagePresenter.loadHomePageData();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

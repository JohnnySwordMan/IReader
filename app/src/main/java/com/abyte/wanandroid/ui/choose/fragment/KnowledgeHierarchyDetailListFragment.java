package com.abyte.wanandroid.ui.choose.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.abyte.wanandroid.R;
import com.abyte.wanandroid.base.fragment.BaseFragment;
import com.abyte.wanandroid.base.listener.LoadMoreOnScrollListener;
import com.abyte.wanandroid.contract.choose.KnowledgeHierarchyDetailListContract;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleEntity;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleListEntity;
import com.abyte.wanandroid.presenter.choose.KnowledgeHierarchyDetailListPresenter;
import com.abyte.wanandroid.ui.choose.adapter.KnowledgeHierarchyDetailListAdapter;
import com.abyte.wanandroid.utils.CollectionUtils;
import com.abyte.wanandroid.widget.SwipeRefreshLayoutCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyan on 2018/9/29
 */
public class KnowledgeHierarchyDetailListFragment extends BaseFragment<KnowledgeHierarchyDetailListPresenter> implements KnowledgeHierarchyDetailListContract.View {

    private static final String KEY_DETAIL_ID = "key_detail_id";
    private KnowledgeHierarchyDetailListPresenter mPresenter;
    private SwipeRefreshLayoutCompat swipeRefreshLayout;
    private RecyclerView recyclerView;
    private int id;
    private List<FeedArticleEntity> articleEntityList = new ArrayList<>();
    private KnowledgeHierarchyDetailListAdapter mAdapter;

    public static KnowledgeHierarchyDetailListFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_DETAIL_ID, id);
        KnowledgeHierarchyDetailListFragment fragment = new KnowledgeHierarchyDetailListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected KnowledgeHierarchyDetailListPresenter createPresenter() {
        mPresenter = new KnowledgeHierarchyDetailListPresenter(mAdapter);
        return mPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_hierarchy_detaik_list;
    }

    @Override
    protected void findViews(View view) {
        super.findViews(view);
        swipeRefreshLayout = view.findViewById(R.id.knowledge_detail_list_swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.knowledge_detail_list_recycler_view);
        mAdapter = new KnowledgeHierarchyDetailListAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        // 设置下拉刷新、上拉加载
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新逻辑
                articleEntityList.clear();
                mPresenter.setRefreshData();
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

        recyclerView.addOnScrollListener(new LoadMoreOnScrollListener() {
            @Override
            public void loadMoreData() {
                mPresenter.loadMoreData();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        id = bundle.getInt(KEY_DETAIL_ID);
        // 分页处理
        mPresenter.setCurrentPage(0, id);
        mPresenter.loadKnowledgeHierarchyDetailListData();
    }


    @Override
    public void showData(FeedArticleListEntity feedArticleListEntity) {
        if (feedArticleListEntity == null) {
            return;
        }
        List<FeedArticleEntity> feedArticleEntities = feedArticleListEntity.datas;
        if (CollectionUtils.isNotEmpty(feedArticleEntities)) {
            articleEntityList.addAll(feedArticleEntities);
        }
        // adapter设置数据
        mAdapter.setData(articleEntityList);
    }
}

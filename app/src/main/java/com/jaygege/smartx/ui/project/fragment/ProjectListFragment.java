package com.jaygege.smartx.ui.project.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jaygege.smartx.R;
import com.jaygege.smartx.base.fragment.BaseFragment;
import com.jaygege.smartx.base.listener.LoadMoreOnScrollListener;
import com.jaygege.smartx.contract.project.ProjectListPageContract;
import com.jaygege.smartx.core.bean.home.collect.FeedArticleEntity;
import com.jaygege.smartx.core.bean.project.ProjectListEntity;
import com.jaygege.smartx.presenter.project.ProjectListPagePresenter;
import com.jaygege.smartx.ui.project.adapter.ProjectListAdapter;
import com.jaygege.smartx.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyan on 2018/9/27
 */
public class ProjectListFragment extends BaseFragment<ProjectListPagePresenter> implements ProjectListPageContract.View {

    public static final String KEY_TAB_ID = "key_tab_id";
    private ProjectListPagePresenter mPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private int id;
    private List<FeedArticleEntity> entityList = new ArrayList<>();
    private ProjectListAdapter mAdapter;

    public static ProjectListFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TAB_ID, id);
        ProjectListFragment fragment = new ProjectListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected ProjectListPagePresenter createPresenter() {
        mPresenter = new ProjectListPagePresenter(mAdapter);
        return mPresenter;
    }

    @Override
    protected void findViews(View view) {
        super.findViews(view);
        swipeRefreshLayout = view.findViewById(R.id.project_list_swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.project_list_recycler_view);
        mAdapter = new ProjectListAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                entityList.clear();
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
        Bundle arguments = getArguments();
        id = arguments.getInt(KEY_TAB_ID);
        mPresenter.setCurrentPage(0, id);
        mPresenter.loadProjectListData();
    }

    @Override
    public void setData(ProjectListEntity entity) {
        if (entity == null || CollectionUtils.isEmpty(entity.datas)) {
            showEmptyPage();
            return;
        }
        disEmptyPage();
        List<FeedArticleEntity> feedArticleEntities = entity.datas;
        entityList.addAll(feedArticleEntities);
        mAdapter.setData(entityList);
    }

    @Override
    public void onFailure() {
        showEmptyPage();
    }

    @Override
    public void onEmptyClickRetry(View view) {
        super.onEmptyClickRetry(view);
        mPresenter.setCurrentPage(0, id);
        mPresenter.loadProjectListData();
    }
}

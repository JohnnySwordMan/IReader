package com.jaygege.smartx.ui.choose.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jaygege.smartx.R;
import com.jaygege.smartx.base.fragment.BaseFragment;
import com.jaygege.smartx.contract.choose.ChoosePageContract;
import com.jaygege.smartx.core.bean.choose.KnowledgeHierarchyEntity;
import com.jaygege.smartx.presenter.choose.ChoosePagePresenter;
import com.jaygege.smartx.ui.choose.adapter.KnowledgeHierarchyAdapter;
import com.jaygege.smartx.utils.CollectionUtils;

import java.util.List;

/**
 * Created by geyan on 2018
 */
public class ChooseFragment extends BaseFragment<ChoosePagePresenter> implements ChoosePageContract.View {
    /**
     * 1.已经获取到网络数据，但是页面没有显示？----------没有设置layoutManager！
     */

    private ChoosePagePresenter mChoosePagePresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private KnowledgeHierarchyAdapter mAdapter;

    public static ChooseFragment newInstance() {
        Bundle bundle = new Bundle();
        ChooseFragment fragment = new ChooseFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected ChoosePagePresenter createPresenter() {
        mChoosePagePresenter = new ChoosePagePresenter();
        return mChoosePagePresenter;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_choose;
    }


    @Override
    protected void findViews(View view) {
        super.findViews(view);
        swipeRefreshLayout = view.findViewById(R.id.choose_page_swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.choose_page_recycler_view);

        // 创建Adapter
        mAdapter = new KnowledgeHierarchyAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mChoosePagePresenter.setRefresh();
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
    }

    @Override
    protected void initData() {
        super.initData();
        mChoosePagePresenter.loadChoosePageData();
    }


    @Override
    public void setData(List<KnowledgeHierarchyEntity> list) {
        if (list == null) {
            showEmptyPage();
            return;
        }
        disEmptyPage();
        mAdapter.setData(list);
    }

    @Override
    public void onFailure() {
        showEmptyPage();
    }

    @Override
    public void onEmptyClickRetry(View view) {
        super.onEmptyClickRetry(view);
        // 不要直接调用initData()，如果以后在父类的initData()中做一些数据初始化操作，然后再onEmptyClickRetry中直接调用initData()是错误的
        mChoosePagePresenter.loadChoosePageData();
    }
}

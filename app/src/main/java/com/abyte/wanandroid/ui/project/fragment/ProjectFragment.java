package com.abyte.wanandroid.ui.project.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.abyte.wanandroid.R;
import com.abyte.wanandroid.base.fragment.BaseFragment;
import com.abyte.wanandroid.contract.project.ProjectPageContract;
import com.abyte.wanandroid.core.bean.project.ProjectTabEntity;
import com.abyte.wanandroid.presenter.project.ProjectPagePresenter;
import com.abyte.wanandroid.ui.project.adapter.ProjectPagerAdapter;
import com.abyte.wanandroid.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目整合页面
 * Created by geyan on 2018
 */
public class ProjectFragment extends BaseFragment<ProjectPagePresenter> implements ProjectPageContract.View {

    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private ProjectPagePresenter mProjectPagePresenter;
    private List<ProjectTabEntity> tabEntityList = new ArrayList<>();
    private ProjectPagerAdapter mAdapter;

    public static ProjectFragment newInstance() {
        Bundle bundle = new Bundle();
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected ProjectPagePresenter createPresenter() {
        mProjectPagePresenter = new ProjectPagePresenter();
        return mProjectPagePresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_picture;
    }

    @Override
    protected void findViews(View view) {
        super.findViews(view);
        tabLayout = view.findViewById(R.id.project_page_sliding_tab);
        viewPager = view.findViewById(R.id.project_page_view_pager);

        mAdapter = new ProjectPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    protected void initData() {
        super.initData();
        mProjectPagePresenter.loadTabData();
    }

    @Override
    public void updateTabTitles(List<ProjectTabEntity> list) {
        if (CollectionUtils.isEmpty(list)) {
            showEmptyPage();
            return;
        }
        disEmptyPage();
        tabEntityList.clear();
        tabEntityList.addAll(list);
        mAdapter.setData(tabEntityList);
        tabLayout.notifyDataSetChanged();
    }

    @Override
    public void onFailure() {
        showEmptyPage();
    }

    @Override
    public void onEmptyClickRetry(View view) {
        super.onEmptyClickRetry(view);
        mProjectPagePresenter.loadTabData();
    }
}

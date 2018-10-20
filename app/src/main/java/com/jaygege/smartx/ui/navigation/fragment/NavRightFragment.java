package com.jaygege.smartx.ui.navigation.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.jaygege.smartx.R;
import com.jaygege.smartx.base.fragment.BaseFragment;
import com.jaygege.smartx.contract.navigation.NavigationRightContract;
import com.jaygege.smartx.core.bean.navigation.NavigationListEntity;
import com.jaygege.smartx.presenter.navigation.NavigationRightPresenter;
import com.jaygege.smartx.ui.navigation.adapter.NavigationAdapter;

/**
 * Created by geyan on 2018/10/3
 */
public class NavRightFragment extends BaseFragment<NavigationRightPresenter> implements NavigationRightContract.View {

    public static final String KEY_POSITION = "key_position";
    public static final String KEY_NAVIGATION = "key_navigation";
    private RecyclerView recyclerView;
    private NavigationAdapter mAdapter;
    private NavigationRightPresenter mPresenter;


    public static NavRightFragment newInstance(int position, String navigationStr) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POSITION, position);
        bundle.putString(KEY_NAVIGATION, navigationStr);
        NavRightFragment fragment = new NavRightFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected NavigationRightPresenter createPresenter() {
        mPresenter = new NavigationRightPresenter();
        return mPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation_right;
    }


    @Override
    protected void findViews(View view) {
        super.findViews(view);
        recyclerView = view.findViewById(R.id.nav_right_recycler_view);
        mAdapter = new NavigationAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        String navigationListEntityStr = arguments.getString(KEY_NAVIGATION);
        NavigationListEntity navigationListEntity = NavigationListEntity.buildObject(navigationListEntityStr, new TypeToken<NavigationListEntity>() {
        });
        mAdapter.setData(navigationListEntity);
    }
}

package com.abyte.wanandroid.ui.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abyte.wanandroid.R;
import com.abyte.wanandroid.base.fragment.BaseFragment;
import com.abyte.wanandroid.contract.me.MePageContract;
import com.abyte.wanandroid.presenter.me.MePagePresenter;
import com.abyte.wanandroid.ui.LoginActivity;
import com.abyte.wanandroid.ui.me.LoginMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 我的页面
 * Created by geyan on 2018/10/17
 */
public class MeFragment extends BaseFragment<MePagePresenter> implements MePageContract.View, View.OnClickListener {


    private TextView mTvLogin;
    private String userName;
    private LinearLayout mLLCollect;
    private LinearLayout mLLSetting;
    private LinearLayout mLLogout;

    public static MeFragment newInstance() {
        Bundle bundle = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected MePagePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void findViews(View view) {
        super.findViews(view);
        mTvLogin = view.findViewById(R.id.tv_login);
        mLLCollect = view.findViewById(R.id.ll_collect);
        mLLSetting = view.findViewById(R.id.ll_setting);
        mLLogout = view.findViewById(R.id.ll_logout);
        mTvLogin.setOnClickListener(this);
        mLLCollect.setOnClickListener(this);
        mLLSetting.setOnClickListener(this);
        mLLogout.setOnClickListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                if (TextUtils.isEmpty(userName)) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.ll_collect:
                Toast.makeText(getActivity(), "敬请期待...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_setting:
                Toast.makeText(getActivity(), "敬请期待...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_logout:
                Toast.makeText(getActivity(), "敬请期待...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginMessageEvent(LoginMessageEvent event) {
        if (event.getCode() == 200) {
            // 登录成功
            userName = event.getUserName();
            mTvLogin.setText(userName);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

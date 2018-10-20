package com.jaygege.smartx.ui.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jaygege.smartx.R;
import com.jaygege.smartx.base.fragment.BaseFragment;
import com.jaygege.smartx.contract.me.MePageContract;
import com.jaygege.smartx.presenter.me.MePagePresenter;
import com.jaygege.smartx.ui.LoginActivity;

/**
 * 我的页面
 * Created by Jaygege on 2018/10/17
 */
public class MeFragment extends BaseFragment<MePagePresenter> implements MePageContract.View {


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
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}

package com.abyte.wanandroid.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abyte.wanandroid.SmartApp;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Jaygege on 2018/9/20
 */
public abstract class AbstractSimpleFragment extends Fragment {


    protected View contentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(getLayoutId(), container, false);

        findViews(contentView);
        return contentView;
    }

    protected abstract void findViews(View view);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = SmartApp.getRetWatcher(getActivity());
        refWatcher.watch(this);
    }

    protected abstract int getLayoutId();

}

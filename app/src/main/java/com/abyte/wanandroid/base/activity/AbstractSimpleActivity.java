package com.abyte.wanandroid.base.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Jaygege on 2018
 */
public abstract class AbstractSimpleActivity extends AppCompatActivity {

    private Bundle bundle;
    protected View mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        bundle = getIntent().getExtras();
        if (bundle == null && savedInstanceState != null) {
            bundle = savedInstanceState;
        }
        if (bundle != null) {
            onCreateIntentExtras(bundle);
        }
        // 以下方法得在onCreateIntentExtras()之前执行，不能放在onContentChanged()中执行
        findViews();
        initClickListener();
        initTitle();
        initView();
    }

    protected void onCreateIntentExtras(Bundle bundle) {

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ViewGroup content = findViewById(android.R.id.content);
        if(content != null){
            // setContentView中设置的布局
            mContentView = content.getChildAt(0);
        }
    }

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initClickListener();

    protected abstract void findViews();

    // 当前Activity的布局
    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

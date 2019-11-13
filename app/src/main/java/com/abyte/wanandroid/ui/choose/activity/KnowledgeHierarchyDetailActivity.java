package com.abyte.wanandroid.ui.choose.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.reflect.TypeToken;
import com.abyte.wanandroid.R;
import com.abyte.wanandroid.base.activity.BaseActivity;
import com.abyte.wanandroid.contract.choose.KnowledgeHierarchyDetailContract;
import com.abyte.wanandroid.core.bean.choose.KnowledgeHierarchyEntity;
import com.abyte.wanandroid.presenter.choose.KnowledgeHierarchyDetailPresenter;
import com.abyte.wanandroid.ui.choose.adapter.KnowledgeHierarchyDetailAdapter;
import com.abyte.wanandroid.utils.CollectionUtils;

import java.util.List;

/**
 * Created by geyan on 2018/9/29
 */
public class KnowledgeHierarchyDetailActivity extends BaseActivity<KnowledgeHierarchyDetailPresenter> implements KnowledgeHierarchyDetailContract.View {

    public static final String KEY_DETAIL_DATA = "key_detail_data";
    private KnowledgeHierarchyDetailPresenter mPresenter;
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private KnowledgeHierarchyEntity knowledgeHierarchyEntity;
    private KnowledgeHierarchyDetailAdapter mAdapter;

    @Override
    protected KnowledgeHierarchyDetailPresenter createPresenter() {
        mPresenter = new KnowledgeHierarchyDetailPresenter();
        return mPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_hierarchy_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNeedDefaultTitle(false);
    }

    @Override
    protected void onCreateIntentExtras(Bundle bundle) {
        super.onCreateIntentExtras(bundle);
        String jsonStr = bundle.getString(KEY_DETAIL_DATA);
        if (!TextUtils.isEmpty(jsonStr)) {
            knowledgeHierarchyEntity = KnowledgeHierarchyEntity.buildObject(jsonStr, new TypeToken<KnowledgeHierarchyEntity>() {
            });
        }
    }

    @Override
    protected void findViews() {
        super.findViews();
        tabLayout = findViewById(R.id.knowledge_detail_sliding_tab);
        viewPager = findViewById(R.id.knowledge_detail_view_pager);

        mAdapter = new KnowledgeHierarchyDetailAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout.setViewPager(viewPager);

        Log.d("aa","findViews");
    }

    @Override
    protected void initView() {
        super.initView();
        List<KnowledgeHierarchyEntity> entityList = knowledgeHierarchyEntity.children;
        updateTabTitles(entityList);
        Log.d("aa","initView");
    }


    private void updateTabTitles(List<KnowledgeHierarchyEntity> list) {
        if (CollectionUtils.isEmpty(list)) {
            // 显示空白页
            return;
        }
        mAdapter.setData(list);
        tabLayout.notifyDataSetChanged();
        Log.d("aa","updateTabTitles");
    }
}

package com.jaygege.smartx.ui.choose.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jaygege.smartx.core.bean.choose.KnowledgeHierarchyEntity;
import com.jaygege.smartx.ui.choose.fragment.KnowledgeHierarchyDetailListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyan on 2018/9/29
 */
public class KnowledgeHierarchyDetailAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<KnowledgeHierarchyEntity> entities;

    public KnowledgeHierarchyDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return entities.get(position).name;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void setData(List<KnowledgeHierarchyEntity> list) {
        this.entities = list;
        newFragments();
        notifyDataSetChanged();
    }

    private void newFragments() {
        fragments.clear();
        for (int i = 0; i < entities.size(); i++) {
            // 创建Fragment
            KnowledgeHierarchyEntity knowledgeHierarchyEntity = entities.get(i);
            fragments.add(KnowledgeHierarchyDetailListFragment.newInstance(knowledgeHierarchyEntity.id));
        }
    }
}

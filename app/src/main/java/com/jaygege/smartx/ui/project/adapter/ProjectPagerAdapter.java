package com.jaygege.smartx.ui.project.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jaygege.smartx.core.bean.project.ProjectTabEntity;
import com.jaygege.smartx.ui.project.fragment.ProjectListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyan on 2018/9/27
 */
public class ProjectPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<ProjectTabEntity> tabEntityList;

    public ProjectPagerAdapter(FragmentManager fm) {
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
        return tabEntityList.get(position).name;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void setData(List<ProjectTabEntity> tabEntities) {
        this.tabEntityList = tabEntities;
        newFragments();
        notifyDataSetChanged();
    }

    // 创建tab对应的Fragment
    private void newFragments() {
        fragments.clear();
        for (int i = 0; i < tabEntityList.size(); i++) {
            ProjectTabEntity entity = tabEntityList.get(i);
            fragments.add(ProjectListFragment.newInstance(entity.id));
        }
    }
}

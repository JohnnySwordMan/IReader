package com.jaygege.smartx.ui.navigation.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jaygege.smartx.R;
import com.jaygege.smartx.base.fragment.BaseFragment;
import com.jaygege.smartx.contract.navigation.NavigationPageContract;
import com.jaygege.smartx.core.bean.navigation.NavigationListEntity;
import com.jaygege.smartx.presenter.navigation.NavigationPagePresenter;
import com.jaygege.smartx.utils.CollectionUtils;
import com.jaygege.smartx.widget.VerticalViewPager;
import com.jaygege.smartx.widget.verticaltablayout.QTabView;
import com.jaygege.smartx.widget.verticaltablayout.TabAdapter;
import com.jaygege.smartx.widget.verticaltablayout.TabView;
import com.jaygege.smartx.widget.verticaltablayout.VerticalTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaygege on 2018
 */
public class NavigationFragment extends BaseFragment<NavigationPagePresenter> implements NavigationPageContract.View {

    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.75f;

    private NavigationPagePresenter mPresenter;
    private VerticalTabLayout tabLayout;
    private VerticalViewPager viewPager;
    private List<NavigationListEntity> entityList = new ArrayList<>();

    public static NavigationFragment newInstance() {
        Bundle bundle = new Bundle();
        NavigationFragment fragment = new NavigationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected NavigationPagePresenter createPresenter() {
        mPresenter = new NavigationPagePresenter();
        return mPresenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void findViews(View view) {
        super.findViews(view);
        tabLayout = view.findViewById(R.id.navigation_page_vertical_tab);
        viewPager = view.findViewById(R.id.navigation_page_view_pager);
    }

    @Override
    protected void initData() {
        super.initData();
        // 请求网络
        mPresenter.loadNavigationData();
    }

    @Override
    public void showNavigationData(List<NavigationListEntity> navEntityList) {
        if (CollectionUtils.isEmpty(navEntityList)) {
            // 显示网络错误页面
            showEmptyPage();
            return;
        }
        disEmptyPage();
        entityList.clear();
        entityList.addAll(navEntityList);
        // 设置左侧tab
        tabLayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return navEntityList.size();
            }

            @Override
            public int getBadge(int position) {
                if (position == navEntityList.size()) {
                    return position;
                }
                return 0;
            }

            @Override
            public QTabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public QTabView.TabTitle getTitle(int position) {
                return new QTabView.TabTitle.Builder(getActivity())
                        .setContent(navEntityList.get(position).name)
                        .setTextColor(ContextCompat.getColor(getActivity(), R.color.shallow_green), ContextCompat.getColor(getActivity(), R.color.shallow_grey))
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        });

        // 左侧选中，更新右侧相应位置
        tabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                // 选中左侧某tab，右侧viewpager跳转到对应位置
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setTabSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new RightTabAdapter(getActivity().getSupportFragmentManager(), navEntityList.size()));

        /**
         * 仔细看看下面几个方法，如果不加，好像是有问题的！！！
         */
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.dp_16));
        viewPager.setPageMarginDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_dark)));
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 1) { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        view.setTranslationY(vertMargin - horzMargin / 2);
                    } else {
                        view.setTranslationY(-vertMargin + horzMargin / 2);
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                    // Fade the page relative to its size.
                    view.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
            }
        });
    }

    @Override
    public void onFailure() {
        showEmptyPage();
    }

    @Override
    public void onEmptyClickRetry(View view) {
        super.onEmptyClickRetry(view);
        mPresenter.loadNavigationData();
    }

    private class RightTabAdapter extends FragmentPagerAdapter {

        List<NavRightFragment> fragments = new ArrayList<>();

        public RightTabAdapter(FragmentManager fm, int num) {
            super(fm);

            for (int i = 0; i < num; i++) {
                NavigationListEntity entity = entityList.get(i);
                fragments.add(NavRightFragment.newInstance(i, entity.toGson()));
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}

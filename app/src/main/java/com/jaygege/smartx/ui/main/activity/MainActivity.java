package com.jaygege.smartx.ui.main.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaygege.smartx.R;
import com.jaygege.smartx.base.Constant;
import com.jaygege.smartx.base.activity.BaseActivity;
import com.jaygege.smartx.contract.main.MainContract;
import com.jaygege.smartx.presenter.main.MainPresenter;
import com.jaygege.smartx.ui.choose.fragment.ChooseFragment;
import com.jaygege.smartx.ui.main.fragment.HomeFragment;
import com.jaygege.smartx.ui.me.fragment.MeFragment;
import com.jaygege.smartx.ui.navigation.fragment.NavigationFragment;
import com.jaygege.smartx.ui.project.fragment.ProjectFragment;


/**
 * Created by Jaygege on 2018
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, View.OnClickListener {

    private static final String TAB_HOME_PAGE = "tab_home_page";
    private static final String TAB_HOME_CHOOSE = "tab_home_choose";
    private static final String TAB_HOME_PIC = "tab_home_pic";
    private static final String TAB_HOME_NAV = "tab_home_nav";
    private static final String TAB_HOME_ME = "tab_home_me";

    private FrameLayout container;
    private LinearLayout homePageLayout;
    private RelativeLayout homeChooseLayout;
    private ImageView mIvHomeChoosePoint;
    private RelativeLayout homePicLayout;
    private ImageView mIvHomePicPoint;
    private RelativeLayout homeNavigationLayout;
    private ImageView mIvHomeNavigationPoint;

    private HomeFragment homeFragment;
    private ChooseFragment chooseFragment;
    private ProjectFragment pictureFragment;
    private NavigationFragment navFragment;

    private int mCurTabIndex = -1;
    private TabInfo[] tabGroup;
    private MainPresenter mMainPresenter;
    private RelativeLayout mRLHomeMe;
    private ImageView mIvHomeMePoint;
    private MeFragment meFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragments(savedInstanceState);
    }

    @Override
    protected void findViews() {
        super.findViews();
        container = findViewById(R.id.container_layout);

        homePageLayout = findViewById(R.id.ll_home_page);

        homeChooseLayout = findViewById(R.id.rl_home_choose);
        mIvHomeChoosePoint = findViewById(R.id.iv_home_choose_point);

        homePicLayout = findViewById(R.id.rl_home_pic);
        mIvHomePicPoint = findViewById(R.id.iv_home_pic_point);

        homeNavigationLayout = findViewById(R.id.rl_navigation);
        mIvHomeNavigationPoint = findViewById(R.id.iv_home_navigation_point);

        mRLHomeMe = findViewById(R.id.rl_home_me);
        mIvHomeMePoint = findViewById(R.id.iv_home_me_point);
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        tabGroup = new TabInfo[5];
        int[] ivIdArray = {R.id.iv_home_page, R.id.iv_home_choose, R.id.iv_home_pic, R.id.iv_home_navigation, R.id.iv_home_me};
        int[] tvIdArray = {R.id.tv_home_page, R.id.tv_home_choose, R.id.tv_home_pic, R.id.tv_home_navigation, R.id.tv_home_me};
        for (int i = 0; i < tabGroup.length; i++) {
            ImageView ivTab = findViewById(ivIdArray[i]);
            TextView tvTab = findViewById(tvIdArray[i]);
            tabGroup[i] = createTab(i, ivTab, tvTab);
        }
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initClickListener() {
        super.initClickListener();
        homePageLayout.setOnClickListener(this);
        homeChooseLayout.setOnClickListener(this);
        homePicLayout.setOnClickListener(this);
        homeNavigationLayout.setOnClickListener(this);
        mRLHomeMe.setOnClickListener(this);
    }

    private void initFragments(Bundle savedInstanceState) {
        int curTabIndex = 0;
        if (savedInstanceState != null) {
            FragmentManager fm = getSupportFragmentManager();
            homeFragment = (HomeFragment) fm.findFragmentByTag(TAB_HOME_PAGE);
            chooseFragment = (ChooseFragment) fm.findFragmentByTag(TAB_HOME_CHOOSE);
            pictureFragment = (ProjectFragment) fm.findFragmentByTag(TAB_HOME_PIC);
            navFragment = (NavigationFragment) fm.findFragmentByTag(TAB_HOME_NAV);
            meFragment = (MeFragment) fm.findFragmentByTag(TAB_HOME_ME);
        }
        changeTab(curTabIndex);
        // 保存当前page
        mMainPresenter.setCurrentPage(Constant.TYPE_HOME_PAGE);
    }


    private void changeTab(int index) {
        // 如果想要切换的tab就是当前tab，则不继续执行
        if (mCurTabIndex == index) {
            return;
        }
        mCurTabIndex = index;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideTabFragments(ft, index);
        addOrShowTabFragment(ft, index);
        ft.commitAllowingStateLoss();
        // 改变底部tab的选中状态
        setTabSelected(index);
    }

    private void setTabSelected(int index) {
        for (int i = 0; i < tabGroup.length; i++) {
            if (i == index) {
                tabGroup[i].setTextColor(getResources().getColor(R.color.yellow));
                tabGroup[i].setIcon(tabGroup[i].resSelected);
            } else {
                tabGroup[i].setTextColor(getResources().getColor(R.color.text_normal));
                tabGroup[i].setIcon(tabGroup[i].resNormal);
            }
        }
    }

    /**
     * 隐藏其他Fragment
     */
    private void hideTabFragments(FragmentTransaction ft, int index) {
        if (index != 0 && homeFragment != null) {
            ft.hide(homeFragment);
        }

        if (index != 1 && chooseFragment != null) {
            ft.hide(chooseFragment);
        }

        if (index != 2 && pictureFragment != null) {
            ft.hide(pictureFragment);
        }

        if (index != 3 && navFragment != null) {
            ft.hide(navFragment);
        }

        if (index != 4 && meFragment != null) {
            ft.hide(meFragment);
        }
    }

    /**
     * 添加或展示Fragment
     */
    private void addOrShowTabFragment(FragmentTransaction ft, int index) {
        if (index == 0) {
            if (homeFragment == null) {
                homeFragment = HomeFragment.newInstance();
                ft.add(R.id.container_layout, homeFragment, TAB_HOME_PAGE);
            } else {
                ft.show(homeFragment);
            }
        }
        if (index == 1) {
            if (chooseFragment == null) {
                chooseFragment = ChooseFragment.newInstance();
                ft.add(R.id.container_layout, chooseFragment, TAB_HOME_CHOOSE);
            } else {
                ft.show(chooseFragment);
            }
        }
        if (index == 2) {
            if (pictureFragment == null) {
                pictureFragment = ProjectFragment.newInstance();
                ft.add(R.id.container_layout, pictureFragment, TAB_HOME_PIC);
            } else {
                ft.show(pictureFragment);
            }
        }
        if (index == 3) {
            if (navFragment == null) {
                navFragment = NavigationFragment.newInstance();
                ft.add(R.id.container_layout, navFragment, TAB_HOME_NAV);
            }
            ft.show(navFragment);
        }
        if (index == 4) {
            if (meFragment == null) {
                meFragment = MeFragment.newInstance();
                ft.add(R.id.container_layout, meFragment, TAB_HOME_ME);
            }
            ft.show(meFragment);
        }
    }


    @Override
    protected MainPresenter createPresenter() {
        mMainPresenter = new MainPresenter();
        return mMainPresenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home_page:
                changeTab(0);
                break;
            case R.id.rl_home_choose:
                changeTab(1);
                break;
            case R.id.rl_home_pic:
                changeTab(2);
                break;
            case R.id.rl_navigation:
                changeTab(3);
                break;
            case R.id.rl_home_me:
                changeTab(4);
                break;
        }
    }


    /**
     * 创建底部tab
     */
    private TabInfo createTab(int index, ImageView ivTab, TextView tvTab) {
        TabInfo tabInfo = new TabInfo();
        tabInfo.ivTab = ivTab;
        tabInfo.tvLabel = tvTab;
        switch (index) {
            case 0:
                tabInfo.resNormal = R.mipmap.icon_home_pager_not_selected;
                tabInfo.resSelected = R.mipmap.icon_home_pager_selected;
                break;
            case 1:
                tabInfo.resNormal = R.mipmap.icon_knowledge_hierarchy_not_selected;
                tabInfo.resSelected = R.mipmap.icon_knowledge_hierarchy_selected;
                break;
            case 2:
                tabInfo.resNormal = R.mipmap.icon_navigation_not_selected;
                tabInfo.resSelected = R.mipmap.icon_navigation_selected;
                break;
            case 3:
                tabInfo.resNormal = R.mipmap.icon_project_not_selected;
                tabInfo.resSelected = R.mipmap.icon_project_selected;
                break;
            case 4:
                tabInfo.resNormal = R.mipmap.icon_tab_me;
                tabInfo.resSelected = R.mipmap.icon_tab_me;
                break;
        }
        return tabInfo;
    }

    private class TabInfo {
        private ImageView ivTab;
        private TextView tvLabel;
        private int resNormal;
        private int resSelected;

        private void setTextColor(int color) {
            if (tvLabel != null) {
                tvLabel.setTextColor(color);
            }
        }

        private void setIcon(int res) {
            if (ivTab != null) {
                ivTab.setImageResource(res);
            }
        }
    }
}

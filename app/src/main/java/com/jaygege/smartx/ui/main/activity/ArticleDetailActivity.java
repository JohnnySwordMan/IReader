package com.jaygege.smartx.ui.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jaygege.smartx.R;
import com.jaygege.smartx.base.Constant;
import com.jaygege.smartx.base.activity.BaseActivity;
import com.jaygege.smartx.contract.main.ArticleDetailContract;
import com.jaygege.smartx.presenter.main.ArticleDetailPresenter;
import com.just.agentweb.AgentWeb;

/**
 * 文章详情页
 * Created by geyan on 2018/9/23
 */
public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenter> implements ArticleDetailContract.View {

    private ArticleDetailPresenter mPresenter;
    private int articleId;
    private String articleTitle;
    private String articleLink;
    private boolean isCollect;
    private boolean isCollectPage;
    private boolean isCommonSite;
    private Toolbar mToolbar;
    private FrameLayout container;
    private AgentWeb mAgentWeb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected ArticleDetailPresenter createPresenter() {
        mPresenter = new ArticleDetailPresenter();
        return mPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void findViews() {
        super.findViews();
        mToolbar = findViewById(R.id.article_detail_toolbar);
        container = findViewById(R.id.article_detail_web_view);
        initToolbar();
    }

    private void initToolbar() {
        mToolbar.setTitle(articleTitle);
        // 设置toolbar
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onCreateIntentExtras(Bundle bundle) {
        super.onCreateIntentExtras(bundle);
        articleId = bundle.getInt(Constant.ARTICLE_ID);
        articleTitle = bundle.getString(Constant.ARTICLE_TITLE);
        articleLink = bundle.getString(Constant.ARTICLE_LINK);
        isCollect = bundle.getBoolean(Constant.IS_COLLECT);
        isCollectPage = bundle.getBoolean(Constant.IS_COLLECTY_PAGE);
        isCommonSite = bundle.getBoolean(Constant.IS_COMMON_SITE);
    }

    @Override
    protected void initView() {
        super.initView();
        // 创建AgentWeb
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.webview_error_view, -1)
                .createAgentWeb()
                .ready()
                .go(articleLink);

        /**
         * 需要设置支持缩放、自适应屏幕，以解决点击banner跳转到详情页的显示问题
         */
        WebView mWebView = mAgentWeb.getWebCreator().getWebView();
        WebSettings mSettings = mWebView.getSettings();

        mSettings.setJavaScriptEnabled(true);
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);

        // 隐藏缩放按钮
        mSettings.setDisplayZoomControls(false);

        // 自适应屏幕
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onEmptyClickRetry(View view) {
        super.onEmptyClickRetry(view);
    }
}

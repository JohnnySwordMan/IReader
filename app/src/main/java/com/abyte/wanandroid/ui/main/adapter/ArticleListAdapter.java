package com.abyte.wanandroid.ui.main.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.abyte.wanandroid.R;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleEntity;

import java.util.List;

/**
 * Created by geyan on 2018/9/23
 */
public class ArticleListAdapter extends BaseQuickAdapter<FeedArticleEntity, ArticleListAdapter.ArticleHolder> {

    private boolean isCollectPage;

    public ArticleListAdapter(int layoutResId, @Nullable List<FeedArticleEntity> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(ArticleHolder helper, FeedArticleEntity item) {
        if (!TextUtils.isEmpty(item.title)) {
            helper.setText(R.id.home_article_content_tv, Html.fromHtml(item.title));
        }
        if (item.collect || isCollectPage) {
            // 已收藏的文章
            helper.setImageResource(R.id.home_article_like_iv, R.drawable.icon_like);
        } else {
            helper.setImageResource(R.id.home_article_like_iv, R.drawable.icon_like_article_not_selected);
        }
        if (!TextUtils.isEmpty(item.author)) {
            helper.setText(R.id.home_article_author_tv, item.author);
        }
        if (!TextUtils.isEmpty(item.chapterName)) {
            String projectClassifyName = item.superChapterName + " / " + item.chapterName;
            if (isCollectPage) {
                helper.setText(R.id.home_article_chapter_name_tv, item.chapterName);
            } else {
                helper.setText(R.id.home_article_chapter_name_tv, projectClassifyName);
            }
        }
        if (!TextUtils.isEmpty(item.niceDate)) {
            helper.setText(R.id.home_article_time_tv, item.niceDate);
        }

        // 点击事件
        helper.addOnClickListener(R.id.home_article_chapter_name_tv);
        helper.addOnClickListener(R.id.home_article_like_iv);
    }

    class ArticleHolder extends BaseViewHolder {

        TextView mTvAuthor;
        TextView mTvChapterName;
        TextView mTvContent;
        ImageView mIvLike;
        TextView mTvTime;

        public ArticleHolder(View view) {
            super(view);

            mTvAuthor = view.findViewById(R.id.home_article_author_tv);
            mTvChapterName = view.findViewById(R.id.home_article_chapter_name_tv);
            mTvContent = view.findViewById(R.id.home_article_content_tv);
            mIvLike = view.findViewById(R.id.home_article_like_iv);
            mTvTime = view.findViewById(R.id.home_article_time_tv);
        }
    }
}

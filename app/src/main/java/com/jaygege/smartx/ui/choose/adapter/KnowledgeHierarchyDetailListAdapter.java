package com.jaygege.smartx.ui.choose.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaygege.smartx.R;
import com.jaygege.smartx.base.Constant;
import com.jaygege.smartx.core.bean.home.collect.FeedArticleEntity;
import com.jaygege.smartx.core.route.SmartPushRoute;
import com.jaygege.smartx.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识体系-每个child的子列表
 * Created by geyan on 2018/9/30
 */
public class KnowledgeHierarchyDetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOT_VIEW = 2;
    private Context mContext;
    private List<FeedArticleEntity> entityList = new ArrayList<>();
    private int currentLoadState = Constant.LOADING_COMPLETE;

    public KnowledgeHierarchyDetailListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<FeedArticleEntity> list) {
        entityList.clear();
        if (CollectionUtils.isNotEmpty(list)) {
            entityList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return entityList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOT_VIEW;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT_VIEW) {
            // inflate其实是通过io读取数据的，性能差，如果可以，最好通过new View的方式来创建布局
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.load_more_foot_view, parent, false);
            return new LoadMoreViewHolder(itemView);
        }
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.home_article_item, parent, false);
        return new ArticleDetailListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreViewHolder) {
            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;
            loadMoreViewHolder.onBindData();
        } else if (holder instanceof ArticleDetailListHolder) {
            ArticleDetailListHolder articleDetailListHolder = (ArticleDetailListHolder) holder;
            articleDetailListHolder.onBindData(position);
        }
    }

    public void setLoadingState(int state) {
        this.currentLoadState = state;
        notifyDataSetChanged();
    }

    class ArticleDetailListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTvAuthor;
        TextView mTvChapterName;
        TextView mTvContent;
        ImageView mIvLike;
        TextView mTvTime;
        CardView cardView;
        FeedArticleEntity articleEntity;

        ArticleDetailListHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.home_article_card_view);
            mTvAuthor = itemView.findViewById(R.id.home_article_author_tv);
            mTvChapterName = itemView.findViewById(R.id.home_article_chapter_name_tv);
            mTvContent = itemView.findViewById(R.id.home_article_content_tv);
            mIvLike = itemView.findViewById(R.id.home_article_like_iv);
            mTvTime = itemView.findViewById(R.id.home_article_time_tv);
            cardView.setOnClickListener(this);
        }

        public void onBindData(int position) {
            articleEntity = entityList.get(position);
            if (articleEntity != null) {
                // 设置作者
                mTvAuthor.setText(articleEntity.author);
                // 设置章节名称
                if (!TextUtils.isEmpty(articleEntity.chapterName)) {
                    String classifyName = articleEntity.superChapterName + " / " + articleEntity.chapterName;
                    mTvChapterName.setText(classifyName);
                } else {
                    mTvChapterName.setText(articleEntity.superChapterName);
                }
                // 设置内容
                mTvContent.setText(articleEntity.title);
                mTvTime.setText(articleEntity.niceDate);
                mIvLike.setImageResource(articleEntity.collect ? R.drawable.icon_like : R.drawable.icon_like_article_not_selected);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.home_article_card_view:
                    startArticleDetailPage();
                    break;
            }
        }

        private void startArticleDetailPage() {
            SmartPushRoute.startArticleDetailActivity(mContext, articleEntity.id, articleEntity.title, articleEntity.link, articleEntity.collect, false, false);
        }
    }

    class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        ProgressBar mPbLoading;
        TextView mTvLoading;
        LinearLayout mLinearEnd;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            mPbLoading = itemView.findViewById(R.id.pb_loading);
            mTvLoading = itemView.findViewById(R.id.tv_loading);
            mLinearEnd = itemView.findViewById(R.id.ll_end);
        }

        public void onBindData() {
            switch (currentLoadState) {
                case Constant.LOADING:
                    // 正在加载
                    mPbLoading.setVisibility(View.VISIBLE);
                    mTvLoading.setVisibility(View.VISIBLE);
                    mLinearEnd.setVisibility(View.GONE);
                    break;
                case Constant.LOADING_COMPLETE:
                    // 加载完成
                    mPbLoading.setVisibility(View.INVISIBLE);
                    mTvLoading.setVisibility(View.INVISIBLE);
                    mLinearEnd.setVisibility(View.GONE);
                    break;
                case Constant.LOADING_END:
                    // 没有数据了
                    mPbLoading.setVisibility(View.GONE);
                    mTvLoading.setVisibility(View.GONE);
                    mLinearEnd.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

}

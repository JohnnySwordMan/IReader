package com.abyte.wanandroid.ui.project.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.abyte.wanandroid.R;
import com.abyte.wanandroid.base.Constant;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleEntity;
import com.abyte.wanandroid.core.route.SmartPushRoute;
import com.abyte.wanandroid.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geyan on 2018/9/27
 */
public class ProjectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOT_VIEW = 2;
    private Context mContext;
    private List<FeedArticleEntity> entityList = new ArrayList<>();
    private int currentLoadState = Constant.LOADING_COMPLETE;

    public ProjectListAdapter(Context context) {
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
        } else {
            return TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT_VIEW) {
            // 上拉加载更多
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.load_more_foot_view, parent, false);
            return new LoadMoreViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_project_list_page, parent, false);
            return new ProjectItemViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreViewHolder) {
            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;
            loadMoreViewHolder.onBindData();
        } else {
            ProjectItemViewHolder projectItemViewHolder = (ProjectItemViewHolder) holder;
            projectItemViewHolder.onBindData(position);
        }
    }

    public void setLoadingState(int state) {
        this.currentLoadState = state;
        notifyDataSetChanged();
    }

    class ProjectItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mIvItemImg;
        TextView mTvItemTitle;
        TextView mTvItemContent;
        TextView mTvItemTime;
        TextView mTvItemAuthor;
        TextView mTvItemInstall;
        CardView cardView;
        FeedArticleEntity feedArticleEntity;

        ProjectItemViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.project_list_item_card_view);
            mIvItemImg = itemView.findViewById(R.id.project_list_item_img);
            mTvItemTitle = itemView.findViewById(R.id.project_list_item_title);
            mTvItemContent = itemView.findViewById(R.id.project_list_item_content);
            mTvItemTime = itemView.findViewById(R.id.project_list_item_time);
            mTvItemAuthor = itemView.findViewById(R.id.project_list_item_author);
            mTvItemInstall = itemView.findViewById(R.id.project_list_item_install);
            cardView.setOnClickListener(this);
        }

        public void onBindData(int position) {
            feedArticleEntity = entityList.get(position);
            if (feedArticleEntity != null) {
                Glide.with(mContext).load(feedArticleEntity.envelopePic).into(mIvItemImg);
                mTvItemTitle.setText(feedArticleEntity.title);
                mTvItemContent.setText(feedArticleEntity.desc);
                mTvItemTime.setText(feedArticleEntity.niceDate);
                mTvItemAuthor.setText(feedArticleEntity.author);
                if (!TextUtils.isEmpty(feedArticleEntity.apkLink)) {
                    mTvItemInstall.setVisibility(View.VISIBLE);
                } else {
                    mTvItemInstall.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.project_list_item_card_view:
                    SmartPushRoute.startArticleDetailActivity(mContext, feedArticleEntity.id, feedArticleEntity.title, feedArticleEntity.link, feedArticleEntity.collect, false, false);
                    break;
            }
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
            }
        }
    }

}

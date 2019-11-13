package com.abyte.wanandroid.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.abyte.wanandroid.core.bean.home.HomeItem;
import com.abyte.wanandroid.core.bean.home.banner.BannerEntity;
import com.abyte.wanandroid.core.bean.home.banner.BannerListEntity;
import com.abyte.wanandroid.core.bean.home.collect.FeedArticleEntity;
import com.abyte.wanandroid.core.route.SmartPushRoute;
import com.abyte.wanandroid.utils.CollectionUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页-列表adapter
 * Created by geyan on 2018/9/25
 */
public class FeedArticleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // 显示正在加载的布局
    public static final int TYPE_FOOT_VIEW = 2;
    private int currentLoadState = Constant.LOADING_COMPLETE;

    private List<HomeItem> homeItems = new ArrayList<>();
    private Context mContext;

    public FeedArticleListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<HomeItem> list) {
        homeItems.clear();
        if (CollectionUtils.isNotEmpty(list)) {
            homeItems.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return homeItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOT_VIEW;
        } else {
            return homeItems.get(position).getItemType();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HomeItem.TYPE_BANNER) {
            // banner布局
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.home_head_banner, parent, false);
            return new BannerViewHolder(itemView);
        } else if (viewType == HomeItem.TYPE_NORMAL) {
            // article列表
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.home_article_item, parent, false);
            return new ArticleViewHolder(itemView);
        } else {
            // 上拉加载更多
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.load_more_foot_view, parent, false);
            return new LoadMoreViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerViewHolder) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.onBindData(position);
        } else if (holder instanceof ArticleViewHolder) {
            ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;
            articleViewHolder.onBindData(position);
        } else if (holder instanceof LoadMoreViewHolder) {
            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;
            loadMoreViewHolder.onBindData();
        }
    }

    public void setLoadingState(int state) {
        this.currentLoadState = state;
        notifyDataSetChanged();
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {

        Banner mHeadBanner;

        BannerViewHolder(View itemView) {
            super(itemView);
            mHeadBanner = itemView.findViewById(R.id.home_head_banner);
        }

        public void onBindData(int position) {
            HomeItem item = homeItems.get(position);
            BannerListEntity bannerList = (BannerListEntity) item;
            List<BannerEntity> mBannerListEntity = bannerList.mBannerListEntity;
            dealWithBannerData(mBannerListEntity);
        }

        // 处理banner
        private void dealWithBannerData(List<BannerEntity> bannerEntityList) {
            List<String> mBannerTitleList = new ArrayList<>();
            List<String> mBannerUrlList = new ArrayList<>();
            List<String> mBannerImageList = new ArrayList<>();

            for (BannerEntity entity : bannerEntityList) {
                mBannerTitleList.add(entity.title);
                mBannerUrlList.add(entity.url);
                mBannerImageList.add(entity.imagePath);
            }
            // 设置banner
            mHeadBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
            mHeadBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            });
            mHeadBanner.setImages(mBannerImageList);
            mHeadBanner.setBannerAnimation(Transformer.DepthPage);
            mHeadBanner.setBannerTitles(mBannerTitleList);
            mHeadBanner.isAutoPlay(true);
            mHeadBanner.setDelayTime(bannerEntityList.size() * 500);
            mHeadBanner.setIndicatorGravity(BannerConfig.CENTER);
            mHeadBanner.setOnBannerListener(i -> SmartPushRoute.startArticleDetailActivity(mContext, 0, mBannerTitleList.get(i),
                    mBannerUrlList.get(i), false, false, true));
            mHeadBanner.start();
        }
    }


    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTvAuthor;
        TextView mTvChapterName;
        TextView mTvContent;
        ImageView mIvLike;
        TextView mTvTime;
        CardView cardView;
        FeedArticleEntity articleItem;

        ArticleViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.home_article_card_view);
            mTvAuthor = view.findViewById(R.id.home_article_author_tv);
            mTvChapterName = view.findViewById(R.id.home_article_chapter_name_tv);
            mTvContent = view.findViewById(R.id.home_article_content_tv);
            mIvLike = view.findViewById(R.id.home_article_like_iv);
            mTvTime = view.findViewById(R.id.home_article_time_tv);
            cardView.setOnClickListener(this);
        }

        public void onBindData(int position) {
            HomeItem item = homeItems.get(position);
            articleItem = (FeedArticleEntity) item;
            if (!TextUtils.isEmpty(articleItem.title)) {
                mTvContent.setText(Html.fromHtml(articleItem.title));
            }
            if (articleItem.collect) {
                mIvLike.setImageResource(R.drawable.icon_like);
            } else {
                mIvLike.setImageResource(R.drawable.icon_like_article_not_selected);
            }
            if (!TextUtils.isEmpty(articleItem.author)) {
                mTvAuthor.setText(articleItem.author);
            }
            if (!TextUtils.isEmpty(articleItem.chapterName)) {
                String projectClassifyName = articleItem.superChapterName + " / " + articleItem.chapterName;
                mTvChapterName.setText(projectClassifyName);
            }
            if (!TextUtils.isEmpty(articleItem.niceDate)) {
                mTvTime.setText(articleItem.niceDate);
            }
        }

        @Override
        public void onClick(View v) {
            startArticleDetailPage(articleItem);
        }

        private void startArticleDetailPage(FeedArticleEntity articleItem) {
            SmartPushRoute.startArticleDetailActivity(mContext, articleItem.id, articleItem.title, articleItem.link, articleItem.collect, false, false);
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

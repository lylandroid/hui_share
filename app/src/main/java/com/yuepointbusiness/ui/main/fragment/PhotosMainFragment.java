package com.yuepointbusiness.ui.main.fragment;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuepointbusiness.R;
import com.yuepointbusiness.bean.PhotoGirl;
import com.yuepointbusiness.ui.main.activity.MainActivity;
import com.yuepointbusiness.ui.news.activity.PhotosDetailActivity;
import com.yuepointbusiness.ui.news.contract.PhotoListContract;
import com.yuepointbusiness.ui.news.model.PhotosListModel;
import com.yuepointbusiness.ui.news.presenter.PhotosListPresenter;
import com.yuepointbusiness.common.base.BaseFragment;
import com.yuepointbusiness.common.commonwidget.LoadingTip;
import com.yuepointbusiness.common.commonwidget.NormalTitleBar;
import com.yuepointbusiness.widget.BaseWebView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * des:美容首页
 * Created by xsf
 * on 2016.09.11:49
 */
public class PhotosMainFragment extends BaseFragment<PhotosListPresenter, PhotosListModel> implements PhotoListContract.View, OnRefreshListener, OnLoadMoreListener {
  /*  @BindView(R.id.ntb)
    NormalTitleBar ntb;
    @BindView(R.id.irc)
    IRecyclerView irc;
    @BindView(R.id.loadedTip)
    LoadingTip loadedTip;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private CommonRecycleViewAdapter<PhotoGirl>adapter;
    private static int SIZE = 20;
    private int mStartPage = 1;*/

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web_view)
    BaseWebView mWebView;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutResource() {
//        return R.layout.act_photos_list;
        return R.layout.app_bar_video;
    }

    @Override
    public void initPresenter() {
//        mPresenter.setVM(this, mModel);
        mToolbar.setNavigationOnClickListener(v -> {
            if (mWebView.canGoBack()) {
                mWebView.goBack();// 返回前一个页面
            } else {
                if (((MainActivity) getActivity()).isFinish()) {
                    getActivity().finish();
                }
            }
        });
        mWebView.setWebViewClientListen(new BaseWebView.WebViewClientListener() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress < 100 && progressBar.getVisibility() != View.VISIBLE) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public void initView() {
        tvTitle.setText("美容");
        mWebView.loadUrl("http://www.shlzmr.com/index.html");
//        mWebView.setWebSettings();
        /*ntb.setTvLeftVisiable(false);
        ntb.setTitleText(getString(R.string.tab_2_title));
        adapter = new CommonRecycleViewAdapter<PhotoGirl>(getContext(), R.layout.item_photo) {
            @Override
            public void convert(ViewHolderHelper helper, final PhotoGirl photoGirl) {
                ImageView imageView = helper.getView(R.id.iv_photo);
                Glide.with(mContext).load(photoGirl.getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(com.yuepointbusiness.common.R.drawable.ic_image_loading)
                        .error(com.yuepointbusiness.common.R.drawable.ic_empty_picture)
                        .centerCrop().override(1090, 1090 * 3 / 4)
                        .crossFade().into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhotosDetailActivity.startAction(mContext, photoGirl.getUrl());
                    }
                });
            }
        };
        irc.setAdapter(adapter);
        irc.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        irc.setOnLoadMoreListener(this);
        irc.setOnRefreshListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irc.smoothScrollToPosition(0);
            }
        });
        mPresenter.getPhotosListDataRequest(SIZE, mStartPage);*/
    }

    @OnClick(R.id.btn_search)
    public void onClick(View v) {
        mWebView.reload();
    }

    @Override
    public void returnPhotosListData(List<PhotoGirl> photoGirls) {
        /*if (photoGirls != null) {
            mStartPage += 1;
            if (adapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                adapter.replaceAll(photoGirls);
            } else {
                if (photoGirls.size() > 0) {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    adapter.addAll(photoGirls);
                } else {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }*/
    }

    @Override
    public void showLoading(String title) {
        /*if (adapter.getPageBean().isRefresh())
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);*/
    }

    @Override
    public void stopLoading() {
        /* loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);*/
    }

    @Override
    public void showErrorTip(String msg) {
        /*if (adapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
            irc.setRefreshing(false);
        } else {
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }*/
    }

    @Override
    public void onRefresh() {
        /*adapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getPhotosListDataRequest(SIZE, mStartPage);*/
    }

    @Override
    public void onLoadMore(View loadMoreView) {
       /* adapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getPhotosListDataRequest(SIZE, mStartPage);*/
    }

    public BaseWebView getWebView() {
        return mWebView;
    }
}

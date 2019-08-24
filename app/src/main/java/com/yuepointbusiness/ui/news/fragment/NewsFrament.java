package com.yuepointbusiness.ui.news.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.animation.ScaleInAnimation;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.yuepointbusiness.R;
import com.yuepointbusiness.app.AppConstant;
import com.yuepointbusiness.bean.NewsSummary;
import com.yuepointbusiness.common.base.BaseFragment;
import com.yuepointbusiness.common.commonwidget.LoadingTip;
import com.yuepointbusiness.ui.main.activity.MainActivity;
import com.yuepointbusiness.ui.news.adapter.NewListAdapter;
import com.yuepointbusiness.ui.news.contract.NewsListContract;
import com.yuepointbusiness.ui.news.model.NewsListModel;
import com.yuepointbusiness.ui.news.presenter.NewsListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * des:新闻fragment
 * Created by xsf
 * on 2016.09.17:30
 */
public class NewsFrament extends BaseFragment<NewsListPresenter, NewsListModel> implements NewsListContract.View, OnRefreshListener, OnLoadMoreListener, View.OnClickListener {
    public static final String TEMP_CODE = "1319972";

    @BindView(R.id.irc)
    IRecyclerView irc;
    @BindView(R.id.loadedTip)
    LoadingTip loadedTip;
    private NewListAdapter newListAdapter;
    private List<NewsSummary> datas = new ArrayList<>();

    private String mNewsId;
    private String mNewsType;
    private int mStartPage = 0;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    private boolean isVisible;
    private View headView;

    @Override
    protected int getLayoutResource() {
        return R.layout.framents_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            mNewsId = getArguments().getString(AppConstant.NEWS_ID);
            mNewsType = getArguments().getString(AppConstant.NEWS_TYPE);
        }
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        datas.clear();
        newListAdapter = new NewListAdapter(getContext(), datas);
        newListAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(newListAdapter);
        addHeadView();
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if (newListAdapter.getSize() <= 0) {
            mStartPage = 0;
            mPresenter.getNewsListDataRequest(mNewsType, mNewsId, mStartPage);
        }
    }


    public void addHeadView() {
        if (headView == null) {
            headView = View.inflate(getActivity(), R.layout.recycler_new_header, null);
            headView.findViewById(R.id.tv_tab_education).setOnClickListener(this);
            headView.findViewById(R.id.tv_tab_medicine).setOnClickListener(this);
            headView.findViewById(R.id.tv_tab_cosmetology).setOnClickListener(this);
            headView.findViewById(R.id.tv_tab_money).setOnClickListener(this);
            irc.removeHeaderAllView();
            irc.addHeaderView(headView);
        }
    }

    @Override
    public void onClick(View v) {
       /* switch (v.getId()) {
            case R.id.tv_tab_education:
                break;
            case R.id.tv_tab_medicine:
                break;
            case R.id.tv_tab_cosmetology:
                break;
            case R.id.tv_tab_money:
                ((MainActivity) getActivity()).login(R.id.tv_tab_money);
                break;
        }*/
        ((MainActivity) getActivity()).login(v.getId());

    }


    @Override
    public void returnNewsListData(List<NewsSummary> newsSummaries) {
        if (newsSummaries != null) {
            mStartPage += 20;
            if (newListAdapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                newListAdapter.replaceAll(newsSummaries);
            } else {
                if (newsSummaries.size() > 0) {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    newListAdapter.addAll(newsSummaries);
                } else {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    /**
     * 返回顶部
     */
    @Override
    public void scrolltoTop() {
        irc.smoothScrollToPosition(0);
    }

    @Override
    public void onRefresh() {
        newListAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getNewsListDataRequest(mNewsType, mNewsId, mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        newListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getNewsListDataRequest(mNewsType, mNewsId, mStartPage);
    }

    @Override
    public void showLoading(String title) {
        if (newListAdapter.getPageBean().isRefresh()) {
            if (newListAdapter.getSize() <= 0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
        }
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if (newListAdapter.getPageBean().isRefresh()) {
            if (newListAdapter.getSize() <= 0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadedTip.setTips(msg);
            }
            irc.setRefreshing(false);
        } else {
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

}

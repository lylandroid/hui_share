package com.yuepointbusiness.ui.news.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.animation.ScaleInAnimation;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.yuepointbusiness.R;
import com.yuepointbusiness.app.AppConstant;
import com.yuepointbusiness.bean.NewsSummary;
import com.yuepointbusiness.ui.main.activity.IdInfoInputActivity;
import com.yuepointbusiness.ui.news.adapter.NewListAdapter;
import com.yuepointbusiness.ui.news.contract.NewsListContract;
import com.yuepointbusiness.ui.news.model.NewsListModel;
import com.yuepointbusiness.ui.news.presenter.NewsListPresenter;
import com.yuepointbusiness.common.base.BaseFragment;
import com.yuepointbusiness.common.commonutils.SPUtils;
import com.yuepointbusiness.common.commonutils.ToastUitl;
import com.yuepointbusiness.common.commonwidget.LoadingTip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

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
        switch (v.getId()) {
            case R.id.tv_tab_education:
                break;
            case R.id.tv_tab_medicine:
                break;
            case R.id.tv_tab_cosmetology:
                break;
            case R.id.tv_tab_money:
//                sendCode(getContext(), R.id.tv_tab_money);
                startActivity(new Intent(getActivity(), IdInfoInputActivity.class));
                break;
        }

    }

    public void sendCode(Context context, int resId) {
        if (TextUtils.isEmpty(SPUtils.getSharedStringData(context, AppConstant.MY_PHONE_KEY))) {
            RegisterPage page = new RegisterPage();
            //如果使用我们的ui，没有申请模板编号的情况下需传null
            page.setTempCode(TEMP_CODE);
            page.setRegisterCallback(new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    Log.i("MY_TAG", event + "    " + result + "  " + data);
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        // 处理成功的结果
                        HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                        String country = (String) phoneMap.get("country"); // 国家代码，如“86”
                        String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
                        SPUtils.setSharedStringData(getContext(), AppConstant.MY_PHONE_KEY, phone);
                        loginSuccess(resId);
                        // TODO 利用国家代码和手机号码进行后续的操作
                    } else {
                        // TODO 处理错误的结果
                        ToastUitl.showShort("登录失败，请稍候重试");
                    }
                }
            });
            page.show(context);
        } else {
            loginSuccess(resId);
        }

    }

    public void loginSuccess(int resId) {
        switch (resId) {
            case R.id.tv_tab_education:
                break;
            case R.id.tv_tab_medicine:
                break;
            case R.id.tv_tab_cosmetology:
                break;
            case R.id.tv_tab_money:
                inMoney();
                break;
        }
    }

    //理财相关逻辑处理
    public void inMoney() {

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

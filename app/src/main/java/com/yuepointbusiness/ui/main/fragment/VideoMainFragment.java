package com.yuepointbusiness.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;

import com.yuepointbusiness.R;
import com.yuepointbusiness.app.AppConstant;
import com.yuepointbusiness.bean.VideoChannelTable;
import com.yuepointbusiness.common.base.BaseFragment;
import com.yuepointbusiness.ui.main.activity.MainActivity;
import com.yuepointbusiness.ui.news.fragment.VideosFragment;
import com.yuepointbusiness.widget.BaseWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * des:美容首页
 * Created by xsf
 * on 2016.09.16:45
 */
public class VideoMainFragment extends BaseFragment {


    //    @BindView(R.id.tabs)
//    TabLayout tabs;
    /*@BindView(R.id.view_pager)
    ViewPager viewPager;*/
    @BindView(R.id.web_view)
    BaseWebView webView;
    /*@BindView(R.id.fab)
    FloatingActionButton fab;
    private BaseFragmentAdapter fragmentAdapter;*/
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutResource() {
        return R.layout.app_bar_video;
    }

    @Override
    public void initPresenter() {
        mToolbar.setNavigationOnClickListener(v -> {
            if (webView.canGoBack()) {
                webView.goBack();// 返回前一个页面
            } else {
                if (((MainActivity) getActivity()).isFinish()) {
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public void initView() {
        tvTitle.setText("医疗");
//        webView.setWebSettings();
//        webView.loadUrl("http://www.shlzmr.com/index.html");
        webView.loadUrl("http://m.51daifu.com/mr/yydt-30628.shtml");
        webView.setWebViewClientListen(new BaseWebView.WebViewClientListener() {
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
      /*  List<String> channelNames = new ArrayList<>();
        List<VideoChannelTable> videoChannelTableList = VideosChannelTableManager.loadVideosChannelsMine();
        List<Fragment> mNewsFragmentList = new ArrayList<>();
        for (int i = 0; i < videoChannelTableList.size(); i++) {
            channelNames.add(videoChannelTableList.get(i).getChannelName());
            mNewsFragmentList.add(createListFragments(videoChannelTableList.get(i)));
        }
        fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, channelNames);
        viewPager.setAdapter(fragmentAdapter);
        tabs.setupWithViewPager(viewPager);
        MyUtils.dynamicSetTabLayoutMode(tabs);
        setPageChangeListener();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRxManager.post(AppConstant.NEWS_LIST_TO_TOP, "");
            }
        });*/
    }

    private void setPageChangeListener() {
      /*  viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }

    @OnClick(R.id.btn_search)
    public void onClick(View v) {
        webView.reload();
    }

    private VideosFragment createListFragments(VideoChannelTable videoChannelTable) {
        VideosFragment fragment = new VideosFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.VIDEO_TYPE, videoChannelTable.getChannelId());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public BaseWebView getWebView() {
        return webView;
    }
}

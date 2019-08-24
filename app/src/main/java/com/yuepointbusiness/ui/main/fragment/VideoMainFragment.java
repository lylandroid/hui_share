package com.yuepointbusiness.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.appcompat.widget.Toolbar;

import com.yuepointbusiness.R;
import com.yuepointbusiness.app.AppConstant;
import com.yuepointbusiness.bean.VideoChannelTable;
import com.yuepointbusiness.common.base.BaseFragment;
import com.yuepointbusiness.ui.main.activity.MainActivity;
import com.yuepointbusiness.ui.news.fragment.VideosFragment;
import com.yuepointbusiness.widget.BaseWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * des:医疗首页
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
//        webView.setWebSettings();
        webView.loadUrl("http://www.shlzmr.com/index.html");
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

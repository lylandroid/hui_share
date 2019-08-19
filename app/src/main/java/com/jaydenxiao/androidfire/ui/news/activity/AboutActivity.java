package com.jaydenxiao.androidfire.ui.news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.BindView;

import static com.jaydenxiao.androidfire.R.layout.act_about;

/**
 * des:关于
 * Created by xsf
 * on 2016.09.16:57
 */
public class AboutActivity extends BaseActivity {


    @BindView(R.id.news_detail_photo_iv)
    ImageView newsDetailPhotoIv;
    @BindView(R.id.mask_view)
    View maskView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.news_detail_from_tv)
    TextView newsDetailFromTv;
    @BindView(R.id.tv_code_des)
    TextView tvCodeDes;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private String mShareLink;

    /**
     * 入口
     *
     * @param mContext
     */
    public static void startAction(Context mContext) {
        Intent intent = new Intent(mContext, AboutActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return act_about;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        SetTranslanteBar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });
        toolbar.setTitle(getString(R.string.app_name));
        toolbarLayout.setTitle(getString(R.string.app_name));
        //分享
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareLink == null) {
                    mShareLink = "";
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_contents, getString(R.string.app_name), mShareLink));
                startActivity(Intent.createChooser(intent, getTitle()));
            }
        });

    }
}

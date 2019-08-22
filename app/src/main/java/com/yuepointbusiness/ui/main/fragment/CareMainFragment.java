package com.yuepointbusiness.ui.main.fragment;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yuepointbusiness.R;
import com.yuepointbusiness.app.AppApplication;
import com.yuepointbusiness.app.AppConstant;
import com.yuepointbusiness.common.commonutils.SPUtils;
import com.yuepointbusiness.ui.main.activity.MainActivity;
import com.yuepointbusiness.ui.news.activity.AboutActivity;
import com.yuepointbusiness.ui.zone.activity.CircleZoneActivity;
import com.yuepointbusiness.common.base.BaseFragment;
import com.yuepointbusiness.common.commonutils.ImageLoaderUtils;
import com.yuepointbusiness.common.commonwidget.WaveView;
import com.yuepointbusiness.common.daynightmodeutils.ChangeModeController;
import com.yuepointbusiness.widget.CustomDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * des:关注主页
 * Created by xsf
 * on 2016.09.17:07
 */
public class CareMainFragment extends BaseFragment {
    @BindView(R.id.ll_friend_zone)
    LinearLayout llFriendZone;
    @BindView(R.id.wave_view)
    WaveView waveView;
    @BindView(R.id.img_logo)
    ImageView imgLogo;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_care_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        //设置头像跟着波浪背景浮动
        ImageLoaderUtils.displayRound(getContext(), imgLogo, R.drawable.bgkobe);
        changeLogoColorState();
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2, -2);
        lp.gravity = Gravity.CENTER;
        waveView.setOnWaveAnimationListener(y -> {
            lp.setMargins(0, 0, 0, (int) y + 2);
            imgLogo.setLayoutParams(lp);
        });
    }

    @OnClick(R.id.ll_friend_zone)
    public void friendZone() {
        CircleZoneActivity.startAction(getContext());
    }

    @OnClick(R.id.ll_daynight_toggle)
    public void dayNightToggle() {
        ChangeModeController.toggleThemeSetting(getActivity());
    }

    @OnClick(R.id.ll_daynight_about)
    public void about() {
        AboutActivity.startAction(getContext());
    }


    @OnClick(R.id.img_logo)
    public void exitLoginDialog() {
        if (TextUtils.isEmpty(SPUtils.getSharedStringData(getActivity(), AppConstant.MY_PHONE_KEY))) {
            ((MainActivity) getActivity()).login(R.id.tv_tab_money);
        } else {
            CustomDialog dialog = new CustomDialog(getActivity(), "是否确定退出当前帐号", null, "退出", "取消");
            dialog.setBtnPositiveListener(v -> {
                SPUtils.setSharedStringData(getActivity(), AppConstant.MY_PHONE_KEY, "");
                Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_LONG).show();
                changeLogoColorState();
                dialog.dismiss();
            });
            dialog.setBtnCancelListener(v -> dialog.dismiss());
            dialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        changeLogoColorState();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            //如果页面是空，当前页面显示则加载数据
            changeLogoColorState();
        }
    }

    public void changeLogoColorState() {
        if (TextUtils.isEmpty(SPUtils.getSharedStringData(getContext(), AppConstant.MY_PHONE_KEY))) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            imgLogo.setColorFilter(filter);
//            imgLogo.setColorFilter(0xFF666666);
        } else {
//            imgLogo.setColorFilter(0x00000000);
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(1);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            imgLogo.setColorFilter(filter);
        }
    }

}

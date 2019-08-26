package com.yuepointbusiness.ui.main.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yuepointbusiness.R;
import com.yuepointbusiness.app.AppConstant;
import com.yuepointbusiness.bean.TabEntity;
import com.yuepointbusiness.common.base.BaseActivity;
import com.yuepointbusiness.common.baseapp.AppConfig;
import com.yuepointbusiness.common.commonutils.LogUtils;
import com.yuepointbusiness.common.daynightmodeutils.ChangeModeController;
import com.yuepointbusiness.ui.main.fragment.CareMainFragment;
import com.yuepointbusiness.ui.main.fragment.NewsMainFragment;
import com.yuepointbusiness.ui.main.fragment.PhotosMainFragment;
import com.yuepointbusiness.ui.main.fragment.VideoMainFragment;
import com.yuepointbusiness.ui.tab.BeautifulActivity;
import com.yuepointbusiness.ui.tab.DoctorActivity;
import com.yuepointbusiness.ui.tab.TeachActivity;
import com.yuepointbusiness.utils.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import cn.hugeterry.updatefun.config.UpdateKey;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import io.reactivex.disposables.Disposable;
import rx.functions.Action1;

/**
 * des:主界面
 * Created by xsf
 * on 2016.09.15:32
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private String[] mTitles = {"首页", "医疗", "美容", "关注"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal, R.mipmap.ic_girl_normal, R.mipmap.ic_video_normal, R.mipmap.ic_care_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected, R.mipmap.ic_girl_selected, R.mipmap.ic_video_selected, R.mipmap.ic_care_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private NewsMainFragment newsMainFragment;
    private PhotosMainFragment photosMainFragment;
    private VideoMainFragment videoMainFragment;
    private CareMainFragment careMainFragment;
    private static int tabLayoutHeight;
    private RxPermissions rxPermissions;
    private Disposable subscribe;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                com.yuepointbusiness.common.R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        //此处填上在http://fir.im/注册账号后获得的API_TOKEN以及APP的应用ID
        UpdateKey.API_TOKEN = AppConfig.API_FIRE_TOKEN;
        UpdateKey.APP_ID = AppConfig.APP_FIRE_ID;
        //如果你想通过Dialog来进行下载，可以如下设置
//        UpdateKey.DialogOrNotification=UpdateKey.WITH_DIALOG;
//        UpdateFunGO.init(this);
        //初始化菜单
        initTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //切换daynight模式要立即变色的页面
        ChangeModeController.getInstance().init(this, R.attr.class);
        super.onCreate(savedInstanceState);
        rxPermissions = new RxPermissions(this);
        //初始化frament
        initFragment(savedInstanceState);
        tabLayout.measure(0, 0);
        tabLayoutHeight = tabLayout.getMeasuredHeight();
        //监听菜单显示或隐藏
        mRxManager.on(AppConstant.MENU_SHOW_HIDE, new Action1<Boolean>() {

            @Override
            public void call(Boolean hideOrShow) {
                startAnimation(hideOrShow);
            }
        });
        requestPermissions();
    }

    public void requestPermissions() {
        if (!rxPermissions.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)
                || !rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            subscribe = rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(granted -> {
                if (granted) { // Always tr
                    // ue pre-M
                    // I can control the camera now
                } else {
                    // Oups permission denied
                }
            });
        }
        if (Build.VERSION.SDK_INT >= 23) {
            int readPhone = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int receiveSms = checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            int readContacts = checkSelfPermission(Manifest.permission.READ_CONTACTS);
            int readSdcard = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            int requestCode = 0;
            ArrayList<String> permissions = new ArrayList<String>();
            if (readPhone != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 0;
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (receiveSms != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 1;
                permissions.add(Manifest.permission.RECEIVE_SMS);
            }
            if (readContacts != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 2;
                permissions.add(Manifest.permission.READ_CONTACTS);
            }
            if (readSdcard != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 3;
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (requestCode > 0) {
                String[] permission = new String[permissions.size()];
                this.requestPermissions(permissions.toArray(permission), requestCode);
                return;
            }
        }
    }


    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 1;
        if (savedInstanceState != null) {
            newsMainFragment = (NewsMainFragment) getSupportFragmentManager().findFragmentByTag("newsMainFragment");
            photosMainFragment = (PhotosMainFragment) getSupportFragmentManager().findFragmentByTag("photosMainFragment");
            videoMainFragment = (VideoMainFragment) getSupportFragmentManager().findFragmentByTag("videoMainFragment");
            careMainFragment = (CareMainFragment) getSupportFragmentManager().findFragmentByTag("careMainFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            newsMainFragment = new NewsMainFragment();
            photosMainFragment = new PhotosMainFragment();
            videoMainFragment = new VideoMainFragment();
            careMainFragment = new CareMainFragment();

            transaction.add(R.id.fl_body, newsMainFragment, "newsMainFragment");
            transaction.add(R.id.fl_body, photosMainFragment, "photosMainFragment");
            transaction.add(R.id.fl_body, videoMainFragment, "videoMainFragment");
            transaction.add(R.id.fl_body, careMainFragment, "careMainFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        LogUtils.logd("主页菜单position" + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                StatusBarUtil.setColor(this, 0xFFFA7C20);
                transaction.hide(photosMainFragment);
                transaction.hide(videoMainFragment);
                transaction.hide(careMainFragment);
                transaction.show(newsMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //医疗
            case 1:
                StatusBarUtil.setColor(this, 0xFFFFFFFF);
                transaction.hide(newsMainFragment);
                transaction.hide(photosMainFragment);
                transaction.hide(careMainFragment);
                transaction.show(videoMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //美容
            case 2:
                StatusBarUtil.setColor(this, 0xFFFFFFFF);
                transaction.hide(newsMainFragment);
                transaction.hide(videoMainFragment);
                transaction.hide(careMainFragment);
                transaction.show(photosMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //关注
            case 3:
                StatusBarUtil.setColor(this, 0xFFFA7C20);
                transaction.hide(newsMainFragment);
                transaction.hide(photosMainFragment);
                transaction.hide(videoMainFragment);
                transaction.show(careMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    /**
     * 菜单显示隐藏动画
     *
     * @param showOrHide
     */
    private void startAnimation(boolean showOrHide) {
       /* final ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
        ValueAnimator valueAnimator;
        ObjectAnimator alpha;
        if (!showOrHide) {
            valueAnimator = ValueAnimator.ofInt(tabLayoutHeight, 0);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 1, 0);
        } else {
            valueAnimator = ValueAnimator.ofInt(0, tabLayoutHeight);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 0, 1);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                tabLayout.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator, alpha);
        animatorSet.start();*/
    }

    /**
     * 监听全屏视频时返回键
     */
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (tabLayout.getCurrentTab() == 1) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && photosMainFragment != null && photosMainFragment.getWebView() != null
                        && photosMainFragment.getWebView().canGoBack()) {
                    photosMainFragment.getWebView().goBack();
                    return true;
                }
            }
        } else if (tabLayout.getCurrentTab() == 2) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && videoMainFragment != null && videoMainFragment.getWebView() != null
                        && videoMainFragment.getWebView().canGoBack()) {
                    videoMainFragment.getWebView().goBack();
                    return true;
                }
            }
        }
        if (!isFinish()) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        LogUtils.loge("onSaveInstanceState进来了1");
        if (tabLayout != null) {
            LogUtils.loge("onSaveInstanceState进来了2");
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
        }
    }

    public void login(int resId) {
        /*if (TextUtils.isEmpty(SPUtils.getSharedStringData(this, AppConstant.MY_PHONE_KEY))) {
            RegisterPage page = new RegisterPage();
            //如果使用我们的ui，没有申请模板编号的情况下需传null
            page.setTempCode(*//*TEMP_CODE*//*null);
            page.setRegisterCallback(new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    Log.i("MY_TAG", event + "    " + result + "  " + data);
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        // 处理成功的结果
                        HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                        String country = (String) phoneMap.get("country"); // 国家代码，如“86”
                        String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
                        SPUtils.setSharedStringData(MainActivity.this, AppConstant.MY_PHONE_KEY, phone);
                        loginSuccess(resId);
                    } else {
                        showShortToast("登录失败，请稍候重试");
                    }
                }
            });
            page.show(this);
        } else {
            loginSuccess(resId);
        }*/
        loginSuccess(resId);

    }

    public void loginSuccess(int resId) {
        switch (resId) {
            case R.id.tv_tab_education:
                startActivity(TeachActivity.class);
                break;
            case R.id.tv_tab_medicine:
                startActivity(DoctorActivity.class);
                break;
            case R.id.tv_tab_cosmetology:
                startActivity(BeautifulActivity.class);
                break;
            case R.id.tv_tab_money:
                inMoney();
                break;
        }
    }

    //理财相关逻辑处理
    public void inMoney() {
       /* rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(granted -> {
            if (granted) { // Always tr
                // ue pre-M
                // I can control the camera now
                startActivity(new Intent(this, IdInfoInputActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "请允许权限", Toast.LENGTH_SHORT).show();
            }
        });*/
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("http://47.95.227.133:10443/?inApp=1");
        intent.setData(content_url);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        UpdateFunGO.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        UpdateFunGO.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!subscribe.isDisposed()) {
            subscribe.dispose();
        }
        ChangeModeController.onDestory();
    }


    long lastTime;

    public boolean isFinish() {
        long currTime = System.currentTimeMillis();
        if (currTime - lastTime < 2000) {
            return true;
        } else {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            lastTime = currTime;
            return false;
        }
    }
}

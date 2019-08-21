package com.yuepointbusiness.app;

import com.mob.MobSDK;
import com.yuepointbusiness.BuildConfig;
import com.yuepointbusiness.common.baseapp.BaseApplication;
import com.yuepointbusiness.common.commonutils.LogUtils;

/**
 * APPLICATION
 */
public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化logger
        LogUtils.logInit(BuildConfig.LOG_DEBUG);
        MobSDK.init(this);
    }
}

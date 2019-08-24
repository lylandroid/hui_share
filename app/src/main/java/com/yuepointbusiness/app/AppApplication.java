package com.yuepointbusiness.app;

import android.os.Handler;
import com.yuepointbusiness.BuildConfig;
import com.yuepointbusiness.common.baseapp.BaseApplication;
import com.yuepointbusiness.common.commonutils.LogUtils;

/**
 * APPLICATION
 */
public class AppApplication extends BaseApplication {
    static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        //初始化logger
        LogUtils.logInit(BuildConfig.LOG_DEBUG);
    }

    public static Handler getHandler() {
        return mHandler;
    }
}

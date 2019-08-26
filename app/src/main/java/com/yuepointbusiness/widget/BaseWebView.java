package com.yuepointbusiness.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * common webView
 * <p>
 * Created by zhangyan42@baidu.com on 2017/5/19.
 */
@SuppressLint("SetJavaScriptEnabled")
public class BaseWebView extends WebView {

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public BaseWebView(Context context) {
        super(context);
        init(context);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void init(Context context) {
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        /*if (Build.VERSION.SDK_INT < 19) {
            removeJavascriptInterface("searchBoxJavaBridge_");
        }*/

        WebSettings localWebSettings = this.getSettings();
        try {
            // 禁用file协议,http://www.tuicool.com/articles/Q36ZfuF, 防止Android WebView File域攻击
            localWebSettings.setAllowFileAccess(false);
            localWebSettings.setSupportZoom(false);
            localWebSettings.setBuiltInZoomControls(false);
            localWebSettings.setUseWideViewPort(true);
            localWebSettings.setDomStorageEnabled(true);
            localWebSettings.setLoadWithOverviewMode(true);
            localWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            localWebSettings.setPluginState(WebSettings.PluginState.ON);

            localWebSettings.setAppCacheMaxSize(1024 * 1024 * 8);
            // 启用数据库
            localWebSettings.setDatabaseEnabled(true);
            // 设置定位的数据库路径
            String dir = context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
            localWebSettings.setGeolocationDatabasePath(dir);
            localWebSettings.setGeolocationEnabled(true);
            localWebSettings.setJavaScriptEnabled(true);
            localWebSettings.setSavePassword(false);

            localWebSettings.setAppCacheEnabled(true);    //开启H5(APPCache)缓存功能
            localWebSettings.setBlockNetworkImage(true);

            requestFocus();
            String agent = localWebSettings.getUserAgentString();

            localWebSettings.setUserAgentString(agent);
            // setCookie(context, ".baidu.com", bdussCookie);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                ((Activity) getContext()).startActivityForResult(Intent.createChooser(intent, "File Chooser"), 0);
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (webViewClientListen != null) {
                    webViewClientListen.onProgressChanged(view, newProgress);
                }

            }
        });
        this.setWebViewClient(new BridgeWebViewClient());
    }


    private void setCookie(Context context, String domain, String sessionCookie) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (sessionCookie != null) {
            // delete old cookies
            cookieManager.removeSessionCookie();
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cookieManager.setCookie(domain, sessionCookie);

        CookieSyncManager.createInstance(context);
        CookieSyncManager.getInstance().sync();
    }

    public void setDownloadListener(DownloadListener listener) {
        super.setDownloadListener(listener);
    }

    private WebViewClientListener webViewClientListen;

    public void setWebViewClientListen(WebViewClientListener webViewClientListen) {
        this.webViewClientListen = webViewClientListen;
    }

    /**
     * 枚举网络加载返回状态 STATUS_FALSE:false
     * STATUS_TRUE:true
     * STATUS_UNKNOW:不知道
     * NET_UNKNOWN:未知网络
     */
    public enum LoadingWebStatus {
        STATUS_FALSE, STATUS_TRUE, STATUS_UNKNOW
    }

    public static abstract class WebViewClientListener {
        public LoadingWebStatus shouldOverrideUrlLoading(WebView view, String url) {
            return LoadingWebStatus.STATUS_UNKNOW;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }

        public void onPageFinished(WebView view, String url) {
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        }

        public void onProgressChanged(WebView view, int newProgress) {
        }
    }

    public class BridgeWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            getSettings().setBlockNetworkImage(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (null != webViewClientListen) {
                webViewClientListen.onPageFinished(view, url);
            }
            getSettings().setBlockNetworkImage(false);
            if (!getSettings().getLoadsImagesAutomatically()) {
                //设置wenView加载图片资源
                getSettings().setLoadsImagesAutomatically(true);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String
                failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (null != webViewClientListen) {
                webViewClientListen.onReceivedError(view, errorCode, description, failingUrl);
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // 当发生证书认证错误时，采用默认的处理方法handler.cancel()，停止加载问题页面
            handler.proceed();
//            handler.cancel();
//            handler.handleMessage(null);
        }
    }
}
package com.yuepointbusiness.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ta.utdid2.android.utils.StringUtils;
import com.weavey.utils.ScreenSizeUtils;
import com.yuepointbusiness.R;
import com.yuepointbusiness.app.AppApplication;
import com.yuepointbusiness.utils.DensityUtil;


/**
 * Created by jy at 18/8/30.
 * 通用对话框
 */

public class CustomDialog extends Dialog implements View.OnClickListener {
    /**
     * padding left,right in dp
     */
    private int margin = 50;
    private View rootView;
    private Context context;
    private TextView title;
    private TextView content;
    private FrameLayout contentLayout;
    private LinearLayout operationLayout;
    private View dividerLine;
    private TextView confirmBtn;
    private TextView cancelBtn;

    public CustomDialog(Context context) {
        super(context, R.style.customDialog);
        this.context = context;
        //加载布局文件
        rootView = getLayoutInflater().inflate(R.layout.dialog_custom, null);
        setContentView(rootView);
        //初始化view
        initView();
    }

    public CustomDialog(@NonNull Context context, String titleStr, String contentStr, String confirmBtnStr, String cancelBtnStr) {
        this(context, titleStr, contentStr, confirmBtnStr, cancelBtnStr, 50);
    }

    public CustomDialog(@NonNull Context context, String titleStr, String contentStr, String confirmBtnStr, String cancelBtnStr, int margin) {
        super(context, R.style.customDialog);
        this.context = context;
        this.margin = margin;
        //加载布局文件
        rootView = getLayoutInflater().inflate(R.layout.dialog_custom, null);
        setContentView(rootView);
        //初始化view
        initView();
        //标题一定有
        title.setText(titleStr);
        //内容也许为空
        if (StringUtils.isEmpty(contentStr)) {
            contentLayout.setVisibility(View.GONE);
        } else {
            contentLayout.setVisibility(View.VISIBLE);
            content.setText(contentStr);
        }
        //确定按钮一定有，取消按钮不一定
        confirmBtn.setText(confirmBtnStr);
        if (TextUtils.isEmpty(cancelBtnStr)) {
            setHideCancelButton(true);
        } else {
            setHideCancelButton(false);
            cancelBtn.setText(cancelBtnStr);
        }

    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    /**
     * 初始化view
     */
    private void initView() {
        title = rootView.findViewById(R.id.title);
        content = rootView.findViewById(R.id.content);
        contentLayout = rootView.findViewById(R.id.contentLayout);
        confirmBtn = rootView.findViewById(R.id.confirmBtn);
        dividerLine = rootView.findViewById(R.id.dividerLine);
        cancelBtn = rootView.findViewById(R.id.cancelBtn);

        operationLayout = rootView.findViewById(R.id.operationLayout);
        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    public void setContentLayout(View contentLayout) {
        if (this.contentLayout != null && contentLayout != null) {
            this.contentLayout.removeAllViews();
            this.contentLayout.addView(contentLayout);
            this.contentLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 确认按钮、取消按钮的单击事件
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancelBtn || v.getId() == R.id.confirmBtn) {
            dismiss();
        }
    }

    /**
     * 设置内容
     *
     * @param resId
     */
    public void setContent(int resId) {
        setContent(context.getString(resId));
    }

    /**
     * 设置内容
     *
     * @param content
     */
    public void setContent(String content) {
        if (this.contentLayout.getVisibility() != View.VISIBLE) {
            this.contentLayout.setVisibility(View.VISIBLE);
        }
        this.content.setText(content);
    }

    /**
     * 设置确定按钮文字
     *
     * @param resId
     */
    public void setPositiveBtnText(int resId) {
        setPositiveBtnText(context.getString(resId));
    }

    /**
     * 设置确定按钮文字
     *
     * @param text
     */
    public void setPositiveBtnText(String text) {
        confirmBtn.setText(text);
    }

    /**
     * 设置取消按钮文字
     *
     * @param resId
     */
    public void setCancelBtnText(int resId) {
        setCancelBtnText(context.getString(resId));
    }

    /**
     * 设置取消按钮文字
     *
     * @param text
     */
    public void setCancelBtnText(String text) {
        cancelBtn.setText(text);
    }

    /**
     * 确认监听
     *
     * @param listener
     */
    public void setBtnPositiveListener(View.OnClickListener listener) {
        confirmBtn.setOnClickListener(listener);
    }

    /**
     * 取消监听
     *
     * @param listener
     */
    public void setBtnCancelListener(View.OnClickListener listener) {
        cancelBtn.setOnClickListener(listener);
    }

    /**
     * 显示对话款调用的方法
     */
    @Override
    public void show() {
        dialogAutoWidth(this);
        super.show();
    }

    public void show(int width) {
        this.getWindow().getAttributes().width = DensityUtil.dp2px(AppApplication.getAppContext(), width);
        super.show();
    }

    /**
     * 设置标题
     */
    public void setTitle(int resId) {
        title.setVisibility(View.VISIBLE);
        setTitle(context.getString(resId));
    }

    /**
     * 设置标题
     *
     * @param text
     */
    public void setTitle(String text) {
        title.setVisibility(View.VISIBLE);
        title.setText(text);
    }

    public void setTitle(Spannable spannable) {
        title.setText(spannable);
    }

    /**
     * 自动设置dialog宽度
     *
     * @param dialog
     */
    private void dialogAutoWidth(Dialog dialog) {
        if (dialog == null) {
            return;
        }
        dialogAutoWidth(dialog.getWindow());
    }

    /**
     * 自动设置window宽度
     *
     * @param window
     */
    private void dialogAutoWidth(Window window) {
        if (window == null) {
            return;
        }
        int screenWidth = ScreenSizeUtils.getInstance(AppApplication.getAppContext()).getScreenWidth();
        int screenHeight = ScreenSizeUtils.getInstance(AppApplication.getAppContext()).getScreenHeight();
        int width = (Math.min(screenWidth, screenHeight) - DensityUtil.dp2px(AppApplication.getAppContext(), getDialogWidth()));
        window.getAttributes().width = width;
    }

    public int getDialogWidth() {
        return margin * 2;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    /**
     * 只显示确定按钮的处理
     *
     * @param isHideCancelButton
     */
    public void setHideCancelButton(boolean isHideCancelButton) {
        if (isHideCancelButton) {
            cancelBtn.setVisibility(View.GONE);
            dividerLine.setVisibility(View.GONE);
            confirmBtn.setBackgroundResource(R.drawable.shape_custom_dialog_button_bg);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) confirmBtn.getLayoutParams();
            params.setMargins(DensityUtil.dp2px(AppApplication.getAppContext(), 16), 0,
                    DensityUtil.dp2px(AppApplication.getAppContext(), 16), 0);
        } else {
            cancelBtn.setVisibility(View.VISIBLE);
            dividerLine.setVisibility(View.VISIBLE);
            confirmBtn.setBackgroundResource(R.drawable.shape_custom_dialog_button_bg);
        }
    }

    public void setHideBottom(boolean isHide) {
        operationLayout.setVisibility(isHide ? View.GONE : View.VISIBLE);
    }

    public void setContent(Spannable spannable) {
        this.content.setText(spannable);
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }
}

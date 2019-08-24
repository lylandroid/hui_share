package com.yuepointbusiness.ui.tab;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yuepointbusiness.R;
import com.yuepointbusiness.widget.BaseWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lyl on 2019/8/21.
 * Description: 输入身份信息
 */
public class IdInfoInputActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.web_view)
    BaseWebView webView;
  /*  @BindView(R.id.toolbar)
    Toolbar toolbar;*/

    /*@BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_id_card)
    EditText mEtIdCard;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_id_card_info);
        unbinder = ButterKnife.bind(this);

       /* toolbar.setNavigationOnClickListener(v -> {

        });*/

//        webView.setDefaultWebSettings(webView);
//        webView.setWebSettings();
        webView.loadUrl("http://47.95.227.133:10443/?inApp=1");
//        webView.loadUrl("http://47.95.227.133:10443/login");
       /* mToolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        String mobile = SPUtils.getSharedStringData(this, AppConstant.MY_PHONE_KEY);
        if (!TextUtils.isEmpty(mobile)) {
            mEtPhone.setText(mobile);
        }*/
    }

    /*@OnClick(R.id.btn_next)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                check();
                break;
        }
    }

    private void check() {
        String phone = mEtPhone.getText().toString();
        String name = mEtName.getText().toString();
        String idCard = mEtIdCard.getText().toString();

        if (TextUtils.isEmpty(phone) || phone.length() < 11) {
            showToast("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            showToast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(idCard)) {
            showToast("请输入身份证号码");
            return;
        }
        try {
            long startTime = System.currentTimeMillis();
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt("mobile", phone);
            jsonObject.putOpt("name", name);
            jsonObject.putOpt("idCard", idCard);
            jsonObject.putOpt("requestTime", startTime);
            http(phone);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("服务忙，请稍后重试!");
        }

//        http();
    }

    public void http(String mobile) {
        try {
            long timestamp = System.currentTimeMillis();
            String encMobile = RSAUtils.encryptByPublicKey(mobile + timestamp, AppConstant.RSA_PUB_KEY);
            String sign = RSAUtils.signByPrivateKey(encMobile, AppConstant.RSA_PRIVATE_KEY, "utf-8");
            boolean verifyTrue = RSAUtils.verifyByPublicKey(encMobile, AppConstant.RSA_MY_PUB_KEY, sign, "UTF-8");//验签
//            Log.i("MY_TAG", "encMobile: " + encMobile);
//            Log.i("MY_TAG", "sign: " + sign);
//            Log.i("MY_TAG", "verifyTrue: " + verifyTrue);
            AttestationSdkApi.startAttestation(this, encMobile, AppConstant.CHANNEL_ID, sign, userid -> {
                Log.e("MY_TAG", "onUser(String userid) : " + userid);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public void showToast(String txt) {
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

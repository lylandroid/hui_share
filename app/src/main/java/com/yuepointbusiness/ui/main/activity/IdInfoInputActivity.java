package com.yuepointbusiness.ui.main.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yuepointbusiness.R;
import com.yuepointbusiness.app.AppConstant;
import com.yuepointbusiness.utils.RSAUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lyl on 2019/8/21.
 * Description: 输入身份信息
 */
public class IdInfoInputActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_id_card)
    EditText mEtIdCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_id_card_info);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_next)
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

    public void http(String json) {
        Log.e("MY_TAG", "http json: " + json);
        String sign = getSign(json);
       /* AttestationSdkApi.startAttestation(MainActivity.this, data,edChanneID,sign,new AttestationSdkManager.OnUserClickLister() {
            @Override
            public void onUser(String userid) {
                Log.d("TAG", "userid===" + userid);
            }
        });*/


    }


    public String getSign(String json) {
        try {
            String encryptData = RSAUtils.encryptByPublicKey(json + System.currentTimeMillis(), AppConstant.RSA_PUB_KEY);//加密，输出已经进行Base64 encode处理
            String signData = RSAUtils.signByPrivateKey(encryptData, AppConstant.RSA_PRIVATE_KEY, "UTF-8");//加签，输出已经进行Base64 encode处理
//            Log.e("MY_TAG", "encryptData: " + encryptData);
//            Log.e("MY_TAG", "signData: " + signData);
//
//            Log.i("MY_TAG", "私钥解密: " + RSAUtils.decryptByPrivateKey(encryptData, AppConstant.RSA_PRIVATE_KEY));
////            Log.i("MY_TAG", "私钥解密: " + RSAUtils.decryptRSAToString(encryptData, AppConstant.RSA_PRIVATE_KEY));
//
//            Log.e("MY_TAG", "公钥数字签名的校验: " + RSAUtils.verifyByPublicKey(json, AppConstant.RSA_PUB_KEY, signData, "UTF-8"));
            return signData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public void showToast(String txt) {
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

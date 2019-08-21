package com.yuepointbusiness.ui.main.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yuepointbusiness.R;

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
                submit();
                break;
        }
    }

    private void submit() {
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
        long startTime = System.currentTimeMillis();

//        http();
    }

    public void http(String json) {

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

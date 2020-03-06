package com.hailong.starttalkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.qunar.im.ui.sdk.QIMSdk;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etNavUrl)
    EditText etNavUrl;
    @BindView(R.id.btnNavUrl)
    Button btnNavUrl;
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnNavUrl, R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnNavUrl:
                if (TextUtils.isEmpty(etNavUrl.getEditableText().toString().trim())) return;
                QIMSdk.getInstance().setNavigationUrl(etNavUrl.getEditableText().toString().trim());
                toast("导航地址配置成功");
                break;
            case R.id.btnLogin:
                if (TextUtils.isEmpty(etAccount.getEditableText().toString().trim()) || TextUtils.isEmpty(etPwd.getEditableText().toString().trim()))
                    return;
                QIMSdk.getInstance().login(etAccount.getEditableText().toString().trim(), etPwd.getEditableText().toString().trim(), new QIMSdk.LoginStatesListener() {
                    @Override
                    public void isScuess(boolean isScuess, String message) {
                        if (isScuess) {
                            toast("登录成功");
                            AndPermission.with(LoginActivity.this)
                                    .runtime()
                                    .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                                    .onGranted(permissions -> {
                                        // Storage permission are allowed.
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    })
                                    .onDenied(permissions -> {
                                        // Storage permission are not allowed.
                                    })
                                    .start();
                        } else {
                            toast(message);
                        }
                    }
                });
                break;
        }
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

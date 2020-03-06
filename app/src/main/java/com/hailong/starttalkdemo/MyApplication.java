package com.hailong.starttalkdemo;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.qunar.im.ui.sdk.QIMSdk;
import com.xuexiang.xui.XUI;

/**
 * Created by ZuoHailong on 2020/3/4.
 */
public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        QIMSdk.getInstance().init(this);
        QIMSdk.getInstance().openDebug();

        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志
    }
}

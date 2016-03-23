package com.yxhuang.carapplication.car.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.yxhuang.carapplication.car.exception.BaseExceptionHandler;
import com.yxhuang.carapplication.car.exception.LocalFileHandler;

/**
 * Created by Administrator on 2015/10/10.
 */
public  abstract class BaseApplication extends Application {
    public static final String TAG = "Application";
    public static Context applicationContext;

    // 以键值对的形式存储用户名和密码
    public SharedPreferences mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();

        if (getDefaultUncaughtExceptionHandler() == null){
            Thread.setDefaultUncaughtExceptionHandler(new LocalFileHandler(applicationContext));
        } else {
            Thread.setDefaultUncaughtExceptionHandler(getDefaultUncaughtExceptionHandler());
        }

    }


    public abstract BaseExceptionHandler getDefaultUncaughtExceptionHandler();

}

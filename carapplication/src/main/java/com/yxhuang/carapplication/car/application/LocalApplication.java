package com.yxhuang.carapplication.car.application;

import android.util.DisplayMetrics;

import com.yxhuang.carapplication.car.exception.BaseExceptionHandler;
import com.yxhuang.carapplication.car.exception.LocalFileHandler;
import com.yxhuang.carapplication.car.utils.DbUtils;
import com.yxhuang.carapplication.car.utils.HttpUtils;
import com.yxhuang.carapplication.car.utils.JFileKit;

import java.io.File;

/**
 * Created by Administrator on 2015/10/10.
 */
public class LocalApplication extends BaseApplication {

    private static LocalApplication instance;
    private DbUtils dbUtils = null;
    private HttpUtils httpUtils = null;

    // 当前屏幕的宽高
    public int screenW = 0;
    public int screenH = 0;

    public static LocalApplication getInstance(){
        if (instance == null){
            instance = new LocalApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化数据库
//        dbUtils = DbUtils.create(LocalDaoConfig.getInstance(getApplicationContext()));


        // 初始化网络模块
        httpUtils = new HttpUtils();

        // 创建APP崩溃日志目录
        File appFolder = new File(JFileKit.getDiskCacheDir(this) + "/log");
        if (!appFolder.exists()) appFolder.mkdirs();
        instance = this;

        // 得到屏幕的宽高
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenH = dm.heightPixels;
        screenW = dm.widthPixels;
    }

    @Override
    public BaseExceptionHandler getDefaultUncaughtExceptionHandler() {
        return new LocalFileHandler(applicationContext);
    }
}

package com.yxhuang.carapplication.car.exception;

import java.text.DateFormat;

/**
 *  系统异常处理基类
 * Created by Administrator on 2015/10/10.
 */
public abstract class BaseExceptionHandler  implements Thread.UncaughtExceptionHandler{

    public static final String TAG = "CrashHandler";

    protected static final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);


    /**
     *  未能捕获异常跳转
     * @param thread
     * @param throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        if (true){
            try {
                // 如果处理了，让程序继续运行3秒再退出，保证错误日志的保存
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //  退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     *  自定义错误处理，收集错误信息、发送错误报告等操作均在此完成，开发者可以根据自己的情况来自定义异常处理逻辑
     * @param ex
     * @return
     */
    public abstract boolean handleException(Throwable ex);
}

package com.yxhuang.listview.Service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MzService extends Service {

    private MyBinder mBinder = new MyBinder();
    public MzService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.i("yxh", "MzService unbindService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                handleService();
//            }
//        }).start();
        Log.i("yxh", "MzService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("yxh", "MzService onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("yxh", "MzService onDestroy");
    }

     public class MyBinder extends Binder{

            public String getName(String string){
                return "Your name is "+ string;
            }

            public MzService getMzService(){
                return MzService.this;
            }

    }

    public int handleService(){
        Log.i("yxh", "MzService handleService");
        int a = 0;
        for (int i = 0 ; i < 100 ; i ++){
            try {
                Thread.sleep(2 * 1000);
                Log.i("yxh", "MzService handleService: " + i);

                a = i;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return a > 10 ? 0 : a;
    }
}

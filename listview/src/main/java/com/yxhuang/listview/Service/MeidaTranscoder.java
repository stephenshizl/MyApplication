package com.yxhuang.listview.Service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.yxhuang.listview.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeidaTranscoder extends Service {

    public static  final  String ACTION_TRANSCODE_MEDIA = "com.yxhuang.listview.TRANSCODE_MEDIA";
    public static final String EXTRA_OUTPUT_TYPE = "outputType";

    private static final int NOTIFICATION_ID = 1001;
    private int mRunningJobs = 0;
    private ExecutorService mExecutorService;
    private final Object  mLock = new Object();
    private  boolean mIsForeground = false;

    public MeidaTranscoder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutorService = Executors.newCachedThreadPool();
        Log.i("yxh", "MeidaTranscoder>> onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (ACTION_TRANSCODE_MEDIA.equals(action)){
            String outputType = intent.getStringExtra(EXTRA_OUTPUT_TYPE);
            Log.i("yxh", "MeidaTranscoder>> onStartCommand() outputType： " + outputType);
            // 启动新的作业， 并增加计数器
            synchronized (mLock){
//                TranscodeRunnable transcodeRunnable = new TranscodeRunnable(intent.getData(), outputType);
                TranscodeRunnable transcodeRunnable = new TranscodeRunnable( outputType);
                mExecutorService.execute(transcodeRunnable);
                mRunningJobs ++ ;
                startForegroundIfNeeded();
                Log.i("yxh", "MeidaTranscoder>> onStartCommand() mRunningJobs: " + mRunningJobs);
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mExecutorService.shutdown();

        synchronized (mLock){
            mRunningJobs = 0;
            stopForgroundIfAllDone();
        }

        Log.i("yxh", "MeidaTranscoder>> onDestroy()");
    }

    public void startForegroundIfNeeded(){
        if (!mIsForeground){
           Notification notification =  buildNotification();
            startForeground(NOTIFICATION_ID, notification);
            mIsForeground = true;
        }
    }

    private Notification  buildNotification(){
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.mipmap.ic_launcher, "notification", System.currentTimeMillis());
        notification.setLatestEventInfo(this, "This is content title ", "This is content text", null);
         return  notification;
    }

    private void stopForgroundIfAllDone(){
        if (mRunningJobs == 0 && mIsForeground){
            stopForeground(true);
            mIsForeground = false;
        }
    }
    private class TranscodeRunnable implements Runnable{
        private Uri mInData;
        private String mOutputType;

        public TranscodeRunnable(String outputType) {
            mOutputType = outputType;
        }

        public TranscodeRunnable(Uri inData, String outputType) {
            mInData = inData;
            mOutputType = outputType;
        }

        @Override
        public void run() {
            // 在这里执行转码操作

            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 转码完成后， 计数器减1
            synchronized (mLock){
                mRunningJobs -- ;
                stopForgroundIfAllDone();
                Log.i("yxh", "MeidaTranscoder>> TranscodeRunnable() mRunningJobs: " + mRunningJobs);
            }
        }
    }
}

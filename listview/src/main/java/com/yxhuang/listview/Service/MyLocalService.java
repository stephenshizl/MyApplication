package com.yxhuang.listview.Service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.yxhuang.listview.R;

/**
 * Created by Administrator on 2015/7/30.
 */
public class MyLocalService extends Service {
    private static  final  int NOTIFICATION_ID = 1001;
    private LocalBinder mLocalBinder = new LocalBinder();
    private  Callback mCallback;

    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null ){
            String action = intent.getAction();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public   void doLongRunningOperation(MyComplexDataObject dataObject){
        // 为耗时操作启动新线程
        new MyAsyncTask().execute(dataObject);
        Log.i("yxh", "doLongRunningOperation");
    }

    public void setCallback(Callback callback){
        mCallback = callback;

    }

    public void stopService(){
        stopSelf();
        Log.i("yxh", "stopService");
    }


    public class  LocalBinder extends Binder{
        public MyLocalService getService(){
            return  MyLocalService.this;
        }
    }

    public interface Callback{
        void onOperationProgress(int progress);
        void onOperationCompleted(MyComplexResult complexResult);
    }

    private final class MyAsyncTask extends AsyncTask<MyComplexDataObject, Integer, MyComplexResult>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startForeground(NOTIFICATION_ID, buildNotification());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (mCallback != null && values.length > 0){
                for (Integer value : values){
                    mCallback.onOperationProgress(value);
                }
            }
        }

        @Override
        protected void onPostExecute(MyComplexResult myComplexResult) {
            if (mCallback != null ){
                mCallback.onOperationCompleted(myComplexResult);
            }

            stopForeground(true);
        }

        @Override
        protected void onCancelled(MyComplexResult complexResult) {
            super.onCancelled(complexResult);
            stopForeground(true);
        }

        @Override
        protected MyComplexResult doInBackground(MyComplexDataObject... params) {
            MyComplexResult result = new MyComplexResult();
            return result;
        }
    }

    private Notification buildNotification(){
        Notification notification = new Notification(R.mipmap.ic_launcher, "notification", System.currentTimeMillis());
        notification.setLatestEventInfo(this, "This is content title ", "This is content text", null);
        return  notification;
    }
}

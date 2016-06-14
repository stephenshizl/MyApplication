package com.yxhuang.broadcast;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class CounterService extends Service implements ICounterService{
    private static final String TAG = "yxh";

    public static final String BROADCAST_COUNTER_ACTION = "xyhuang.broadcast.COUNTER_ACTION";
    public static final String COUNTER_VALUE = "yxhuang.broadcast.counter.value";

    private boolean stop = false;
    private Binder mBinder = new CounterBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "CounterService create");
    }

    @Override
    public void startCounter(final int initValue){
        AsyncTask<Integer, Integer, Integer> task = new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... params) {
                Integer initCounter = params[0];

                stop  = false;
                while (!stop){
                    publishProgress(initCounter);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    initCounter++;
                }
                return initCounter;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);

                int counter = values[0];
                Intent intent = new Intent();
                intent.setAction(BROADCAST_COUNTER_ACTION);
                intent.putExtra(COUNTER_VALUE, counter);
                sendBroadcast(intent);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);

                int counter = integer;
                Intent intent = new Intent(BROADCAST_COUNTER_ACTION);
                intent.putExtra(COUNTER_VALUE, counter);
                sendBroadcast(intent);
            }
        };

        task.execute(initValue);
    }

    @Override
    public void stopCounter() {
        stop = true;
    }

    public class CounterBinder extends Binder{
        public CounterService getService(){
            return CounterService.this;
        }
    }
}

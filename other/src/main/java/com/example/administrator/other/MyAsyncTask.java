package com.example.administrator.other;

import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/1/23.
 */
public class MyAsyncTask extends AsyncTask<String, Integer, String> {
    private String mName = "AsyncTask";

    public MyAsyncTask(String name){
        super();
        mName = name;
    }

    /**
     *   在主线程中执行，在异步任务执行之前，此方法会被调用，用于做一些准备工作
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     *   在线程池中执行，此方法用于执行异步任务，在此方法中通过 publishProgress 方法来更新任务的进度，
     *    publishProgress 方法会调用 onProgressUpdate 方法。
     *    同时此方法返回结算结果给 onPostExecute 方法
     * @param params 表示输入的参数
     * @return
     */
    @Override
    protected String doInBackground(String... params) {

        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mName;
    }

    /**
     *   在主线程中执行，当后台任务的执行进度发生变化时，此方法会被调用
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    /**
     *  在主线程中执行，在异步任务执行之后，此方法会执行。
     *   如果 onCancelled() 方法调用之后，该方法则不会执行
     * @param result  后台任务的返回值，即 doInBackground 的返回值。
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Log.i("MyAsyncTask", result + " execute finish at " + df.format(new Date()));
    }
}
package com.example.administrator.other;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HandlerThreadActivity extends AppCompatActivity {
    private static final String TAG = "yxh";

    private Button mButton;

    private HandlerThread mHandlerThread;

    // UI 线程的 Handler
    private static Handler mHandler;

    private int count  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "HandlerThreadActivity onCreate");
        setContentView(R.layout.activity_handler_thread);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView

        mButton = (Button) findViewById(R.id.btn_start_handler_thread);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "send msg  " + count + " in thread " + Thread.currentThread().getId());
//                Message message = new Message();
//                message.what = count;
//                mHandler.sendMessageDelayed(message, 3 * 1000);
//                count ++;
//
////                doAsyncTask();
//
//                doIntentService();

                startThirdActivity();
            }
        });

//        doHandlerThread();
    }

    private void doHandlerThread(){
        mHandlerThread = new HandlerThread("new  handler");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.i(TAG, " handle massage  msg " +  msg.what + "  in thread  " + Thread.currentThread().getId() );
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "HandlerThreadActivity onDestroy");
        // 释放资源
        if (mHandler != null) mHandlerThread.quit();
    }

    private void doAsyncTask(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            new MyAsyncTask("AsyncTask#1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
            new MyAsyncTask("AsyncTask#2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
            new MyAsyncTask("AsyncTask#3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
            new MyAsyncTask("AsyncTask#4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        }else {
            new MyAsyncTask("AsyncTask#1").execute("");
            new MyAsyncTask("AsyncTask#2").execute("");
            new MyAsyncTask("AsyncTask#3").execute("");
            new MyAsyncTask("AsyncTask#4").execute("");
        }
    }

    private void doIntentService(){
        Intent intent = new Intent(HandlerThreadActivity.this, LocalIntentService.class);
        intent.putExtra("task_action", "mytask");
        startService(intent);
    }

    private void startThirdActivity(){
        Intent intent = new Intent(HandlerThreadActivity.this, ThirdActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "HandlerThreadActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "HandlerThreadActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "HandlerThreadActivity onPause");
    }
}

package com.example.administrator.other;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class HandlerThreadTest extends AppCompatActivity {

    private static final String TAG = "handler_thread";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread_test);

        Log.i(TAG, "Main Thread id is " + Thread.currentThread().getId());

        startNewThread();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  0:
                    Log.i(TAG, "in Handler Thread id is " + Thread.currentThread().getId());
                    break;

                default:
                    break;
            }
        }
    };


    // 启动一个新的线程
    private void startNewThread(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "in startNewThread Thread id is " + Thread.currentThread().getId());
                Looper.prepare();
                Handler handler = new Handler();
                Looper.loop();
            }
        }).start();
    }


    private void testHandler(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "in testHandler run Thread id is " + Thread.currentThread().getId());
            }
        });
    }


}

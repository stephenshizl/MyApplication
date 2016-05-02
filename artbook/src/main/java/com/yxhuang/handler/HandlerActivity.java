package com.yxhuang.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.administrator.artbook.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HandlerActivity extends Activity {
    private static final String TAG = "yxh";

    private static final int CONTINUE_POLL = 1;
    private static final int QUITE_POLL = 2;

    private static final int POLL_TIMES = 10;

    private static Future mFuture;

    private MyDialogFragment mDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_activiyt);

        Log.i(TAG, "MainThread " + Thread.currentThread().getName());

        MyRunnable runnable = new MyRunnable("12306");
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        mFuture = executorService.submit(runnable);


        mDialogFragment = new MyDialogFragment();
        findViewById(R.id.btn_show_dialog_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialogFragment.show(getFragmentManager(), "dialog");
            }
        });


    }

    private static void futureCancel(){
        mFuture.cancel(true);
        Log.i(TAG, "future is cancel");

    }



    private static class MyRunnable implements Runnable {
        private String mRunId;
        private Handler mHandler;
        private int mCurrentPollTimes = 0;

        public MyRunnable(String id){
            Log.i(TAG, "create runnable thread " + Thread.currentThread().getName());
            mRunId = id;
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what){
                        case CONTINUE_POLL:
                            mCurrentPollTimes++;
                            poll();
                            break;
                        case QUITE_POLL:
                            futureCancel();
                            break;
                        default:
                            break;
                    }
                }
            };
        }

        @Override
        public void run() {
            Log.i(TAG, "runnable  run  thread " + Thread.currentThread().getName());
            poll();
        }

        private void poll(){
            Log.i(TAG, "poll  times " + mCurrentPollTimes);

            if (mCurrentPollTimes < POLL_TIMES){
                mHandler.sendEmptyMessageDelayed(CONTINUE_POLL, 3 * 1000);
            } else {
                mHandler.sendEmptyMessage(QUITE_POLL);
            }
        }
    }

}

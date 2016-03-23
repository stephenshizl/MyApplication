package com.example.administrator.other;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Administrator on 2016/2/14.
 */
public class LocalIntentService extends IntentService {

    private static final String TAG = "LocalIntentService";

    public LocalIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getStringExtra("task_action");
        Log.i(TAG, " receive task :" + action);
        SystemClock.sleep(3 * 1000);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "service destroyed");
        super.onDestroy();
    }
}

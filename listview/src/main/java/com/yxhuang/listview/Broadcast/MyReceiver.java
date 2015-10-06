package com.yxhuang.listview.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public static final String RECEIVER = "com.yxhuang.listview.ACTION_BROADCAST";
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(RECEIVER)){
            String string = intent.getStringExtra("message");
            Toast.makeText(context, string, Toast.LENGTH_LONG).show();
            Log.i("yxh", "BroadcastReceiver:   onReceive 1");

            abortBroadcast();
        }
    }
}

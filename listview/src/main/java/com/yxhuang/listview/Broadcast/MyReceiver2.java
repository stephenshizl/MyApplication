package com.yxhuang.listview.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver2 extends BroadcastReceiver {
    public MyReceiver2() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(MyReceiver.RECEIVER)){
            Log.i("yxh", "BroadcastReceiver:   onReceive 2");
        }
    }
}

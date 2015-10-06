package com.yxhuang.listview.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.yxhuang.listview.R;

public class LocalBroadcastActivity extends AppCompatActivity {
    public  static final String LOCAL_BROADCAST_ACTION = "localBroadcast";
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_broadcast);

        findViewById(R.id.send_local_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(LOCAL_BROADCAST_ACTION);
                sendLocalBroadcast(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter(LOCAL_BROADCAST_ACTION);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("yxh",  "LocalBroadcastActivity  onReceive");
            }
        };

        localBroadcastManager.registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(mReceiver);
        Log.i("yxh", "LocalBroadcastActivity  onPause");
    }

    private void sendLocalBroadcast(Intent broadcastIntent){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(broadcastIntent);
        Log.i("yxh", "LocalBroadcastActivity  sendLocalBroadcast");
    }

}

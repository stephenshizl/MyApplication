package com.yxhuang.listview.Broadcast;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.yxhuang.listview.R;

public class BroadcastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        findViewById(R.id.send_broadcast_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MyReceiver.RECEIVER);
                intent.putExtra("message", "广播通知");
                sendOrderedBroadcast(intent, null);
            }
        });

        findViewById(R.id.jump_to_local_broadcast_activity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BroadcastActivity.this, LocalBroadcastActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.send_order_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrderedbroadcastAndGetResponse();
            }
        });
    }


    // 发送有序广播
    private void sendOrderedbroadcastAndGetResponse(){
        String orderedBroadcastAction = "com.yxhuang.listview.orderedbroadcast";
        Intent intent = new Intent(orderedBroadcastAction);
        // 处理响应的广播接收器
        BroadcastReceiver responseReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String resultData = getResultData();
                Log.i("yxh", "BroadcastActivity onReceive  resultData: " + resultData);
                Bundle resultExtras = getResultExtras(false);
                if (resultExtras != null){
                    ComponentName registeredComponent = resultExtras.getParcelable("componentName");
                    Log.i("yxh", "BroadcastActivity onReceive  registeredComponent: " + registeredComponent);
                }
            }
        };

        sendOrderedBroadcast(intent, null, responseReceiver, null, RESULT_OK, null, null);
    }


}

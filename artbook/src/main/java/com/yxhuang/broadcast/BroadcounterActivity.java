package com.yxhuang.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.artbook.R;

public class BroadcounterActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "yxh";

    private TextView mCounter;
    private Button mStartButton;
    private Button mStopButton;

    private ICounterService counterService = null;

    private ServiceConnection mServiceConnetion = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
             counterService = ((CounterService.CounterBinder)service).getService();
            Log.i(TAG, "counter service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            counterService = null;
            Log.i(TAG, "counter service disconnected");
        }
    };

    private BroadcastReceiver counterActionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int counter = intent.getIntExtra(CounterService.COUNTER_VALUE, 0);
            String text = String.valueOf(counter);
            mCounter.setText(text);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcounter);

        mCounter = (TextView) findViewById(R.id.tv_counter);
        mStartButton = (Button) findViewById(R.id.btn_start_counter);
        mStopButton = (Button) findViewById(R.id.btn_stop_counter);

        mStartButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);

        Intent intent = new Intent(BroadcounterActivity.this, CounterService.class);
        bindService(intent, mServiceConnetion, BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter counterActionFilter = new IntentFilter(CounterService.BROADCAST_COUNTER_ACTION);
        registerReceiver(counterActionReceiver, counterActionFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(counterActionReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnetion);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mStartButton)){
            if (counterService != null){
                counterService.startCounter(0);
                mStartButton.setEnabled(false);
                mStopButton.setEnabled(true);
                Log.i(TAG, "start counter");
            }
        } else if (v.equals(mStopButton)){
            if (counterService != null){
                counterService.stopCounter();
                mStartButton.setEnabled(true);
                mStopButton.setEnabled(false);
                Log.i(TAG, "stop counter");
            }
        }
    }


}

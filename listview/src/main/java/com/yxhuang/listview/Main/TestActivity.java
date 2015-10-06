package com.yxhuang.listview.Main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yxhuang.listview.R;
import com.yxhuang.listview.Service.MzService;


public class TestActivity extends ActionBarActivity {

    private LinearLayout mLinearLayout;
    private TextView mTextView1;
    private TextView mTextView2;


    private Intent mStartMzservice;
    private MzService.MyBinder mBinder;
    private MzService mMzService;

    private static  final  String WIFI_NAME = "wifi_name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mLinearLayout = (LinearLayout) findViewById(R.id.rl_linearlayout);
        mTextView1 = (TextView) findViewById(R.id.text1);
        mTextView2 = (TextView) findViewById(R.id.text2);

        netWorkInfo();

        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("yxh", "mLinearLayout");
                mTextView1.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        switch (event.getAction()) {
                            case KeyEvent.ACTION_DOWN:
                                Log.i("yxh", "mTextView1: ACTION_DOWN");
                                break;
                            case KeyEvent.ACTION_UP:
                                Log.i("yxh", "mTextView1: ACTION_UP");
                                break;
                            case KeyEvent.FLAG_LONG_PRESS:
                                Log.i("yxh", "mTextView1: FLAG_LONG_PRESS");
                                break;

                        }
                        return false;
                    }
                });

                if (mTextView2.hasFocusable()) {
                    Log.i("yxh", "mTextView2: hasFocusable");
                }


                if (mTextView2.isSelected()) {
                    Log.i("yxh", "mTextView2: isSelected");
                }

                if (mTextView2.isPressed()) {
                    Log.i("yxh", "mTextView2: isPressed");
                }

            }
        });


        findViewById(R.id.start_service_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mStartMzservice = new Intent(TestActivity.this, MzService.class);
                startService(mStartMzservice);
            }
        });

        findViewById(R.id.stop_service_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartMzservice != null) stopService(mStartMzservice);
            }
        });

        // 绑定服务
        findViewById(R.id.bind_service_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, MzService.class);
                bindService(intent, mConnection, BIND_AUTO_CREATE);
            }
        });

        // 解除服务
        findViewById(R.id.unbind_service_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(mConnection);
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("yxh", "TestActivity   onDestroy: " );
    }

    public  ServiceConnection mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                mBinder=  (MzService.MyBinder)service;

                Toast.makeText(TestActivity.this, mBinder.getName("yxhuang"),  Toast.LENGTH_LONG).show();

                mMzService = ( (MzService.MyBinder)service).getMzService();
//               int bb = 0;
//                Thread thread = new Thread(new Runnable() {

//                    @Override
//                    public void run() {
//                      int aa =  mBinder.getMzService().handleService();
//                        Log.i("yxh", "aaaaaaa: " + aa);
//
//                    }
//                });
//
//                thread.start();

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mMzService = null;
                Log.i("yxh", " TestActivity onServiceDisconnected() " );

            }
        };















    private void netWorkInfo(){
        ConnectivityManager connectivityManager1 = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =  connectivityManager1.getActiveNetworkInfo();
       WifiManager  wifiManager  = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null ){

            Toast.makeText(this, wifiInfo.getSSID(), Toast.LENGTH_LONG).show();
            Log.i("yxh", "wifiInfo.getSSID()" + wifiInfo.getSSID().toString());

            Log.i("yxh", "wifiInfo.getHiddenSSID() " + wifiInfo.getHiddenSSID());
        }

    }

}

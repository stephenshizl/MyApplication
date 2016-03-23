package com.yxhuang.ipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.memoryleaktest.R;

public class MessengerActivity extends AppCompatActivity {

    private static final  String TAG = "MessengerActivity";

    private Messenger mService;


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message msg = Message.obtain(null, MyConstents.MSG_FROM_CLIENT);
            Bundle date = new Bundle();
            date.putString("msg", "the msg from  client.");
            msg.setData(date);

            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            Log.i(TAG, "connection");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Intent intent = new Intent(MessengerActivity.this, MessengerService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}

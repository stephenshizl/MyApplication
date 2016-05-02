package com.yxhuang.boundService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.artbook.R;

public class MessengerActivity extends Activity implements View.OnClickListener {

    // Messenger for communicating with the service;
    private Messenger mService;

    private boolean mBound;

    private Button mButton;

    private ServiceConnection mConnection  = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binging);

        mButton = (Button) findViewById(R.id.btn_bing_service);
        mButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_bing_service){
            sayHello();
        }
    }

    private void sayHello(){
        if (!mBound) return;
                 Message msg = Message.obtain(null, MessengerService.MSG_FROM_CLIENTS, 0, 0);
        try {
            Log.i("yxh", "messenger send");
            mService.send(msg);
        } catch (DeadObjectException e){
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

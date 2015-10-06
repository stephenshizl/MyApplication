package com.yxhuang.listview.Service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yxhuang.listview.R;

public class MyActivity extends AppCompatActivity implements ServiceConnection, MyLocalService.Callback {

    private MyLocalService mService;

    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mTextView = (TextView) findViewById(R.id.tv_style);

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("yxh", "bindService");

        startMyLoalService();

    }


    private  void  startMyLoalService(){
        Intent bindIntent = new Intent(this, MyLocalService.class);
        bindService(bindIntent, this, BIND_AUTO_CREATE);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mService != null)  {
            mService.setCallback(null);         //  避免内存泄露
            unbindService(this);
            Log.i("yxh", "unbindService");
        }

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.i("yxh", "onServiceConnected");

        mService = ((MyLocalService.LocalBinder)service).getService();
        mService.setCallback(this);

        // 一旦获得对Service的引用，接下来就可以更新UI, 把之前处与disabled 状态的按钮变为enabled状态
        findViewById(R.id.trigger_operation_button).setEnabled(true);
        try {
            Thread.sleep(4 * 1000);
            mService.stopService();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i("yxh", "onServiceDisconnected");
        // 断开Service后， 把按钮置为disabled状态
        findViewById(R.id.trigger_operation_button).setEnabled(false);
        mService = null;

    }


    // 将回调函数指派给UI中按钮的onClick
    public  void OnTriggerLongRunningOperation(View view){
        if (mService != null){
            mService.doLongRunningOperation(new MyComplexDataObject());
        }
    }

    @Override
    public void onOperationProgress(int progress) {
            // 跟新进度条
    }

    @Override
    public void onOperationCompleted(MyComplexResult complexResult) {
            // 展示结果
    }
}

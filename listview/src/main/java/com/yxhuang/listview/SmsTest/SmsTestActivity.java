package com.yxhuang.listview.SmsTest;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yxhuang.listview.R;

public class SmsTestActivity extends AppCompatActivity {

    private TextView mPhoneNumber;
    private TextView mSmsContent;
    private IntentFilter mIntentFilter;
    private MessageReceiver mReceiver;
    private EditText mEditTextNumber;
    private EditText mEditTextSmsContent;
    private Button mSendSmsButton;

    private  IntentFilter sendFilter;
    private SendSmsStatusReceiver mSendSmsStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_test);

        mPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
        mSmsContent = (TextView) findViewById(R.id.tv_phone_content);
        mEditTextNumber = (EditText) findViewById(R.id.et_phone_number);
        mEditTextSmsContent = (EditText) findViewById(R.id.et_phone_content);
        mSendSmsButton = (Button) findViewById(R.id.btn_send_sms);


        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        mSendSmsStatusReceiver = new SendSmsStatusReceiver();
        registerReceiver(mSendSmsStatusReceiver, sendFilter);
        mSendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager manager = SmsManager.getDefault();
                Intent sendIntent = new Intent("SENT_SMS_ACTION");
                PendingIntent pi = PendingIntent.getBroadcast(SmsTestActivity.this, 0, sendIntent, 0);
                manager.sendTextMessage(mEditTextNumber.toString(), null, mEditTextSmsContent.toString(), pi, null);
            }
        });


        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        mIntentFilter.setPriority(100);
        mReceiver = new MessageReceiver();

        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    // 短信广播
    private class  MessageReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null){
                Log.i("yxh", "MessageReceiver");
                Bundle bundle = intent.getExtras();
                Object[] pdus = (Object[]) bundle.get("pdus");            // 提取短信消息
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0 ; i < messages.length ; i ++){
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }

                String address = messages[0].getOriginatingAddress();              // 获取短信发送方的号码
                String fullMessage = "";
                for (SmsMessage message : messages){
                    fullMessage +=  message.getMessageBody();        // 获取短信内容
                }

                Log.i("yxh", "MessageReceiver   address: " + address + "   fullMessage: " + fullMessage);
                mPhoneNumber.setText(address);
                mSmsContent.setText(fullMessage);
            }

            abortBroadcast();  // 广播停止下传
        }
    }


    // 短信发送监听广播
    private class SendSmsStatusReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (getResultCode() == RESULT_OK){
                Toast.makeText(context, "短信发送成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "短信发送失败", Toast.LENGTH_SHORT).show();
            }

        }
    }

}

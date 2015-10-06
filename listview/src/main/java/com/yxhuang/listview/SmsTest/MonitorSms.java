package com.yxhuang.listview.SmsTest;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yxhuang.listview.R;

public class MonitorSms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_sms);

        getContentResolver().registerContentObserver(
                Uri.parse("content://sms"), true,
                new SmsObserver(new Handler())
        );
    }


    // 短信ContentObserver
    private class SmsObserver extends ContentObserver{
        public SmsObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            Log.i("yxh", "sms change: " );
            // 查询发信箱中的短信
            Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

            // 遍历查询得到的结果集
            while (cursor.moveToNext()){
                StringBuffer sb = new StringBuffer();

                // 短信地址
                sb.append("address =").append(cursor.getString(cursor.getColumnIndex("address")));
                // 短信标题
                sb.append("; subject =").append(cursor.getString(cursor.getColumnIndex("subject")));
                // 短信内容
                sb.append("；body =").append(cursor.getString(cursor.getColumnIndex("body")));
                // 短信时间
                sb.append("； date =").append(cursor.getString(cursor.getColumnIndex("date")));

                Log.i("yxh", "sms change: " + sb);

            }

        }
  }
}

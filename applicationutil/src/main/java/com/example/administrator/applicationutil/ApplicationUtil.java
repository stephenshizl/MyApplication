package com.example.administrator.applicationutil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ApplicationUtil extends AppCompatActivity {
    private static final int POLL_INTERVAL = 1000 * 15 ;  // 15秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_util);
    }


    /**
     * 检查后台网络的可用性
     * 4.0以前使用getBackgroundDataSetting
     * 4.0以后使用getActiveNetworkInfo
     */
    @SuppressWarnings("deprecation")
    private void checkBackgroundNetwork(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isNetWorkAvailable = cm.getBackgroundDataSetting()  && cm.getActiveNetworkInfo() != null;
        if (! isNetWorkAvailable) return;
    }

    /**
     *  使用AlamManager 延迟运行服务
     * @param context  上下文
     * @param isOn   是否开启定时服务
     */
    private void setServiceAlarm(Context context, boolean isOn){
        Intent i = new Intent(context, MyService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isOn)  {
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), POLL_INTERVAL,  pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }


}

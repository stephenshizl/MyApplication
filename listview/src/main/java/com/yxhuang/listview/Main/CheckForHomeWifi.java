package com.yxhuang.listview.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class CheckForHomeWifi extends BroadcastReceiver {
    public  static final String PREFS_HOME_WIFI_SSID = "homeSSID";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String homeWifi = preferences.getString(PREFS_HOME_WIFI_SSID,  null);

        if (homeWifi != null){
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null  && networkInfo.getState().equals(NetworkInfo.State.CONNECTED) ){
                WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);

                if (wifiInfo != null && homeWifi.equals(wifiInfo.getSSID())){
                    // 连上默认的wifi
                    Toast.makeText(context, "连上Wifi " + wifiInfo.getSSID(), Toast.LENGTH_LONG).show();
                } else {
                    // 连上其他的wifi
                    Toast.makeText(context, "连上Wifi " + wifiInfo.getSSID(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}

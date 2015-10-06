package com.yxhuang.listview.Broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class OrderedReceiver extends BroadcastReceiver {
    public OrderedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isOrderedBroadcast()){
            setResultCode(Activity.RESULT_OK);
            setResultData("simple response string");
            //  获取当前响应的extras, 如果为null 则新建一个
            Bundle resulteExtras = getResultExtras(true);
            // 设置组件名称
            resulteExtras.putParcelable("componentName", new ComponentName(context, getClass()));

            Log.i("yxh", "OrderedReceiver onReceive  result");
        }
    }
}

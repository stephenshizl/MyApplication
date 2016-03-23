package com.yxhuang.carapplication.car.callback;

import android.app.Dialog;

/**
 * Created by Administrator on 2015/10/12.
 */
public interface DialogCallBack {

     void onButtonClicked(Dialog dialog, int position, Object tag);

     void onCancelDialog(Dialog dialog, Object tag);

}

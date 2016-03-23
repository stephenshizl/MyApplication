package com.yxhuang.carapplication.car.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Administrator on 2015/11/4.
 */
public class DisplayUtil {


    /**
     *   dp值转化成px
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}

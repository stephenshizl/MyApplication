package com.example.administrator.artbook;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/4/3.
 */
public class InterceptTouchEventLayout extends LinearLayout {


    public InterceptTouchEventLayout(Context context) {
        super(context);
    }

    public InterceptTouchEventLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptTouchEventLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("yxh", "InterceptTouchEventLayout " + TouchDelegateActivity.getActionName(ev));
        return false;
    }

}

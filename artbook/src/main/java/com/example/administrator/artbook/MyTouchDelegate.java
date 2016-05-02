package com.example.administrator.artbook;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2016/4/6.
 */
public class MyTouchDelegate extends TouchDelegate {


    private Rect mBounds;
    private boolean mDelegateTargeted;

    private Rect mSlopBounds;
    private int mSlop;

    private View mDelegateView;


    public MyTouchDelegate(Rect bounds, View delegateView) {
        super(bounds, delegateView);
        mBounds = bounds;

        mSlop = ViewConfiguration.get(delegateView.getContext()).getScaledTouchSlop();
        mSlopBounds = new Rect(bounds);
        mSlopBounds.inset(-mSlop, -mSlop);
        mDelegateView = delegateView;

    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        boolean sendToDelegate = false;
        boolean hit = true;
        boolean handled = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Rect bounds = mBounds;

                if (bounds.contains(x, y)) {
                    mDelegateTargeted = true;
                    sendToDelegate = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
                sendToDelegate = mDelegateTargeted;
                if (sendToDelegate) {
                    Rect slopBounds = mSlopBounds;
                    if (!slopBounds.contains(x, y)) {
                        hit = false;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                sendToDelegate = mDelegateTargeted;
                mDelegateTargeted = false;
                break;
        }

        Log.i("yxh", "MyTouchDelegate onTouchEvent ");

        if (sendToDelegate) {
            final View delegateView = mDelegateView;

            if (hit) {
                // Offset event coordinates to be inside the target view
                event.setLocation(delegateView.getWidth() / 2, delegateView.getHeight() / 2);
            } else {
                // Offset event coordinates to be outside the target view (in case it does
                // something like tracking pressed state)
                int slop = mSlop;
                event.setLocation(-(slop * 2), -(slop * 2));
            }
            Log.i("yxh", "MyTouchDelegate onTouchEvent  sendToDelegate");
            handled = delegateView.dispatchTouchEvent(event);
        }
        return handled;
    }

}

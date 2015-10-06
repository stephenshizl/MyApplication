package com.yxhuang.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2015/8/30.
 */
public abstract class BaseView extends View {

    private MyThread mThread;
    private boolean running = true;

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(Context context) {
        super(context);
    }

    protected abstract void  drawSub(Canvas canvas);

    protected abstract void logic();

    @Override
    protected final void onDraw(Canvas canvas) {

        if (mThread == null){
            mThread = new MyThread();
            mThread.start();
        } else {
            drawSub(canvas);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        running = false;
    }

    class MyThread extends Thread{

        @Override
        public void run() {
            while (running){
               logic();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }

        }
    }

}

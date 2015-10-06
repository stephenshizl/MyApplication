package com.yxhuang.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Administrator on 2015/8/30.
 */
public class MyCustomView extends View {

    private Paint paint;
    private float rx = 0;
    private MyThread mThread;
    private float sweepAngle = 0;

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public MyCustomView(Context context) {
        super(context);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 画文字
         paint.setTextSize(50);
        canvas.drawText("Logic View", rx, 50, paint);

        // 画圆
        RectF rectF  = new RectF();
        rectF.set(0, 60, 300, 300);
        canvas.drawArc(rectF, 0, sweepAngle, true, paint);

        if (mThread == null){
            mThread = new MyThread();
            mThread.start();
        }
    }

    class MyThread extends Thread{
        Random random = new Random();
        @Override
        public void run() {
            while (true){
                rx += 3;
                if (rx> getWidth()){
                    rx = 0 - paint.measureText("Logic View");
                }

                sweepAngle ++;
                if (sweepAngle == 360){
                    sweepAngle = 0;
                }

                int r = random.nextInt(256);
                int g = random.nextInt(256);
                int b = random.nextInt(256);
                paint.setARGB(255, r, g, b);

                try {
                    java.lang.Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                postInvalidate();
            }

        }
    }

}

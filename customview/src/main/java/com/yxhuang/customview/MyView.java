package com.yxhuang.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.util.Random;

/**
 * Created by Administrator on 2015/8/30.
 */
public class MyView extends BaseView {
    private float sweepAngle = 0;
    Random random = new Random();
    private Paint paint = new Paint();
    private float rx = 0;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void drawSub(Canvas canvas) {
        // 画文字
        paint.setTextSize(50);
        canvas.drawText("Logic View", rx, 50, paint);

        // 画圆
        RectF rectF  = new RectF();
        rectF.set(0, 60, 300, 300);
        canvas.drawArc(rectF, 0, sweepAngle, true, paint);
    }

    @Override
    protected void logic() {

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
    }
}

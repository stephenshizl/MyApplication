package com.yxhuang.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2015/8/30.
 */
public class CustomTextView extends BaseView {

    private float rx = 0;
    Paint paint = new Paint();

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context) {
        super(context);
    }

    @Override
    protected void drawSub(Canvas canvas) {

        paint.setTextSize(30);
        canvas.drawText("My TextView", rx, 30, paint);

    }

    @Override
    protected void logic() {
            rx += 3;
           if (rx > getWidth()){
               rx = - paint.measureText("My TextView");
           }

    }
}

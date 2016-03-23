package com.example.administrator.androidscroll.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2015/11/29.
 */
public class ClockView extends View {

    private int mHight;
    private int mWidth;

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mHight = getMeasuredHeight();
        mWidth = getMeasuredWidth();

        // 画外圆
        Paint paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(4);
        canvas.drawCircle(mWidth / 2, mHight / 2, mWidth / 2, paintCircle);

        // 画刻度
        Paint paintDegree = new Paint();
        paintDegree.setStrokeWidth(3);
        for (int i = 0; i < 24; i++){
            // 区分整点与非整点
            if (i == 0 || i == 6 || i == 12 || i ==18){
                paintDegree.setStrokeWidth(5);
                paintDegree.setTextSize(30);
                canvas.drawLine(mWidth / 2, mHight / 2 - mWidth / 2, mWidth / 2, mHight / 2 - mWidth / 2 + 60, paintDegree);
                String degree = String.valueOf(i);
                canvas.drawText(degree, mWidth / 2 - paintDegree.measureText(degree) / 2, mHight / 2 - mWidth / 2 + 90, paintDegree);
            } else {
                paintDegree.setStrokeWidth(3);
                paintDegree.setTextSize(15);
                canvas.drawLine(mWidth / 2, mHight / 2 - mWidth / 2, mWidth / 2, mHight / 2 - mWidth / 2 + 30, paintDegree);
                String degree = String.valueOf(i);
                canvas.drawText(degree, mWidth / 2 - paintDegree.measureText(degree) / 2, mHight / 2 - mWidth / 2 + 60, paintDegree);
            }

            // 通过 旋转画布简化坐标运算
            canvas.rotate(15, mWidth / 2, mHight / 2);
        }

        // 画指针
        Paint paintHour = new Paint();
        paintHour.setStrokeWidth(20);
        Paint paintMinute = new Paint();
        paintMinute.setStrokeWidth(10);
        canvas.save();
        canvas.translate(mWidth / 2, mHight / 2);
        canvas.drawLine(0, 0, 100, 100, paintHour);
        canvas.drawLine(0, 0, 100, 200, paintMinute);
        canvas.restore();
    }
}

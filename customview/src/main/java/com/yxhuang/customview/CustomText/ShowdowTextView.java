package com.yxhuang.customview.CustomText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/11/17.
 */
public class ShowdowTextView extends TextView {
    private Paint mPaint1;
    private Paint mPaint2;
    private Context mContext;

    public ShowdowTextView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public ShowdowTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public ShowdowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制外层矩形
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);
        // 绘制内存矩形
        canvas.drawRect(10, 10, getMeasuredWidth() - 10 , getMeasuredHeight() - 10, mPaint2);
        canvas.save();
        // 绘制文字前平移10px
//        canvas.translate(10, 0);
        super.onDraw(canvas);
        canvas.restore();
    }

    private void initPaint(){
        mPaint1 = new Paint();
        mPaint1.setColor(mContext.getResources().getColor(android.R.color.holo_blue_light));
        mPaint1.setStyle(Paint.Style.FILL);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }
}

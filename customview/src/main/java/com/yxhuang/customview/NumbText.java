package com.yxhuang.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2015/8/30.
 */
public class NumbText extends BaseView {

    private Paint mPaint = new Paint();
    private int LineNum;
    private boolean xScroll;
    private float mfx =0;


    public NumbText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NumbText);
        LineNum = ta.getInteger(R.styleable.NumbText_LineNum, 1);
        xScroll = ta.getBoolean(R.styleable.NumbText_xScroll, false);
    }

    public NumbText(Context context) {
        super(context);
    }

    @Override
    protected void drawSub(Canvas canvas) {
        for (int i = 0 ; i < LineNum; i++){
                int textSize = 30 + i;
                mPaint.setTextSize(textSize);
                canvas.drawText("广州图书馆", mfx, textSize + textSize * i, mPaint);
        }
    }

    @Override
    protected void logic() {
            if (xScroll){
                mfx += 3;
                if (mfx > getWidth()){
                    mfx = - mPaint.measureText("广州图书馆");
                }
            }

    }
}

package com.yxhuang.customview.CustomText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * Created by Administrator on 2015/11/22.
 */
public class VolumeView extends View {

    private int mWidth;
    /**
     *  音频条数
     */
    private int mRectCount;

    /**
     *  音频条的宽度
     */
    private int mRectWidth;

    /**
     *  音频条的高度
     */
    private int mRectHeight;

    private int offset = 5;

    private double mRamdom;

    private Paint mPaint;

    private LinearGradient mLinearGradient;

    public VolumeView(Context context) {
        super(context);
        initView();
    }

    public VolumeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VolumeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();;
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mRectCount = 12;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = getWidth();
        mRectHeight = getHeight();
        mRectWidth = (int) (mWidth * 0.6 / mRectCount);
        mLinearGradient = new LinearGradient(0, 0, mRectWidth, mRectHeight, Color.YELLOW, Color.BLUE, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mRectCount; i++){
            mRamdom = Math.random();
            float currentHeight = (float) (mRectHeight * mRamdom);
            canvas.drawRect((float)(mWidth * 0.4 / 2 + mRectWidth * i + offset), currentHeight, (float)(mWidth * 0.4 / 2 + mRectWidth * (i + 1)), mRectHeight, mPaint);

            postInvalidateDelayed(500);
        }

    }
}

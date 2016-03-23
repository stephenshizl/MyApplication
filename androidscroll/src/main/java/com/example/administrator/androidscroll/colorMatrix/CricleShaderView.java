package com.example.administrator.androidscroll.colorMatrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.androidscroll.R;

/**
 *  使用BitmapShader画圆形图片
 * Created by Administrator on 2015/12/13.
 */
public class CricleShaderView extends View {
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private Paint mPaint;

    public CricleShaderView(Context context) {
        super(context);
    }

    public CricleShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CricleShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
//        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        mPaint = new Paint();
//        mPaint.setShader(mBitmapShader);
//        canvas.drawCircle(400, 300, 250, mPaint);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
        mPaint = new Paint();
        mPaint.setShader(mBitmapShader);
        canvas.drawCircle(500, 250, 200, mPaint);
    }
}

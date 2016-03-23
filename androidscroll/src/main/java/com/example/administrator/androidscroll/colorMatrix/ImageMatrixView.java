package com.example.administrator.androidscroll.colorMatrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.androidscroll.R;

/**
 *   6.6 图形变换
 * Created by Administrator on 2015/12/12.
 */
public class ImageMatrixView extends View {

    private Matrix mMatrix;
    private Bitmap mBitmap;


    public ImageMatrixView(Context context) {
        super(context);
        initView();
    }

    public ImageMatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ImageMatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     *  设置图片和它的位置矩阵
     * @param bitmap
     * @param matrix
     */
    public void setImageAndMatrix(Bitmap bitmap, Matrix matrix){
        mBitmap = bitmap;
        mMatrix = matrix;
    }

    private void initView(){
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.moon);
        setImageAndMatrix(mBitmap, new Matrix());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }
}

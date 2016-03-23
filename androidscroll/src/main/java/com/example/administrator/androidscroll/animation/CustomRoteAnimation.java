package com.example.administrator.androidscroll.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

/**
 *  自定义3D动画效果
 * Created by Administrator on 2015/12/20.
 */
public class CustomRoteAnimation extends Animation {

    private int mCenterWidth;
    private int mCenterHeight;
    private Camera mCamera = new Camera();
    private float mRotateY = 0.0f;

    public void setRotateY(float rotateY){
        mRotateY = rotateY;
    }
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

        setDuration(2000);
        setFillAfter(true);
        setInterpolator(new BounceInterpolator());
        mCenterHeight  = height / 2;
        mCenterWidth = width / 2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        Matrix matrix = t.getMatrix();
        mCamera.save();
        // 使用Camera 设置旋转的角度
        mCamera.rotateY(mRotateY * interpolatedTime);
        // 将旋转作用于matrix
        mCamera.getMatrix(matrix);
        mCamera.restore();
        // 通过pre方法设置矩阵作用前的偏移量来改变旋转中心
        matrix.preTranslate(mCenterWidth, mCenterHeight);
        matrix.postTranslate(-mCenterWidth, - mCenterHeight);
    }
}

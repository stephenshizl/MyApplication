package com.example.administrator.androidscroll.animation;

import android.graphics.Matrix;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 *  模拟电视关机的自定义动画
 * Created by Administrator on 2015/12/20.
 */
public class CustomTVColoseAnimation extends Animation {

    private int mCenterWidth;
    private int mCenterHeight;


    /**
     *   动画的初始化工作
     * @param width
     * @param height
     * @param parentWidth
     * @param parentHeight
     */
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

        // 设置默认时长
        setDuration(2000);
        // 动画结束后保留状态
        setFillAfter(true);
        // 设置默认插值器
        setInterpolator(new AccelerateInterpolator());

        mCenterHeight = height / 2;
        mCenterWidth = width / 2;

    }

    /**
     *  自定义动画的核心
     * @param interpolatedTime   插值器的时间因子 0--1.0
     * @param t
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        Matrix matrix = t.getMatrix();
        // 通过Matrix 的各种操作来实现动画
        matrix.setScale(1, 1 - interpolatedTime, mCenterWidth, mCenterHeight);
    }
}

package com.example.administrator.androidscroll;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 *  使用视图坐标拖动
 * Created by Administrator on 2015/11/29.
 */
public class DragView1 extends View {

    private int lastX;
    private int lastY;

   private Scroller mScroller;


    public DragView1(Context context) {
        super(context);
        initView();
    }

    public DragView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        setBackgroundColor(Color.BLUE);
    }

  /**
     *  视图坐标
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 记录触摸点坐标
                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                // 计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;

                // 在当前left、top、right、bottom的基础上加上偏移量
//                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);

                // 同时对left 、right 进行偏移
                offsetLeftAndRight(offsetX);
                // 同时对top、bottom 偏移
                offsetTopAndBottom(offsetY);

                break;

        }
        return true;
    }

 /*   *//**
     *   绝对坐标
     * @param event
     * @return
     *//*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 记录触摸坐标
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算偏移量
                int offsetX = rawX - lastX;
                int offsetY = rawY - lastY;

                // 在当前left、top、right、bottom的基础上加上偏移量
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);

                // 重新设置初始化坐标
                lastX = rawX;
                lastY = rawY;
                break;
        }
        return true;
    }*/



}

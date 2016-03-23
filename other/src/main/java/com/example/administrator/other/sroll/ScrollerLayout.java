package com.example.administrator.other.sroll;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/3/13.
 */
public class ScrollerLayout extends ViewGroup {

    private Scroller mScroller;

    private int mTochSlop;
    private float mXDown;
    private float mXMove;
    private float mLastMove;
    private int mLeftBorder;
    private int mRightBorder;

    public ScrollerLayout(Context context) {
        super(context);
        initView(context);
    }

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++){
            View childView = getChildAt(i);
            // 为 ScrollerLayout 中每一个子控件测量大少
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++){
                View childView = getChildAt(i);
                // 为 ScrollerLayout 中的子控件在水平方向进行布局
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
            // 初始化左右边界
            mLeftBorder = getChildAt(0).getLeft();
            mRightBorder = getChildAt(getChildCount() - 1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mXDown = event.getRawX();
                mXMove = mXDown;
                break;

            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mLastMove = mXMove;
                // 当手指拖动的距离打印 TouchSlop 的值，认为应该滑动，拦截子事件
                if (diff > mTochSlop){
                    return  true;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int srolledX = (int) (mLastMove - mXMove);
                if (getScaleX() + srolledX < mLeftBorder){
                    scrollTo(mLeftBorder, 0);
                    return true;
                } else if (getScaleX() + getWidth() + srolledX > mRightBorder){
                    scrollTo(mRightBorder - getWidth(), 0);
                    return true;
                }

                scrollBy(srolledX, 0);
                mLastMove = mXMove;
                break;

            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值判断应该滚动到哪个子控件的界面上
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                // 第二步，调用 startScroll() 方法初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        // 重写 computeScroll 方法，并在内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    private void initView(Context context){
        // 第一步， 创建 Scroller 实例
        mScroller = new Scroller(context);
        // 获取 TouchSlop 的值
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTochSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

    }
}

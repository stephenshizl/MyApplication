package com.example.administrator.other.sroll;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.other.R;

public class ScrollerActivity extends AppCompatActivity {

    private Button mScrollTo;
    private Button mScrollBy;
//    private RelativeLayout mRelativeLayout;

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
//        mRelativeLayout.requestDisallowInterceptTouchEvent(false);
//        mScrollTo = (Button) findViewById(R.id.btn_scroll_to);
//        mScrollBy = (Button) findViewById(R.id.btn_scroll_by);

//        mScrollTo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRelativeLayout.scrollTo(-60, -100);
//            }
//        });
//
//        mScrollBy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRelativeLayout.scrollBy(-60, -100);
//            }
//        });

        mButton = (Button) findViewById(R.id.btn_scroller);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroller();
            }
        });

        Log.i("yxh", " scroller activity ");
        Toast.makeText(this, "ScrollerActivity", Toast.LENGTH_SHORT).show();

    }

    private void scroller(){
        final int startX = 0;
        final int deltax = 1000;

        final ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animator.getAnimatedFraction();
                mButton.scrollTo(startX +(int)(deltax * fraction), 0);
            }
        });
        animator.start();
        Log.i("yxh", "scroller");

    }

//    Scroller mScroller = new Scroller(mContext);
//
//    // 缓慢滚动到指定的位置
//    private void smoothScrollTo(int destX, int destY){
//        int scrollX = getScrollX();
//        int deltaX = destX - scrollX;
//        // 以 1000ms 内滑向 destX， 效果是慢慢滑动
//        mScroller.startScroll(scrollX, destY, deltaX , 0, 1000);
//        // View 的重绘
//        invalidate();
//    }
//
//    @Override
//    public void computeScroll() {
//        // 重写 computeScroll 方法，并在内部完成平滑滚动的逻辑
//        if (mScroller.computeScrollOffset()){
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            // 再次进行重绘
//            postInvalidate();
//
//        }
//    }



}

package com.example.administrator.other.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.other.MainActivity;
import com.example.administrator.other.R;

public class AnimationActivity extends AppCompatActivity {

    private LinearLayout mLinearLayout;
    private Button mStartLayoutAnimationButton;
    private Button mAnimationButton;

    private TextView mTextView;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        mLinearLayout = (LinearLayout) findViewById(R.id.ll_animation);
        mStartLayoutAnimationButton = (Button) findViewById(R.id.btn_start_layout_animation);
        mAnimationButton = (Button) findViewById(R.id.btn_animation);

        mTextView = (TextView) findViewById(R.id.tv_animation);

        mStartLayoutAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
//                mLinearLayout.clearAnimation();
//                mLinearLayout.setLayoutAnimation(getLayoutAnimationController());
//                performAnimate1();
//                performAnimate2(mStartAnimationButton, mStartAnimationButton.getWidth(), 700);

//                ScaleAnimation scaleAnimation2 = new ScaleAnimation(0, 1, 0, 1,
//                        Animation.RELATIVE_TO_SELF, 0.5F,
//                        Animation.RELATIVE_TO_SELF, 0.5F);
//                scaleAnimation2.setDuration(3000);
//                mTextView.startAnimation(scaleAnimation2);
//
//                Animation animation = AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.animation_set);
//                mTextView.startAnimation(animation);

                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(AnimationActivity.this, R.animator.property_animatior);
                set.setTarget(mTextView);
                set.start();



            }
        });

/*    mStartLayoutAnimationButton.setBackgroundResource(R.drawable.frame_animation);
        AnimationDrawable drawable = (AnimationDrawable) mStartLayoutAnimationButton.getBackground();
        drawable.start();*/


//        mLinearLayout.setLayoutAnimation(getLayoutAnimationController());
//        mLinearLayout.getLayoutAnimation().start();
//
//        mListView = (ListView) findViewById(R.id.lv_anima);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_layout);
//        LayoutAnimationController controller = new LayoutAnimationController(animation);
//        controller.setDelay(0.5f);
//        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
//        mListView.setLayoutAnimation(controller);

    }

    // LayoutAnimation
    private LayoutAnimationController getLayoutAnimationController(){

        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(alphaAnimation);
        layoutAnimationController.setDelay(0.2f);
        return layoutAnimationController;
    }

    /**
     *  用一个类来包装原始对象，间接为其提供 get 和 set 方法
     */
    private void performAnimate1(){
        ViewWrapper viewWrapper = new ViewWrapper(mAnimationButton);
        ObjectAnimator.ofInt(viewWrapper, "width", 700)
                .setDuration(2000)
                .start();

    }

    private void performAnimate2(final View view , final int start, final int end){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            // 持有一个 IntEvaluator 对象， 方便下面估值的时候使用
            private IntEvaluator mIntEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                // 获得当前动画的进度值， 整型 ， 1~ 100 之间
                int currentValue = (Integer) animation.getAnimatedValue();
                Log.i("yxh", "current value: " + currentValue);

                // 获得当前进度占整个动画过程的比例， 浮点型， 0~1 之间
                float fraction = animation.getAnimatedFraction();
                // 直接调用整型估值器， 通过比例计算出宽度，然后再设给 view
                view.getLayoutParams().width = mIntEvaluator.evaluate(fraction, start, end);
                view.requestLayout();
            }
        });

        valueAnimator.setDuration(2000).start();
    }


    private void startActivityWithAnimation(){
        Intent intent = new Intent(AnimationActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter_anim, R.anim.activity_exit_anim);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_enter_anim, R.anim.activity_exit_anim);
    }


    private static  class ViewWrapper{
        private View mTargetView;

        public ViewWrapper(View view){
            mTargetView = view;
        }

        public int getWidth(){
            return  mTargetView.getLayoutParams().width;
        }

        public void setWidth(int width){
            mTargetView.getLayoutParams().width = width;
            mTargetView.requestLayout();
        }
    }

    private AnimationSet viewAnimation(){
        View view = new View(this);
        // 透明度动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1.0F);
        alphaAnimation.setDuration(1000);
        view.startAnimation(alphaAnimation);

        // 旋转动画
        /**
         *   0, 360 代表旋转的起始角度
         *   50， 100 代表旋转中心的坐标
         *
         */
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, 50, 100);
        rotateAnimation.setDuration(1000);
        view.startAnimation(rotateAnimation);

        // 可以通过设置参数控制旋转动画的参考系，厦门就是设置旋转参考系未自身中心点
        RotateAnimation rotateAnimation1 = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F,
                RotateAnimation.RELATIVE_TO_SELF, 0.5F);

        // 位移动画
        TranslateAnimation transformation = new TranslateAnimation(0, 200, 2, 200);
        transformation.setDuration(1000);
        transformation.setFillAfter(true);   // 设置定格在动画之后的状态

        // 缩放动画
        ScaleAnimation scaleAnimation1 = new ScaleAnimation(0, 2, 0, 2);
        // 设置缩放的中心点， 下面是选择自身为缩放中心点。
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5F);

        // 动画集合
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(1000);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(transformation);


        view.startAnimation(animationSet);

        // 动画监听
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return  animationSet;

    }

    /**
     *  整个 View 的动画
     * @return
     */
    private LayoutAnimationController getLayoutAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(200);
        alphaAnimation.setFillAfter(true);
        LayoutAnimationController controller = new LayoutAnimationController(alphaAnimation);
        controller.setInterpolator(new AccelerateInterpolator());
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);
        return controller;
    }


}


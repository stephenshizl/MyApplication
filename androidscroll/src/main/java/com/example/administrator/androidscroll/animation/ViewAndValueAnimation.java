package com.example.administrator.androidscroll.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

/**
 *   集合一些常用的ViewAnimation 和 ValueAnimation
 * Created by Administrator on 2015/12/26.
 */
public class ViewAndValueAnimation {

    private View mView;
    private Context mContext;
    /**
     *  视图动画包含透明度动画、旋转动画、位移动画、缩放动画、已经AnimationSet 动画集合
     */
    private void setViewAnimation(){
        // 透明度动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        mView.startAnimation(alphaAnimation);

        // 旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, 100, 100);
        rotateAnimation.setDuration(1000);
        mView.startAnimation(rotateAnimation);

        // 位移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 200, 0, 300);
        translateAnimation.setDuration(1000);
        mView.startAnimation(translateAnimation);

        // 缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 2, 0, 2);
        scaleAnimation.setDuration(1000);
        mView.startAnimation(scaleAnimation);

        // 动画集合
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(1000);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(scaleAnimation);

        mView.startAnimation(animationSet);

        // 可以增加动画回调， 这里以透明度动画为例
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

    }

    /**
     *  属性动画 ObjectAnimator
     *   这是在 Android 3.0 以后才添加的， 既 API 11 以上
     *    Animator 框架中可以使用 AnimatorSet 和 ObjectAnimator 配合，使用 ObjectAnimator 进行更精细化控制，
     *    只控制一个对象的一个属性，而使用多个ObjectAnimator 组合到 AnimatorSet 中形成一个动画。
     *
     */
    private void setObjectAnimation() {
        /**
         *   mView 要操作的 View
         *    第二个参数是要操纵的属性
         *    第三个是可变数组参数，属性变化过程的取值
         *
         */
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mView, "translationX", 300);
        objectAnimator.setDuration(1000);
        objectAnimator.start();

        // 针对同一个对象的多个属性，要同时作用多种动画，使用PropertyValuesHolder 实现
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("translastionX", 300);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scalaX", 1f, 0, 1f);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("scalaY", 1f, 0, 1f);
        objectAnimator.ofPropertyValuesHolder(mView, holder1, holder2, holder3).setDuration(1000).start();

        // 使用AnimatorSet 的 playTogether(), playSequentially(), animSet.play().with()、before(), after()方法 实现更精确的顺序控制,
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mView, "translationX", 300f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mView, "scaleX", 1f, 0f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mView, "scaleY", 1f, 0f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.playTogether(animator1, animator2, animator3);
        set.start();

        // 动画的监听
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        // 单个动画状态的监听
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                // do something
            }
        });

    }

    // 在代码中使用 XML 定义的属性动画
    private void scaleX(){
//        Animator animator = AnimatorInflater.loadAnimator(this, R.animotor.object_anim);
//        animator.setTarget(mView);
//        animator.start();
    }



    /**
     *  如果一个属性没有get 、set 方法， 可以通过自定义一个属性类或者包装类，间接给这个属性添加get、set 方法
     *  WrapperView 为例
     */
    private static class WrapperView{
        private View mTargetView;

        public WrapperView(View targetView){
            mTargetView = targetView;
        }

        public int getWith(){
            return mTargetView.getLayoutParams().width;
        }

        public void setWith(int with){
            if (with < 0 || with == 0) return;
            mTargetView.getLayoutParams().width = with;
            mTargetView.requestLayout();
        }
    }

    private void setWrapperViewObjectAnimator(){
        Button button = new Button(mContext);
        WrapperView wrapperView = new WrapperView(button);
        ObjectAnimator.ofInt(wrapperView, "with", 500).setDuration(1000).start();

    }

}

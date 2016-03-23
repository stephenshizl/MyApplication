package com.example.administrator.memoryleaktest;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class AnimationLeakActivity extends AppCompatActivity {

    private Button mButton;
    private Button mStartAnimationButton;

    private  ObjectAnimator mAlphaAnimtion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_leak);

        mButton = (Button) findViewById(R.id.btn_start_animation);
        mStartAnimationButton = (Button) findViewById(R.id.btn_animation);
        mStartAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startViewAnimation();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startObjectAnimation();
            }
        });
    }

    private void startObjectAnimation(){
        mAlphaAnimtion = ObjectAnimator.ofFloat(mButton, "alpha",  1f, 0);
        mAlphaAnimtion.setRepeatCount(-1);  // 无限循环
        mAlphaAnimtion.setDuration(3000);
        mAlphaAnimtion.start();
    }

    /**
     *   需要在 Activity  退出时 ， 退出属性动画
     */
    @Override
    protected void onDestroy() {
        mAlphaAnimtion.cancel();
        super.onDestroy();
    }

    private void startViewAnimation(){
        TranslateAnimation transformation = new TranslateAnimation(0, 0, 0, 500);
        transformation.setDuration(2000);
        transformation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    mButton.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mButton.startAnimation(transformation);
    }
}

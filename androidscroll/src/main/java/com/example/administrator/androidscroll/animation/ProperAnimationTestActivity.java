package com.example.administrator.androidscroll.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.androidscroll.R;

public class ProperAnimationTestActivity extends AppCompatActivity {
    private Button btn_animation;
    private ImageView iv_animaiton;

    private ImageView iv_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proper_animation_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
    }

    private void initView(){
        btn_animation = (Button) findViewById(R.id.btn_test_animation);
        iv_animaiton = (ImageView) findViewById(R.id.iv_animation);
        iv_tv = (ImageView) findViewById(R.id.iv_tv);

        btn_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startAnimation();
//                colseTVAnimation();
//                roteAnimation();
//                svgAnimation();
//                lineSvgAnimation();
                sunAndearthAnimation();
            }
        });
    }

    // 开始动画
    private void startAnimation(){
//        animation1();
        animation2();

    }

    // 单个属性动画
    private void animation1(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_animaiton, "translationX", 300);
        animator.setDuration(300);
        animator.start();
    }

    // 属性动画集合
    private void animation2(){
        PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translationX", 300);
        PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
        PropertyValuesHolder pvh3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(iv_animaiton, pvh1, pvh2, pvh3);
        animator.setDuration(2000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Toast.makeText(ProperAnimationTestActivity.this, "Animation start", Toast.LENGTH_SHORT).show();
                Log.i("yxh",  " onAnimationStart " );
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(ProperAnimationTestActivity.this, "Animation End", Toast.LENGTH_SHORT).show();
                Log.i("yxh", " onAnimation  end ");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Toast.makeText(ProperAnimationTestActivity.this, "Animation Repeat", Toast.LENGTH_SHORT).show();
                Log.i("yxh", " onAnimation repeat ");
            }
        });

        animator.start();
        animator.setRepeatCount(10);
        animator.setRepeatMode(ValueAnimator.REVERSE);
    }

    // 自定义关闭电视动画
    private void colseTVAnimation(){
        CustomTVColoseAnimation animation = new CustomTVColoseAnimation();
        iv_tv.startAnimation(animation);
    }

    // 自定义选择动画
    private void roteAnimation(){
        CustomRoteAnimation animation = new CustomRoteAnimation();
        animation.setRotateY(60);
        iv_animaiton.startAnimation(animation);
    }

    // SVG 动画
    private void svgAnimation(){
        Drawable drawable = iv_animaiton.getDrawable();
        if (drawable instanceof Animatable) {
            Toast.makeText(ProperAnimationTestActivity.this, "start svg animation", Toast.LENGTH_SHORT).show();
            ((Animatable) drawable).start();
        }
    }

    // 线的SVG 动画
    private void lineSvgAnimation(){
        Drawable drawable = iv_tv.getDrawable();
        if (drawable instanceof Animatable){
            Toast.makeText(ProperAnimationTestActivity.this, "start svg animation", Toast.LENGTH_SHORT).show();
            ((Animatable) drawable).start();
        }
    }

    private void sunAndearthAnimation(){
        Drawable drawable = iv_animaiton.getDrawable();
        if (drawable instanceof Animatable) {
            Toast.makeText(ProperAnimationTestActivity.this, "start svg animation", Toast.LENGTH_SHORT).show();
            ((Animatable) drawable).start();
        }
    }

}

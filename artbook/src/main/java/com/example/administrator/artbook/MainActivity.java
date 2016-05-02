package com.example.administrator.artbook;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button mButton;
    LinearLayout mLinearLayout;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.btn_scroller);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scroller();
                showToast();
            }
        });


        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

    }

    private void showToast(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(MainActivity.this, "啦啦啦啦~~", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();;
    }

    private void scroller(){
        final int startX = 0;
        final int deltax = 1000;

        final ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(3000);
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

}

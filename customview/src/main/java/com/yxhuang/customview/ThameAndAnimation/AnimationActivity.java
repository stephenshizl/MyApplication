package com.yxhuang.customview.ThameAndAnimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.yxhuang.customview.R;

public class AnimationActivity extends AppCompatActivity {
    private Button btn_start_animation;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animaition);

        mImageView = (ImageView) findViewById(R.id.iv_animation);
        btn_start_animation = (Button) findViewById(R.id.btn_start_animation);
        btn_start_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleAnimation();
            }
        });

    }

    private void scaleAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5F);
        scaleAnimation.setDuration(3 * 1000);
        scaleAnimation.setRepeatCount(10);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        mImageView.startAnimation(scaleAnimation);
    }

}

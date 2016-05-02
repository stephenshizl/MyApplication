package com.example.administrator.other.bar;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.other.R;

public class ProgressBarActivity extends Activity {


    private Button mButton;
    private ProgressBar mProgressBar;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        mButton = (Button) findViewById(R.id.btn_view_framelayout);

        mTextView = (TextView) findViewById(R.id.tv_pro);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(mTextView, "translationY", 0, 500f);
                animator.setDuration(3000);
                animator.start();
            }
        });
    }
}

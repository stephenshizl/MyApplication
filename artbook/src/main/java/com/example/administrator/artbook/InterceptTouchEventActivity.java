package com.example.administrator.artbook;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class InterceptTouchEventActivity extends Activity {
    private static  final String TAG = "yxh";

    private Button mInterceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercept_touch_event);

        mInterceptButton = (Button) findViewById(R.id.btn_intercept);
        mInterceptButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "perform onTouch");
                return true;
            }
        });

        mInterceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Intercept Button is click ");
//                Intent intent = new Intent(InterceptTouchEventActivity.this, DialogActivity.class);
//                if (intent.resolveActivity(getPackageManager()) != null) startActivity(intent);
            }
        });

        // 即使 onTouch(...) 方法返回了 true, 也可通过调用 performClick() 方法使 OnClickListener.onClick(...) 方法执行
        mInterceptButton.performClick();
    }
}

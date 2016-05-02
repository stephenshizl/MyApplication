package com.example.administrator.artbook;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TouchDelegateActivity extends Activity {
    private static  final String TAG = "yxh";

    private LinearLayout mLinearLayout;
    private ImageButton mImageButton;

//    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_delegate);

        mLinearLayout = (LinearLayout) findViewById(R.id.rl_touch);
        mImageButton = (ImageButton) findViewById(R.id.ib_touch);
//        mButton = (Button) findViewById(R.id.btn_test);
//
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "test button click");
//            }
//        });

        mLinearLayout.post(new Runnable() {
            @Override
            public void run() {


                // 将 ImageButton 的 enable 设为true 确保它能接收到点击事件
                mImageButton.setEnabled(true);
                mImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TouchDelegateActivity.this, " Button touch", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Button  onClick ");
                    }
                });


                // 1、设置 ImageButton 可点击的范围
                Rect delegateArea = new Rect();
                mImageButton.getHitRect(delegateArea);

                // Extend the touch area of the button beyond its bound on the right and bottom
                // 2、扩大 ImageButton 的点击范围
                delegateArea.right += 100;
                delegateArea.bottom +=500;

                // Instantiate a TouchDelegate
                // 3、实例化 TouchDelegate
                TouchDelegate touchDelegate = new TouchDelegate(delegateArea, mImageButton);

                // Sets the TouchDelegate on the parent view, such that touches within the touch delegate
                // are routed to the child.
                ///4、将 touchDelegate 设置到 ImageButton 的父视图上。
                if (View.class.isInstance(mImageButton.getParent())){
                    ((View)mImageButton.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });

        mImageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "Button  onTouch " + getActionName(event));
                return false;
            }
        });

        mLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "LinearLayout onTouch  " + getActionName(event));
                return false;
            }
        });

        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "LinearLayout onClick ");
            }
        });

    }


    public static String getActionName(MotionEvent event){
        String name = "ACTION_DOWN";
        int action = event.getAction();
        switch (action){
            case  0:
                name = "ACTION_DOWN";
                break;
            case  1:
                name = "ACTION_UP";
                break;
            case  2:
                name = "ACTION_MOVE";
                break;
            default:
                break;
        }

        return  name;
    }


}

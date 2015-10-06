package com.yxhuang.customview.ThameAndAnimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.yxhuang.customview.R;

public class DragViewActivity extends AppCompatActivity {
    private Button btn_drag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view);


        // 获取屏幕的宽高
        DisplayMetrics dm = getResources().getDisplayMetrics();
        final int screenWith = dm.widthPixels;
        final int screenHeight = dm.heightPixels;

        btn_drag = (Button) findViewById(R.id.btn_drag);
        btn_drag.setOnTouchListener(new View.OnTouchListener() {
                int lastX;
                int lastY;
                boolean isDraging = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int ee = event.getAction();
                switch (ee){
                    case MotionEvent.ACTION_DOWN:
                        isDraging = true;
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isDraging){
                            // x轴方向的位移差
                            int dx = (int) event.getRawX() - lastX;
                            //  y轴方向的位移差
                            int dy = (int) event.getRawY() - lastY;

                            int l = v.getLeft() + dx;
                            int t = v.getTop() + dy;
                            int r = v.getRight() + dx;
                            int b = v.getBottom() + dy;

                            // 判断超出屏幕
                            if (l < 0){
                                l = 0;
                                r = l + v.getWidth();
                            }
                            if (t < 0){
                                t = 0;
                                b = t + v.getHeight();
                            }
                            if (r > screenWith){
                                r = screenWith;
                                l = r - v.getWidth();
                            }
                            if (b > screenHeight){
                                b = screenHeight;
                                t = b - v.getHeight();
                            }

                            // 重绘view
                            v.layout(l, t, r, b);
                            v.postInvalidate();

                            lastX = (int) event.getRawX();
                            lastY = (int) event.getRawY();
                        }
                        break;

                    case  MotionEvent.ACTION_UP:
                        isDraging = false;
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}

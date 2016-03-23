package com.yxhuang.customview.CustomViewGroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yxhuang.customview.CustomText.TopBar;
import com.yxhuang.customview.R;


public class CustomViewActivity extends AppCompatActivity {

    private TopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        mTopBar = (TopBar) findViewById(R.id.tobar);

        mTopBar.setOnTopBarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void leftClick() {
                Toast.makeText(CustomViewActivity.this, "左边按钮点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(CustomViewActivity.this, "右边按钮点击", Toast.LENGTH_SHORT).show();
            }
        });

        // 将右边按钮不显示
        mTopBar.setButtonVisiable(1,false);
    }

    /**
     *  自定义测量
     * @param measureSpec
     * @return
     */
    private int measureWidth(int measureSpec){
        int result = 0;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY){
            result = specSize;
        } else {
            result = 200;
            if (specMode == View.MeasureSpec.AT_MOST){
                result = Math.max(result, specSize);
            }
        }
        return  result;
    }
}

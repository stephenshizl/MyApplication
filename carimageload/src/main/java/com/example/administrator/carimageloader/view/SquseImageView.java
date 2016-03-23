package com.example.administrator.carimageloader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 *  一个正方形的ImageView
 * Created by Administrator on 2015/10/24.
 */
public class SquseImageView extends ImageView {
    public SquseImageView(Context context) {
        super(context);
    }

    public SquseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquseImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

    }
}

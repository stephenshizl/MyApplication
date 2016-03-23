package com.yxhuang.customview.CustomText;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/11/18.
 */
public class FlashTextView extends TextView {


    public FlashTextView(Context context) {
        super(context);
    }

    public FlashTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlashTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        /**
         *  在回调父类方法前，实现自己的逻辑，对于TextView 来说即是绘制文本内容之前
         */
        super.onDraw(canvas);
        /**
         *  在回调父类方法后， 实现自己的逻辑， 对于TextView 来说就是在绘制文本内容之后
         */
    }
}

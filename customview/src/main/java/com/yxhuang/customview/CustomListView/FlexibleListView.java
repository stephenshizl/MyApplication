package com.yxhuang.customview.CustomListView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

/**
 *   穿件有弹性的ListView
 * Created by Administrator on 2015/11/23.
 */
public class FlexibleListView extends ListView {

    private int mMaxOverDistance = 50;
    private Context mContext;

    public FlexibleListView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public FlexibleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public FlexibleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    /**
     *  通过屏幕的density 来计算具体的值，让不同分辨率的弹性距离基本一致
     */
    private void initView(){
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        float density = metrics.density;
        mMaxOverDistance = (int) (density * mMaxOverDistance);

    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY,
                scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX,
                mMaxOverDistance,              // maxOverScrollY默认的值，通过修改其值让ListView有弹性
                isTouchEvent);
    }
}

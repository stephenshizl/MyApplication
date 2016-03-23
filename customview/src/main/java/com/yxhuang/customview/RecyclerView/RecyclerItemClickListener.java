package com.yxhuang.customview.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2015/9/17.
 *  创建RecyclerView的Item点击类
 *  网址：http://sapandiwakar.in/recycler-view-item-click-handler/
 *             http://stackoverflow.com/questions/24471109/recyclerview-onclick/26826692#26826692
 *             http://www.littlerobots.nl/blog/Handle-Android-RecyclerView-Clicks/
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    public interface  OnItemClickListener{
         void onItemClick(View view, int position);
         void onItemLongClick(View view, int position);
    }

    GestureDetector mGestureDetector;
    private OnItemClickListener mItemClickListener;

    public RecyclerItemClickListener(Context context, final RecyclerView view, OnItemClickListener listener){
        mItemClickListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = view.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mItemClickListener != null){
                    mItemClickListener.onItemLongClick(view, view.getChildAdapterPosition(childView));
                }
            }
        });

    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mItemClickListener != null && mGestureDetector.onTouchEvent(e)) {
            mItemClickListener.onItemClick(view, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

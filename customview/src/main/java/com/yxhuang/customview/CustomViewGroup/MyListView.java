package com.yxhuang.customview.CustomViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yxhuang.customview.R;

/**
 * Created by Administrator on 2015/9/1.
 */
public class MyListView extends ListView implements View.OnTouchListener, GestureDetector.OnGestureListener{

    private Context mContext;
    private GestureDetector mGestureDetector;
    private OnDeleteListener listener;
    private boolean isDeleteShown;
    private ViewGroup itemLayout;
    private View  deleteButton;
    private int selectedItem;

    public MyListView(Context context) {
        super(context);
        mContext = context;
        mGestureDetector = new GestureDetector(context, this);
        setOnTouchListener(this);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mGestureDetector = new GestureDetector(context, this);
        setOnTouchListener(this);
    }

    public void setOnDeleteListener(OnDeleteListener l){
        listener = l;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isDeleteShown){
            itemLayout.removeView(deleteButton);
            deleteButton = null;
            isDeleteShown = false;
            return  false;
        } else {
            return  mGestureDetector.onTouchEvent(event);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (isDeleteShown){
            selectedItem = pointToPosition((int)e.getX(), (int)e.getY());
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if ( !isDeleteShown  && Math.abs(velocityX) > Math.abs(velocityY) ){
            deleteButton = LayoutInflater.from(mContext).inflate(R.layout.delete_button, null);
            deleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemLayout.removeView(deleteButton);
                    deleteButton = null;
                    isDeleteShown = false;
                    listener.onDelete(selectedItem);
                }
            });

            itemLayout = (ViewGroup) getChildAt(selectedItem - getFirstVisiblePosition());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,   ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            itemLayout.addView(deleteButton, params);
            isDeleteShown = true;

        }
        return false;
    }


    public interface OnDeleteListener{
        void onDelete(int index);
    }

}

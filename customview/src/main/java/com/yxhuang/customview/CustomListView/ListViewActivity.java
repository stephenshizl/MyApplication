package com.yxhuang.customview.CustomListView;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yxhuang.customview.R;

public class ListViewActivity extends AppCompatActivity {

    private FlexibleListView mFlexibleListView;
    private String[] data = new String[30];

    private ListView mListView;
    private float mFirstY;
    private float mCurrentY;
    private float mTouchSlop;
    private int direction;
    private boolean mShow = true;
    private Animator mAnimator;
    private Toolbar mToolbar;
    private String[] mStr = new String[20];


    View.OnTouchListener myTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mFirstY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurrentY = event.getY();
                    if (mCurrentY - mFirstY > mTouchSlop){
                        direction = 0;  //down
                    } else if (mFirstY - mCurrentY > mTouchSlop){
                        direction = 1; // up
                    }

                    if (direction == 1){
                        if (mShow){
                            toolbarAnim(1);  // show
                            mShow = !mShow;
                        }
                    } else if (direction == 0){
                        if (!mShow){
                            toolbarAnim(0);  // hide
                            mShow = !mShow;
                        }
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    break;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

//        for (int i = 0; i < 30; i++) {
//            data[i] = "" + i;
//        }
//        mFlexibleListView = (FlexibleListView) findViewById(R.id.flexible_listview);
//        mFlexibleListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data));

        mListView = (ListView) findViewById(R.id.listview);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.listview);
        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        for (int i = 0; i < mStr.length; i++) {
            mStr[i] = "Item " + i;
        }
        View header = new View(this);
        header.setLayoutParams(new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(
                        R.dimen.abc_action_bar_default_height_material)));
        mListView.addHeaderView(header);
        mListView.setAdapter(new ArrayAdapter<>(
                ListViewActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                mStr));
        mListView.setOnTouchListener(myTouchListener);

    }

    /**
     *  动画
     * @param flag
     */
    private void toolbarAnim(int flag){
        if (mAnimator != null && mAnimator.isRunning()){
            mAnimator.cancel();
        }
        if (flag == 0){
            mAnimator = ObjectAnimator.ofFloat(mToolbar, "translationY", mToolbar.getTranslationY(), 0);
        } else {
            mAnimator = ObjectAnimator.ofFloat(mToolbar, "translationY", mToolbar.getTranslationY(),  -mToolbar.getHeight());
        }

        mAnimator.start();
    }
}

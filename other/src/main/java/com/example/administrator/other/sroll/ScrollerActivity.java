package com.example.administrator.other.sroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.administrator.other.R;

public class ScrollerActivity extends AppCompatActivity {

    private Button mScrollTo;
    private Button mScrollBy;
//    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sroller);

//        mRelativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
//        mRelativeLayout.requestDisallowInterceptTouchEvent(false);
        mScrollTo = (Button) findViewById(R.id.btn_scroll_to);
        mScrollBy = (Button) findViewById(R.id.btn_scroll_by);

//        mScrollTo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRelativeLayout.scrollTo(-60, -100);
//            }
//        });
//
//        mScrollBy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRelativeLayout.scrollBy(-60, -100);
//            }
//        });

    }

}

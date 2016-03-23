package com.android5.x.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.android5.x.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/2.
 */
public class RecyclerViewActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private Spinner mSpinner;
    private Button btn_add_view;
    private Button btn_del_view;

    private List<String> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        btn_add_view = (Button) findViewById(R.id.btn_add_view);
        btn_del_view = (Button) findViewById(R.id.btn_del_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        // 设置动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSpinner.setOnItemSelectedListener(this);
        btn_add_view.setOnClickListener(this);
        btn_del_view.setOnClickListener(this);


        // 增加测试数据
        mData.add("Recycler");
        mData.add("Recycler");
        mData.add("Recycler");

        mRecyclerAdapter = new RecyclerAdapter(mData);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mRecyclerAdapter.setItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, int position) {
                // 设置点击动画
                view.animate()
                        .translationZ(15F).setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                view.animate()
                                        .translationZ(1F)
                                        .setDuration(500)
                                        .start();
                            }
                        }).start();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            // 设置成线性布局
            Log.i("yxh", "onItemSelected   0" );
            mRecyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this));
        } else if (position == 1) {
            Log.i("yxh", "onItemSelected   1" );
            mRecyclerView.setLayoutManager(new GridLayoutManager(RecyclerViewActivity.this, 3));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_view:
                Log.i("yxh", "add view" );
                mData.add("RecyclerView");
                int position = mData.size();
                if (position > 0) mRecyclerAdapter.notifyDataSetChanged();
                break;

            case R.id.btn_del_view:
                Log.i("yxh", "del  view" );
                int position1 = mData.size();
                if (position1 > 0){
                    mData.remove(position1 -1);
                    mRecyclerAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}

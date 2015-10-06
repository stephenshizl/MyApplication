package com.yxhuang.customview.RecyclerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yxhuang.customview.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mStringList;
    private MyRecyclerViewAdapter mRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initDates();

        initView();

        mRecyclerViewAdapter = new MyRecyclerViewAdapter(this, mStringList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        // 设置RecyclerView的方向
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        // 设置RecyclerView的分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // 设置RecyclerView添加或删除的动画
        ScaleInAnimator animator = new ScaleInAnimator();
        mRecyclerView.setItemAnimator(animator);

        // 设置RecyclerView 的点击事件
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(RecyclerViewActivity.this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(RecyclerViewActivity.this, "item click position: " + position , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(RecyclerViewActivity.this, "item long click position: " + position , Toast.LENGTH_SHORT).show();
                    }
                })
        );

    }

    // 初始化数据
    private void initDates(){
        mStringList = new ArrayList<>();
        for (int i = 'A'; i  <= 'z'; i ++){
            mStringList.add("" + (char)i);
        }
    }

    // 初始化视图
    private void initView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater  inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                mRecyclerViewAdapter.add(4);
                return true;

            case R.id.action_deleted:
                mRecyclerViewAdapter.deleted(1);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.yxhuang.customview.CustomViewGroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yxhuang.customview.R;

import java.util.ArrayList;
import java.util.List;

public class CustomViewMainActivity extends AppCompatActivity {
    private MyListView mMyListView;
    private MyListViewAdapter mAdapter;
    private List<String> containList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_main_acitivity);

        mMyListView = (MyListView) findViewById(R.id.my_list_view);

         initList();
        mMyListView.setOnDeleteListener(new MyListView.OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                containList.remove(index);
                mAdapter.notifyDataSetChanged();
            }
        });

        mAdapter = new MyListViewAdapter(this, 0, containList);
        mMyListView.setAdapter(mAdapter);
    }

    private void initList() {
        containList.add("Content Item 1");
        containList.add("Content Item 2");
        containList.add("Content Item 3");
        containList.add("Content Item 4");
        containList.add("Content Item 5");
        containList.add("Content Item 6");
        containList.add("Content Item 7");
        containList.add("Content Item 8");
        containList.add("Content Item 9");
        containList.add("Content Item 10");
        containList.add("Content Item 11");
        containList.add("Content Item 12");
        containList.add("Content Item 13");
        containList.add("Content Item 14");
        containList.add("Content Item 15");
        containList.add("Content Item 16");
        containList.add("Content Item 17");
        containList.add("Content Item 18");
        containList.add("Content Item 19");
        containList.add("Content Item 20");
    }
}

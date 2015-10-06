package com.yxhuang.listview.Main;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.yxhuang.listview.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    private List<String> dates;

    private ListView mListView;
    private EditText mEditText;
   private  ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


//        mListView = (ListView) findViewById(R.id.my_list);
        mEditText = (EditText) findViewById(R.id.edit);

       displayList();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        dates.remove(position);
        adapter.notifyDataSetChanged();

    }

    private void displayList(){

        dates = new ArrayList<>();
        dates.add("中国");
        dates.add("美国");
        dates.add("日本");

       adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dates);

        getListView().setAdapter(adapter);
//        mListView.setAdapter(adapter);

        mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    dates.add(mEditText.getText().toString());
                    adapter.notifyDataSetChanged();
                    mEditText.setText(null);
                }
                return false;
            }
        });

    }

}

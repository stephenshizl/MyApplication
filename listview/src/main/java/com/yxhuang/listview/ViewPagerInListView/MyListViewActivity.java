package com.yxhuang.listview.ViewPagerInListView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yxhuang.listview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyListViewActivity extends AppCompatActivity {
    private ListView mListView;
    private ListViewAdapter mListViewAdapter;

    private List<String> mStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_view);

        mListView = (ListView) findViewById(R.id.list_view);

        List<Objects> objectses = new ArrayList<>();

        mStrings.add("中国");
        mStrings.add("美国");
        mStrings.add("英国");
        mStrings.add("法国");
        mStrings.add("德国");

        mListViewAdapter = new ListViewAdapter(objectses, mStrings);
        mListView.setAdapter(mListViewAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("yxh", "MyListViewActivity onItemClickListener: " + position);
                Toast.makeText(MyListViewActivity.this, mStrings.get(position), Toast.LENGTH_LONG).show();

            }
        });

    }


    /**
     * Listview 的内部适配器
     */
    private class  ListViewAdapter extends BaseAdapter{
        private List<Objects> mList;
        private List<String> mStringList;
        public ListViewAdapter(List<Objects> list, List<String> strings) {
            mList = list;
            mStringList = strings;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView =  LayoutInflater.from(MyListViewActivity.this).inflate(R.layout.item_list_view, null);
                viewHolder.viewpager = (android.support.v4.view.ViewPager) convertView.findViewById(R.id.viewpager);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_item_list_view_title);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            List<CustomTextView> list = new ArrayList<>();
             List<String> mSubTitle = new ArrayList<>();
            mSubTitle.add("广州");
            mSubTitle.add("深圳");
            mSubTitle.add("佛山");
            list.add(new CustomTextView(MyListViewActivity.this, mSubTitle));
            list.add(new CustomTextView(MyListViewActivity.this, mSubTitle));
            list.add(new CustomTextView(MyListViewActivity.this,  mSubTitle));
            list.add(new CustomTextView(MyListViewActivity.this,  mSubTitle));

            viewHolder.viewpager.setAdapter(new ViewItemAdapter(list));
            viewHolder.textView.setText(mStringList.get(position));

            return convertView;
        }
    }

    private class ViewHolder{
        android.support.v4.view.ViewPager viewpager;
        TextView textView;
    }


}

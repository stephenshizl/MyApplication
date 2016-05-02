package com.yxhuang.customview.CustomViewGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yxhuang.customview.R;

import java.util.List;

/**
 * Created by Administrator on 2015/9/2.
 */
public class MyListViewAdapter extends ArrayAdapter<String> {

    private Context mContext;
    public MyListViewAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, View parent) {
        View view;
        if (convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.my_list_view_item,  null);
        } else {
            view = convertView;
        }
        TextView tv_ItemListView = (TextView) view.findViewById(R.id.tv_item_list_view);
        tv_ItemListView.setText(getItem(position));

        return view;
    }
}

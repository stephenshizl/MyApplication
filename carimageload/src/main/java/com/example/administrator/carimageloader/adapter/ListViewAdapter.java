package com.example.administrator.carimageloader.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.carimageloader.R;
import com.example.administrator.carimageloader.imageLoader.AsyncImageLoader;
import com.example.administrator.carimageloader.view.SquseImageView;

import java.util.List;

/**
 *  listview 适配器
 * Created by Administrator on 2015/10/24.
 */
public class ListViewAdapter extends SimpleBaseAdapter<String>{
    private ListView mListView;

    public ListViewAdapter(Context context, List<String> dates, ListView listview) {
        super(context, dates);
        mListView = listview;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_listview, null);
            viewHolder.imageView = (SquseImageView) convertView.findViewById(R.id.iv_squse);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 开启异步加载图片
        AsyncImageLoader.getInstance(mContext).loadBitmaps(mListView, viewHolder.imageView, mDates.get(position));

        return convertView;
    }

    class ViewHolder{
        SquseImageView imageView;
    }
}

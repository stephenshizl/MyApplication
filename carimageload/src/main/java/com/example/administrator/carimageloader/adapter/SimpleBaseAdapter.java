package com.example.administrator.carimageloader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 *  SimpleBaseAdapter extends BaseAdapter
 *   实现getCount, getItem, getItemId 方法
 * Created by Administrator on 2015/10/10.
 */
public abstract class SimpleBaseAdapter<T> extends BaseAdapter{
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mDates;

    public SimpleBaseAdapter(Context context, List<T> dates){
        mContext = context;
        mDates = dates;
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     *  刷新适配器数据源
     * @param dates
     */
    public void refreshDates(List<T> dates){
        mDates = dates;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDates != null ? mDates.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}

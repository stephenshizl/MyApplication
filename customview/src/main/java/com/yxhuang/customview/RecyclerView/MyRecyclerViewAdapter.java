package com.yxhuang.customview.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yxhuang.customview.R;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context mContext;
    private List<String> mStringList;
    private LayoutInflater mLayoutInflater;

    public MyRecyclerViewAdapter(Context context, List<String> list) {
        mContext = context;
        mStringList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("yxh", "onCreateViewHolder: ");
        View view = mLayoutInflater.inflate(R.layout.item_recycler_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_recycler_view.setText(mStringList.get(position));
    }


    @Override
    public int getItemCount() {
        Log.i("yxh", "getItemCount: " + mStringList.size());
        return mStringList.size();
    }

    public  void add(int position){
        mStringList.add(position, "Add One");
        notifyItemInserted(position);           // notifyDateChanged()
    }

    public  void deleted(int position){
        mStringList.remove(position);
        notifyItemRemoved(position);
    }
}


class  MyViewHolder extends RecyclerView.ViewHolder {
    TextView tv_recycler_view;

    public MyViewHolder(View itemView) {
        super(itemView);
        tv_recycler_view = (TextView) itemView.findViewById(R.id.tv_item_recycler_view);
    }
}

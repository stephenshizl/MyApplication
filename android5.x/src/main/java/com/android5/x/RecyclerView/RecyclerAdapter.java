package com.android5.x.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android5.x.R;

import java.util.List;

/**
 *
 * Created by Administrator on 2016/1/2.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<String > mData;


    public RecyclerAdapter(List<String> data){
        mData = data;
    }


    // 需要自己实现点击事件
    public OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener){
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }




    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
            mTextView.setOnClickListener(this);
        }

        // 通过接口回调来实现RecyclerView 的点击事件
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }



    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 将布局转化为View 并传递给 RecyclerView 封装好的 ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
        // 建立起 ViewHolder 中视图与数据的关联
        holder.mTextView.setText(mData.get(position) + position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

package com.yxhuang.carapplication.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yxhuang.carapplication.R;
import com.yxhuang.carapplication.car.bean.NewsEntity;
import com.yxhuang.carapplication.car.imageLoader.ImageLoader;

import java.util.List;

/**
 *  新闻适配器
 * Created by Administrator on 2015/10/19.
 */
public class NewsItemAdapter extends SimpleBaseAdapter<NewsEntity> {
    private ImageLoader mImageLoader;

    public NewsItemAdapter(Context context, List<NewsEntity> list){
        super(context, list);
        mImageLoader  = ImageLoader.build(context);
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null){
            mViewHolder = new ViewHolder();
            convertView =  mLayoutInflater.from(mContext).inflate(R.layout.item_main_news_fragment, null, false);
            ViewUtils.inject(mViewHolder, convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.tv_title.setText(mDatas.get(position).getTitle());
        mViewHolder.tv_time.setText(mDatas.get(position).getTime());
        mViewHolder.tv_comment.setText("评论：" + mDatas.get(position).getComment());
        mImageLoader.bindBitmap(mDatas.get(position).getUrl(), mViewHolder.iv_car, 100, 200);

        return convertView;
    }
}

class  ViewHolder {
    // 标题
    @ViewInject(R.id.item_main_news_tv_title)
    TextView tv_title;
    // 图片
    @ViewInject(R.id.item_main_news_iv_cart)
    ImageView iv_car;
    // 时间
    @ViewInject(R.id.item_main_news_tv_time)
    TextView tv_time;
    // 评论
    @ViewInject(R.id.item_main_news_tv_comment)
    TextView tv_comment;


}

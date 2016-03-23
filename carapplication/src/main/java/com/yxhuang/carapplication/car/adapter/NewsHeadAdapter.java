package com.yxhuang.carapplication.car.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yxhuang.carapplication.R;
import com.yxhuang.carapplication.car.bean.NewsItem;
import com.yxhuang.carapplication.car.imageLoader.ImageLoader;

import java.util.List;

/**
 *  新闻头部适配器
 * Created by Administrator on 2015/11/1.
 */
public class NewsHeadAdapter extends PagerAdapter {
    private List<NewsItem> mDates;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ImageLoader mLoader;

    public NewsHeadAdapter(List<NewsItem> dates, Context context) {
        mDates = dates;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mLoader = ImageLoader.build(context);
    }

    @Override
    public int getCount() {
        return mDates.size();
    }

    /**
     *  刷新数据
     * @param dates
     */
    public void refleshDates(List<NewsItem> dates){
        mDates = dates;
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==  object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @SuppressLint("InflateParams")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View layout = mLayoutInflater.inflate(R.layout.news_head_item, null);
        ImageView viewpager_item_img = (ImageView) layout.findViewById(R.id.viewpager_item_img);
        Log.i("yxh", " instantiateItem  + imageview "+ viewpager_item_img);
                 viewpager_item_img.setTag(mDates.get(position).getNews_image_url());
        Log.i("yxh", " instantiateItem " + mDates.get(position).getNews_image_url());
        Log.i("yxh", " imageLoader " +mLoader);
        mLoader.bindBitmap(mDates.get(position).getNews_image_url(), viewpager_item_img, 200, 400);
        container.addView(layout);
        return layout;
    }

    /** 配合notifyDataSetChanged方法刷新viewpager*/
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}

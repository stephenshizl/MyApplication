package com.example.administrator.carimageloader.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  异步加载图片任务
 * Created by Administrator on 2015/10/23.
 */
public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

    // 异步加载图片工具
    private AsyncImageLoader mImageLoader;
    // 显示图片的控件所在的视图
    private View mView;
    // 图片地址
    protected String mImageUrl;

    public BitmapWorkerTask(AsyncImageLoader imagerLoader, View view){
        mImageLoader = imagerLoader;
        mView = view;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        mImageUrl = params[0];
        // 获取网络图片
        Bitmap bitmap = downloadBitmap(params[0]);
        if (bitmap != null){
            // 图片下载完成后缓存都LrcCache中
            mImageLoader.addBitmapToMemoryCache(params[0], bitmap);
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        // 根据Tag找到相应的ImageView控件
        ImageView imageView = (ImageView) mView.findViewWithTag(mImageUrl);
        if (imageView != null){
            // 加载成功显示图片
            if (bitmap != null){
                imageView.setImageBitmap(bitmap);
            } else {        // 加载失败显示图片
                if (mImageLoader.loadFailBitmap != null){
                    imageView.setImageBitmap(mImageLoader.loadFailBitmap);
                }
            }
        }
        // 从任务集合中移除当前任务
        mImageLoader.taskCollection.remove(this);
    }


    private Bitmap downloadBitmap(String imageUrl){
        Bitmap bitmap = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout( 5 * 1000);
            connection.setReadTimeout(10 * 1000);
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }

        return bitmap;
    }
}

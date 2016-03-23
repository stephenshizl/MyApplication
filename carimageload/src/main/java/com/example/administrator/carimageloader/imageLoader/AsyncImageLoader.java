package com.example.administrator.carimageloader.imageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  异步加载图片工具类
 * Created by Administrator on 2015/10/23.
 */
public class AsyncImageLoader {
    private static AsyncImageLoader imagerLoader;
    private Context mContext;
    // 异步任务执行者
    private Executor mExecutor;
    // 加载任务结合
    public Set<BitmapWorkerTask> taskCollection;
    // 内存缓存
    public LruMemoryCache memoryCache;
    // 加载中显示图片
    public Bitmap loadingBitmap;
    // 加载失败显示图片
    public Bitmap loadFailBitmap;

    public static AsyncImageLoader getInstance(Context context){
        if (imagerLoader == null){
            imagerLoader = new AsyncImageLoader(context);
        }
        return  imagerLoader;
    }

    private AsyncImageLoader(Context context){
        mContext = context;
        //  初始化线程池核心3个线程， 大小为200
        mExecutor = new ThreadPoolExecutor(3, 200, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        // 初始化任务集合
        taskCollection = new HashSet<>();

        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        // 设置内存缓存大小为程序内存可用的1/8
        int cacheSize = maxMemory / 8;
        memoryCache = new LruMemoryCache(cacheSize);
    }


    /**
     *  加载bitmap对象
     *   此方法会在memoryCache中查找指定url地址的bitmap对象， 如果返回null 就会开启异步线程去硬盘缓存中查找，
     *   如果还是返回null就会从新下载图片。
     * @param view 显示图片控件的视图
     * @param imageView 显示图片的控件
     * @param imageUrl   图片地址url
     */
    public void loadBitmaps(View view, ImageView imageView, String imageUrl){
        if (imageView != null && loadingBitmap != null){
            imageView.setImageBitmap(loadingBitmap);
        }

        try {
            Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
            if (bitmap == null){
                BitmapWorkerTask task =new BitmapWorkerTask(imagerLoader, view);
                taskCollection.add(task);
                task.executeOnExecutor(mExecutor, imageUrl);
            } else {
                if (imageView != null && bitmap != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  设置加载图片
     * @param resourceId
     */
    public void setLoadingDrawable(int resourceId){
        loadingBitmap = BitmapFactory.decodeResource(mContext.getResources(), resourceId);
    }

    /**
     *  设置加载失败显示图片
     * @param resourceId
     */
    public void setLoadFailDrawable(int resourceId){
        loadingBitmap = BitmapFactory.decodeResource(mContext.getResources(), resourceId);
    }

    /**
     *  将一张图片存储懂啊memoryCache中
     * @param key
     * @param bitmap
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap){
        if (getBitmapFromMemoryCache(key) == null){
            memoryCache.put(key, bitmap);
        }
    }

    /**
     *  从memoryCache中获取一张图片，如果不存在就返回null
     * @param imageUrl
     * @return
     */
    private Bitmap getBitmapFromMemoryCache(String imageUrl) {
        return memoryCache.get(imageUrl);
    }

    /**
     *  取消所用正在下载或者等待下载的任务
     */
    public void cancelAllTasks(){
        if (taskCollection != null){
            for (BitmapWorkerTask task : taskCollection){
                task.cancel(false);
            }
        }
    }

}

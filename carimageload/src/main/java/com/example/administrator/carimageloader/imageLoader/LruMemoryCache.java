package com.example.administrator.carimageloader.imageLoader;

import android.graphics.Bitmap;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/24.
 */
public class LruMemoryCache implements MemoryCacheAware<String,Bitmap> {


    // LRU 缓存
    private LinkedHashMap<String, Bitmap> cache;
    // 最大缓存空间
    private  int maxSize;
    // 当前缓存空间
    private int currentSize;

    public LruMemoryCache(int maxSize) {
        if (maxSize <= 0) throw new IllegalArgumentException("maxSize <= 0");

        this.maxSize = maxSize;
        // 初始化队列按照访问顺序从少到多排列
        this.cache = new LinkedHashMap<>(0, 0.75f, true);
    }

    /**
     *      将图片放入链表
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean put(String key, Bitmap value) {
        if (key == null || value == null) throw new NullPointerException("key == null || value == null");

        synchronized (this){
            currentSize += sizeOf(key, value);
            Bitmap previous = cache.put(key, value);
            if (previous != null) currentSize -=sizeOf(key, previous);
        }

        trimToSize(maxSize);
        return true;
    }

    /**
     *  从链表中获取指定key对应的图片
     * @param key
     * @return
     */
    @Override
    public Bitmap get(String key) {
        if (key == null) throw new NullPointerException("key == null");

        synchronized (this){
            return cache.get(key);
        }
    }

    /**
     * 从链表中移除指定key对应的图片
     * @param key
     * @return
     */
    @Override
    public void remove(String key) {
        if (key == null) throw new NullPointerException("key == null");

        synchronized (this){
            Bitmap previous = cache.remove(key);
            if (previous != null) currentSize -= sizeOf(key, previous);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public void clear() {
        if ( !cache.isEmpty()) cache.clear();
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<String> keys() {
        return null;
    }

    /**
     *  把最近最少使用的对象在缓存值达到预定值之前从内存中移除
     * @param maxSize
     */
    private void trimToSize(int maxSize){
        while (true){
            String key = "";
            Bitmap value = null;
            if (currentSize < 0 || (cache.isEmpty() && currentSize != 0))
                throw new IllegalStateException(getClass().getName() + ".sizeOf is reporting inconsistent");

            if (currentSize <= maxSize || cache.isEmpty()) break;

            Map.Entry<String, Bitmap> toEvict = cache.entrySet().iterator().next();
            if (toEvict == null) break;

            key = toEvict.getKey();
            value = toEvict.getValue();
            cache.remove(key);
            currentSize -= sizeOf(key, value);
        }
    }

    /**
     *  返回指定图片的大小
     * @param key
     * @param value
     * @return
     */
    private int sizeOf(String key, Bitmap value){
        return value.getRowBytes() * value.getHeight();
    }

    public synchronized final String toString(){
        return String.format("LruCache[maxSize=%d]", maxSize);
    }
}

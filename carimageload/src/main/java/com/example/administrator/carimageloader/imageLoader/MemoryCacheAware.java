package com.example.administrator.carimageloader.imageLoader;

import java.util.Collection;

/**
 *  内存缓存
 * Created by Administrator on 2015/10/24.
 */
public interface MemoryCacheAware<K, V>{
    // 存储
    boolean put (K key, V value);

    // 获取
    V get(K key);

    // 移除
    void remove(K key);

    // 清空
    void clear();

    // 返回所有键名
    Collection<K> keys();

}

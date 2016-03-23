package com.yxhuang.carapplication.car.utils;

import android.content.Context;
import android.os.Environment;

/**
 * Created by Administrator on 2015/10/10.
 */
public class JFileKit {


    /**
     *  根据传入的context 获取硬盘缓存的路径地址
     * @param context
     * @return
     */
    public static String getDiskCacheDir(Context context){
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()){
            cachePath = context.getExternalCacheDir().getAbsolutePath();
        } else {
            cachePath = context.getCacheDir().getAbsolutePath();
        }
        return cachePath;
    }


    /**
     *  在SDCard 创建目录或者文件
     * @param path
     * @return
     */
//    public static String createFileOnSDCard(String path){
//        if (! sdcardIsReadyForWrite()){
//            throw new BaseException("SD 卡不可写");
//        }
//        return null;
//    }
}

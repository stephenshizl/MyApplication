package com.example.administrator.other;

import android.os.AsyncTask;

import java.net.URL;

/**
 *     使用 AsyncTask 的一个例子，由于后台下载
 * Created by Administrator on 2016/1/23.
 */
public class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
    @Override
    protected Long doInBackground(URL... urls) {
//        int count = urls.length;
//        long totalSize= 0;
//        for (int i = 0; i < count; i++){
//            totalSize += Downloader.downloadFile(urls[i]);
//            publishProgress((int)(i / (float)count) * 100);
//            if (isCancelled()){
//                break;
//            }
//        }
        return null;
    }

    // 更新进程
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    // 最后在主线程中执行
    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
    }
}

package com.example.administrator.downloaddemo;

import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.golshadi.majid.core.DownloadManagerPro;
import com.golshadi.majid.report.listener.DownloadManagerListener;

import java.io.IOException;

/**
 *  使用Github 上的下载库研究
 *
 *  https://github.com/majidgolshadi/Android-Download-Manager-Pro
 */
public class MainActivity extends AppCompatActivity implements DownloadManagerListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        startDownload();
    }


    private void startDownload(){

        Toast.makeText(this, "启动下载", Toast.LENGTH_SHORT).show();

        DownloadManagerPro downloadManager = new DownloadManagerPro(this);

        downloadManager.init("downloadManager/", 12, this);

        int taskToekn = downloadManager.addTask("save_name", "http://img.meilishuo.net/css/images/AndroidShare/Meilishuo_3.6.1_10006.apk", false, false);

        try {
            downloadManager.startDownload(taskToekn);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void OnDownloadStarted(long taskId) {
        Looper.prepare();
        Toast.makeText(this, "下载开始", Toast.LENGTH_SHORT).show();
       Looper.loop();
    }

    @Override
    public void OnDownloadPaused(long taskId) {

    }

    @Override
    public void onDownloadProcess(long taskId, double percent, long downloadedLength) {

    }

    @Override
    public void OnDownloadFinished(long taskId) {
        Toast.makeText(this, "下载完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnDownloadRebuildStart(long taskId) {

    }

    @Override
    public void OnDownloadRebuildFinished(long taskId) {

    }

    @Override
    public void OnDownloadCompleted(long taskId) {

    }

    @Override
    public void connectionLost(long taskId) {

    }
}

package com.yxhuang.listview.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yxhuang.listview.R;
import com.yxhuang.listview.Service.MeidaTranscoder;
import com.yxhuang.listview.Service.UploadImageIntentService;

public class UploadImageActivity extends AppCompatActivity {

    public static  final  String ACTION_UPLOAD_IMAGE_RESULT = "com.yxhuang.listview.activity.upload_image_result";

    private Button mButton;
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        mButton = (Button) findViewById(R.id.button);
        mTextView  = (TextView) findViewById(R.id.text);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImageIntentService.startUploadImage(UploadImageActivity.this, "路径1~~~");
            }
        });

        findViewById(R.id.btn_start_MediaTranacoder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadImageActivity.this, MeidaTranscoder.class);
                intent.setAction(MeidaTranscoder.ACTION_TRANSCODE_MEDIA);
                intent.putExtra(MeidaTranscoder.EXTRA_OUTPUT_TYPE, "呵呵呵呵");
                startService(intent);
                Log.i("yxh", "启动 MeidaTranscoder");
            }
        });
    }

    /**
     * 上传完图片的BroadcastReceiver
     */
    private BroadcastReceiver upLoadImageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(ACTION_UPLOAD_IMAGE_RESULT));
                String path = intent.getStringExtra(UploadImageIntentService.EXTRA_IMAGE_PATH);
                handleResult(path);
        }
    };

    private void handleResult(String string){
        mTextView.setText("图片上传完成： " + string);
        Log.i("yxh", "图片上传完成");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPLOAD_IMAGE_RESULT);
        registerReceiver(upLoadImageReceiver, filter);
        Log.i("yxh", "注册广播");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(upLoadImageReceiver);
        Log.i("yxh", "注销广播");
    }
}

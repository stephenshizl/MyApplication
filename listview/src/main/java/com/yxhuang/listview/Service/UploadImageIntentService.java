package com.yxhuang.listview.Service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yxhuang.listview.Activity.UploadImageActivity;

public class UploadImageIntentService extends IntentService {

    public static final String ACTION_UPLOAD_IMAGE = "com.yxhuang.listview.Service.action.upload_image";
    public static final String EXTRA_IMAGE_PATH= "com.yxhuang.listview.Service.action.image_path";

    /**
     *  启动Service
     * @param context   上下文
     * @param path  要上传图片的路径
     */
    public static void startUploadImage(Context context , String path){
        Intent intent = new Intent(context, UploadImageIntentService.class);
        intent.setAction(ACTION_UPLOAD_IMAGE);
        intent.putExtra(EXTRA_IMAGE_PATH, path);
        context.startService(intent);

    }

    public UploadImageIntentService() {
        super("UploadImageIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null){
            String action = intent.getAction();
            if (action != null &&  action.equals(ACTION_UPLOAD_IMAGE)){
                String path = intent.getStringExtra(EXTRA_IMAGE_PATH);
                handleUploadImage(path);
            }
        }
    }

    private void handleUploadImage(String path){
        if (path == null) return;

        try {
            // 线程沉睡3秒，模拟上次图片
            Thread.sleep(3 * 1000);


            // 图片上次完成，发送广播
            Intent intent = new Intent(UploadImageActivity.ACTION_UPLOAD_IMAGE_RESULT);
            intent.putExtra(EXTRA_IMAGE_PATH, path);
            sendBroadcast(intent);

            Log.i("yxh", "上传完图片，发送广播");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("yxh", "启动Service");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("yxh", "注销Service");
    }
}

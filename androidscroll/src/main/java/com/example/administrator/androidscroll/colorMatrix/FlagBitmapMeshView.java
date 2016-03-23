package com.example.administrator.androidscroll.colorMatrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.androidscroll.R;

/**
 * Created by Administrator on 2015/12/13.
 */
public class FlagBitmapMeshView extends View {
    private static int HEIGHT = 200;
    private static int WIDTH = 200;
    private int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    private float[] orig = new float[COUNT * 2];
    private float[] verts = new float[COUNT * 2];

    private Bitmap mBitmap;

    private float A;
    private float k = 1;

    public FlagBitmapMeshView(Context context) {
        super(context);
        initView();
    }

    public FlagBitmapMeshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FlagBitmapMeshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        flagWave();
        k += 0.1f;
        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
        invalidate();
    }

    // 初始化视图
    private void initView(){
        setFocusable(true);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);

        float bitmapWith = mBitmap.getWidth();
        float bitmapHeight = mBitmap.getHeight();
        int index = 0;
        for (int y = 0; y <= HEIGHT; y ++){
            float fy = bitmapHeight * y / HEIGHT;
            for (int x = 0; x <= WIDTH; x ++){
                float fx = bitmapWith * x / WIDTH;
                orig[index * 2 + 0] = verts[index * 2 + 0] = fx;
                // 这里人为将坐标+100，是为了让图像下移，避免扭曲后被屏幕遮挡
                orig[index * 2 + 1] = verts[index * 2 + 1] = fy + 100;
                index +=1;
            }
        }
        A = 50;
    }

    // 实现旗帜飘扬的效果
    private void flagWave(){
        for (int j = 0; j <= HEIGHT; j ++){
            for (int i = 0; i <= WIDTH; i ++){
                verts[(j * (WIDTH + 1) + i) * 2 + 0] +=0;
                float offsetY = (float) Math.sin((float)i / WIDTH * 2 * Math.PI + Math.PI * k);
                verts[(j * (WIDTH  + 1) + i) * 2 + 1] = orig[(j * WIDTH + i) * 2 + 1] + offsetY * A;
            }
        }
    }

}

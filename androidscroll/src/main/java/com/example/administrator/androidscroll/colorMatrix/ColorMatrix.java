package com.example.administrator.androidscroll.colorMatrix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.administrator.androidscroll.R;

public class ColorMatrix extends AppCompatActivity  implements View.OnClickListener{

    private ImageView mImageView;
    private GridLayout mGroup;
    private Button btn_change, btn_reset;

    private Bitmap mBitmap;

    private int mEtWith, mEtHeight;
    private EditText[] mEts = new EditText[20];
    private float[] mColorMatrix = new float[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.moon);
        mImageView = (ImageView) findViewById(R.id.imageview);
        mGroup = (GridLayout) findViewById(R.id.group);
        btn_change = (Button) findViewById(R.id.btn_change);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        mImageView.setImageBitmap(mBitmap);

        btn_change.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

        // 创建颜色矩阵
        mGroup.post(new Runnable() {
            @Override
            public void run() {
                // 获取宽高信息
                mEtHeight = mGroup.getHeight() / 4;
                mEtWith = mGroup.getWidth() / 5;

                addEts();
                initMatrix();
            }
        });
    }

    // 添加EditText
    private void addEts(){
        for (int i = 0; i < 20; i++){
            EditText editText = new EditText(ColorMatrix.this);
            mEts[i] = editText;
            mGroup.addView(editText, mEtWith, mEtHeight);
        }
    }

    // 初始化颜色矩阵为初始状态
    private void initMatrix(){
        for (int i = 0; i < 20; i ++){
            if (i % 6 == 0){
                mEts[i].setText(String.valueOf(1));
            } else {
                mEts[i].setText(String.valueOf(0));
            }
        }
    }

    // 获取矩阵值
    private void getMatrix(){
        for (int i = 0; i < 20; i++){
            mColorMatrix[i] = Float.valueOf(mEts[i].getText().toString());
        }
    }

    // 将矩阵值设置到图像
    private void setImageMatrix(){
        Bitmap bmp = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        android.graphics.ColorMatrix colorMatrix = new android.graphics.ColorMatrix();

        colorMatrix.set(mColorMatrix);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        mImageView.setImageBitmap(mBitmap);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 作用矩阵效果
            case R.id.btn_change:
                getMatrix();
                setImageMatrix();
                break;

            // 重置矩阵效果
            case R.id.btn_reset:
                initMatrix();
                getMatrix();
                setImageMatrix();
                break;

            default:
                break;
        }
    }

    //
}

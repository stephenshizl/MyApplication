package com.example.administrator.androidscroll.colorMatrix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import com.example.administrator.androidscroll.R;

public class ImageMatrixViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageMatrixView mImageMatrixView;
    private Button btn_change, btn_reset;
    private GridLayout mGroup;
    private Bitmap mBitmap;
    private int mEtWidth = 0;
    private int mEtHeight = 0;
    private float[] mImageMatrix = new float[9];
    private EditText[] mETs = new EditText[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_matrix_view);

        mImageMatrixView = (ImageMatrixView) findViewById(R.id.iv_matrix_view);
        mGroup = (GridLayout) findViewById(R.id.group);
        btn_change = (Button) findViewById(R.id.btn_matrix_change);
        btn_reset = (Button) findViewById(R.id.btn_matrix_reset);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        btn_change.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

        mGroup.post(new Runnable() {
            @Override
            public void run() {
                // 获取宽高
                mEtWidth = mGroup.getWidth() / 3;
                mEtHeight = mGroup.getHeight() / 3;

                addEts();
                initImageMatrix();

            }
        });
    }

    // 添加Edttext
    private void addEts(){
        for (int i = 0; i < 9; i++){
            EditText et = new EditText(ImageMatrixViewActivity.this);
            et.setGravity(Gravity.CENTER);
            mETs[i] = et;
            mGroup.addView(et, mEtWidth, mEtHeight);
        }
    }

    // 获取设置后矩阵的值
    private void getImageMatrix(){
        for (int i = 0; i < 9; i++){
            EditText et = mETs[i];
            mImageMatrix[i] = Float.valueOf(et.getText().toString());
        }
    }

    // 初始化矩阵的值
    private void initImageMatrix(){
        for (int i = 0; i < 9; i++){
            if (i % 4 == 0){
                mETs[i].setText(String.valueOf(1));
            } else {
                mETs[i].setText(String.valueOf(0));
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_matrix_change:
                getImageMatrix();
                Matrix matrix = new Matrix();
                matrix.setValues(mImageMatrix);

                matrix.setRotate(45);
                matrix.postTranslate(200, 200);


                mImageMatrixView.setImageAndMatrix(mBitmap, matrix);
                mImageMatrixView.invalidate();
                break;

            case R.id.btn_matrix_reset:
                initImageMatrix();
                getImageMatrix();
                Matrix matrix1 = new Matrix();
                matrix1.setValues(mImageMatrix);
                mImageMatrixView.setImageAndMatrix(mBitmap, matrix1);
                mImageMatrixView.invalidate();
                break;

            default:
                break;
        }

    }
}

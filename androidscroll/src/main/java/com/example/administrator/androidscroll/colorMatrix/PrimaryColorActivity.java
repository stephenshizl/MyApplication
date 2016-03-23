package com.example.administrator.androidscroll.colorMatrix;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.administrator.androidscroll.R;

/**
 * Created by Administrator on 2015/12/7.
 */
public class PrimaryColorActivity extends Activity  implements SeekBar.OnSeekBarChangeListener{

    private static int MAX_VALUE = 255;
    private static int MID_VALUE = 127;
    private ImageView mImageView;
    private SeekBar mSeekbarhue, mSeekBarStaturation, mSeekBarLum;
    private float mHue, mStauration, mLum;
    private Bitmap mBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_color);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.moon);

        mImageView = (ImageView) findViewById(R.id.imageview);
        mSeekbarhue = (SeekBar) findViewById(R.id.seekbarHue);
        mSeekBarStaturation = (SeekBar) findViewById(R.id.seekbarSaturation);
        mSeekBarLum = (SeekBar) findViewById(R.id.seekbatLum);

        mSeekbarhue.setOnSeekBarChangeListener(this);
        mSeekBarStaturation.setOnSeekBarChangeListener(this);
        mSeekBarLum.setOnSeekBarChangeListener(this);

        mSeekbarhue.setMax(MAX_VALUE);
        mSeekBarStaturation.setMax(MAX_VALUE);
        mSeekBarLum.setMax(MAX_VALUE);
        mSeekbarhue.setProgress(MID_VALUE);
        mSeekBarStaturation.setProgress(MID_VALUE);
        mSeekBarLum.setProgress(MID_VALUE);

        // 底片效果
//        mImageView.setImageBitmap(ImageHelper.handleImageNegative(mBitmap));

        // 老照片效果
//        mImageView.setImageBitmap(ImageHelper.handleImagePixeleOldPhoto(mBitmap));

        // 浮雕效果
        mImageView.setImageBitmap(ImageHelper.handleImagePixelsRelief(mBitmap));

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.seekbarHue:
                mHue = (progress - MID_VALUE) * 1.0F / MID_VALUE;
                break;

            case R.id.seekbarSaturation:
                mStauration = progress * 1.0F / MID_VALUE;
                break;

            case R.id.seekbatLum:
                mLum = progress * 1.0F / MID_VALUE;
                break;
        }

        mImageView.setImageBitmap(ImageHelper.handleImageEffect(mBitmap, mHue, mStauration, mLum));

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

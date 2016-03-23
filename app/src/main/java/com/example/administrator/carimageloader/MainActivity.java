package com.example.administrator.carimageloader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static final int MAX = 30;
    private TextView mTextView;

    private Button mButton;
    private ProgressBar mProgressBar;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.tv_textview);

        mButton = (Button) findViewById(R.id.btn);
        CharSequence text = "什么鬼啊，今天这么好的天气！！！";

        mTextView.setText(text);

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);

        ImageSpan imageSpan = new ImageSpan(this, R.mipmap.ic_audience_inform);


        stringBuilder.setSpan(imageSpan, 0, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTextView.append(stringBuilder);


        showProgress();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mProgressBar.setProgress(mProgressBar.getProgress() + 5);
//
//                if (mProgressBar.getProgress() >= mProgressBar.getMax()) mProgressBar.setProgress(0);
//
//                Intent intent = new Intent(MainActivity.this, SecondDisplayDemo.class);
//                startActivity(intent);
//
                Intent intent = new Intent();
                String data = "http://www.baidu.com";
                Uri uri = Uri.parse(data);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        showSeekBar();

    }
    private void showProgress(){
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void showSeekBar(){
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

}

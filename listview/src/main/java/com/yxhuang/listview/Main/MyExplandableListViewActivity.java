package com.yxhuang.listview.Main;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.yxhuang.listview.R;


public class MyExplandableListViewActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory {

    private ImageSwitcher mImageSwitcher;
    private Button btnSwitch;
    private int mInt = 0;

    private int[]  mImageViews = {
            R.mipmap.box_bs_normal,
            R.mipmap.box_bs_pressed,
            R.mipmap.box_car_store_normal,
            R.mipmap.box_car_store_pressed};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_explandable_list_view);

        mImageSwitcher = (ImageSwitcher) findViewById(R.id.my_imageSwitcher);
        btnSwitch = (Button) findViewById(R.id.btn_switcher);

        mImageSwitcher.setFactory(this);

        mImageSwitcher.setImageResource(mImageViews[mInt]);


        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++ mInt ;
                mImageSwitcher.setImageResource(mImageViews[mInt]);
                toggleActivities();

            }
        });

    }

    // 选择主Activity
    private void toggleActivities(){
        PackageManager manager = getPackageManager();

        // 启动主Activity
        manager.setComponentEnabledSetting(new ComponentName(this, TestActivity.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        // 禁止主Activity
        manager.setComponentEnabledSetting(new ComponentName(this, MyExplandableListViewActivity.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

    }

    @Override
    public View makeView() {
        return new ImageView(MyExplandableListViewActivity.this);
    }

    private void openAssetFlie(){
        AssetManager manager = getAssets();

    }
}

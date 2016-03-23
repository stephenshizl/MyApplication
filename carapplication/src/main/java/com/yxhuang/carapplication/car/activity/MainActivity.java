package com.yxhuang.carapplication.car.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yxhuang.carapplication.R;
import com.yxhuang.carapplication.car.fragment.ComnunityFragment;
import com.yxhuang.carapplication.car.fragment.MineFragment;
import com.yxhuang.carapplication.car.fragment.NewsFragment;
import com.yxhuang.carapplication.car.fragment.SelectCarFragment;

public class MainActivity extends BaseActivity {
    private static final String  TAG = "MainActivity";


    // 头条
    @ViewInject(R.id.main_tv_news)
    TextView main_tv_news;
    // 社区
    @ViewInject(R.id.main_tv_community)
    TextView main_tv_community;
    // 选车
    @ViewInject(R.id.main_tv_select_car)
    TextView main_tv_select_car;
    // 我的
    @ViewInject(R.id.main_tv_mine)
    TextView main_tv_mine;


    private int chooseIndex = -1;
    private boolean isRecycle  = false;     // 屏幕旋转后，是否记住参数


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)      viewOnClick(main_tv_news);
    }

    // 屏幕旋转后，保存原有参数
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRecycle", true);
        outState.putInt("chooseIndex", chooseIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRecycle)  tabBgChange(chooseIndex);
    }

    // 控件点击事件
    @OnClick({R.id.main_tv_news, R.id.main_tv_community, R.id.main_tv_select_car, R.id.main_tv_mine})
    public void viewOnClick(View v){
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.main_tv_news:
                Log.i(TAG,  "选中了头条");
                ft.replace(R.id.car_flyt_content, NewsFragment.instantiate(MainActivity.this, NewsFragment.class.getName(), null), "newsFragment");
                chooseIndex = 0;
                tabBgChange(chooseIndex);
                break;

            case R.id.main_tv_community:
                Log.i(TAG,  "选中了社区");
                ft.replace(R.id.car_flyt_content, ComnunityFragment.instantiate(MainActivity.this, ComnunityFragment.class.getName(), null), "communityFragment");
                chooseIndex = 1;
                tabBgChange(chooseIndex);
                break;

            case R.id.main_tv_select_car:
                Log.i(TAG,  "选中了选车");
                ft.replace(R.id.car_flyt_content, SelectCarFragment.instantiate(MainActivity.this, SelectCarFragment.class.getName(), null), "selectCarFragment");
                chooseIndex = 2;
                tabBgChange(chooseIndex);
                break;

            case R.id.main_tv_mine:
                Log.i(TAG, "选中了我的");
                ft.replace(R.id.car_flyt_content, MineFragment.instantiate(MainActivity.this, MineFragment.class.getName(), null), "mineFragment");
                chooseIndex = 3;
                tabBgChange(chooseIndex);
                break;

            default:
                Log.i(TAG,  "选中了default");
                break;
        }

        ft.commit();
    }


    @SuppressWarnings("deprecation")
    private void tabBgChange(int index){
        Resources r = getResources();
        switch (index) {
            case 0:         // 头条
                main_tv_news.setSelected(true);
                main_tv_news.setTextColor(r.getColor(R.color.car_cl_choose));
                main_tv_community.setSelected(false);
                main_tv_community.setTextColor(r.getColor(R.color.car_cl_unchoose));
                main_tv_select_car.setSelected(false);
                main_tv_select_car.setTextColor(r.getColor(R.color.car_cl_unchoose));
                main_tv_mine.setSelected(false);
                main_tv_mine.setTextColor(r.getColor(R.color.car_cl_unchoose));
                break;

            case 1:         // 社区
                main_tv_news.setSelected(false);
                main_tv_news.setTextColor(r.getColor(R.color.car_cl_unchoose));
                main_tv_community.setSelected(true);
                main_tv_community.setTextColor(r.getColor(R.color.car_cl_choose));
                main_tv_select_car.setSelected(false);
                main_tv_select_car.setTextColor(r.getColor(R.color.car_cl_unchoose));
                main_tv_mine.setSelected(false);
                main_tv_mine.setTextColor(r.getColor(R.color.car_cl_unchoose));
                break;


            case 2:     // 选车
                main_tv_news.setSelected(false);
                main_tv_news.setTextColor(r.getColor(R.color.car_cl_unchoose));
                main_tv_community.setSelected(false);
                main_tv_community.setTextColor(r.getColor(R.color.car_cl_unchoose));
                main_tv_select_car.setSelected(true);
                main_tv_select_car.setTextColor(r.getColor(R.color.car_cl_choose));
                main_tv_mine.setSelected(false);
                main_tv_mine.setTextColor(r.getColor(R.color.car_cl_unchoose));
                break;

            case 3:            // 我的
                main_tv_news.setSelected(false);
                main_tv_news.setTextColor(r.getColor(R.color.car_cl_unchoose));
                main_tv_community.setSelected(false);
                main_tv_community.setTextColor(r.getColor(R.color.car_cl_unchoose));
                main_tv_select_car.setSelected(false);
                main_tv_select_car.setTextColor(r.getColor(R.color.car_cl_unchoose));
                main_tv_mine.setSelected(true);
                main_tv_mine.setTextColor(r.getColor(R.color.car_cl_choose));
                break;

            default:
                break;
        }
    }

}

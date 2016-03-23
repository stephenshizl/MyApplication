package com.yxhuang.carapplication.car.fragment;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yxhuang.carapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/18.
 */
public class NewsFragment extends BaseFragment {
    private static final String TAG = "NewsFragment";

    // 要闻
    @ViewInject(R.id.new_main_news)
    TextView new_main_news;
    // 测试
    @ViewInject(R.id.new_car_test)
    TextView new_car_test;
    // 说车
    @ViewInject(R.id.new_about_car)
    TextView new_about_car;
    // 视频
    @ViewInject(R.id.new_car_video)
    TextView new_car_video;

    // Viewpager
    @ViewInject(R.id.pager)
    ViewPager news_pager;

    // 构建Fragment
    private List<Fragment>  mFragmentList = new ArrayList<>();
    private MainNewsFragment mainNewsFragment;
    private TestFragment testFragment;
    private VidieoFragment vidieoFragment;
    private AboutCarFragment aboutCarFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }


    // 初始化参数
    @Override
    protected void initParams() {
        mainNewsFragment = new MainNewsFragment();
        testFragment = new TestFragment();
        vidieoFragment = new VidieoFragment();
        aboutCarFragment = new AboutCarFragment();
        mFragmentList.add(mainNewsFragment);
        mFragmentList.add(testFragment);
        mFragmentList.add(aboutCarFragment);
        mFragmentList.add(vidieoFragment);

        news_pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });

        news_pager.addOnPageChangeListener(new DefineOnPageChangeListener());
        news_pager.setCurrentItem(0);

    }

    @OnClick({R.id.new_main_news, R.id.new_car_test, R.id.new_about_car, R.id.new_car_video})
    public void viewOnClick(View v){
        switch (v.getId()){
            case R.id.new_main_news:
                news_pager.setCurrentItem(0);
                break;

            case R.id.new_car_test:
                news_pager.setCurrentItem(1);
                break;

            case R.id.new_about_car:
                news_pager.setCurrentItem(2);
                break;

            case R.id.new_car_video:
                news_pager.setCurrentItem(3);
                break;
        }
    }

    /**
     *  内部适配器类
     */
    private class DefineOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Resources resources = getResources();
            switch (position){
                case 0:     // 要闻
                    new_main_news.setTextColor(resources.getColor(R.color.car_cl_choose));
                    new_car_test.setTextColor(resources.getColor(R.color.car_cl_unchoose));
                    new_about_car.setTextColor(resources.getColor(R.color.car_cl_unchoose));
                    new_car_video.setTextColor(resources.getColor(R.color.car_cl_unchoose));
                    break;

                case 1:  // 测试
                    new_main_news.setTextColor(resources.getColor(R.color.car_cl_unchoose));
                    new_car_test.setTextColor(resources.getColor(R.color.car_cl_choose));
                    new_about_car.setTextColor(resources.getColor(R.color.car_cl_unchoose));
                    new_car_video.setTextColor(resources.getColor(R.color.car_cl_unchoose));
                    break;

                case 2:      // 说车
                    new_main_news.setTextColor(resources.getColor(R.color.car_cl_unchoose));
                    new_car_test.setTextColor(resources.getColor(R.color.car_cl_unchoose));
                    new_about_car.setTextColor(resources.getColor(R.color.car_cl_choose));
                    new_car_video.setTextColor(resources.getColor(R.color.car_cl_unchoose));
                    break;

                case 3:       // 视频
                    new_main_news.setTextColor(resources.getColor(R.color.car_cl_unchoose));
                    new_car_test.setTextColor(resources.getColor(R.color.car_cl_unchoose));
                    new_about_car.setTextColor(resources.getColor(R.color.car_cl_unchoose));
                    new_car_video.setTextColor(resources.getColor(R.color.car_cl_choose));
                    break;

                default:
                    break;
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}

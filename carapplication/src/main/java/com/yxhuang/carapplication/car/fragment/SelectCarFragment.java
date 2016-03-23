package com.yxhuang.carapplication.car.fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yxhuang.carapplication.R;

/**
 * Created by Administrator on 2015/10/18.
 */
public class SelectCarFragment extends BaseFragment {

    /**相关控件*/
    @ViewInject(R.id.fragment_find_tv_brand)
    TextView fragment_find_tv_brand;
    @ViewInject(R.id.fragment_find_tv_filter)
    TextView fragment_find_tv_filter;

    private FragmentTransaction mFragmentTransaction;
    private FindBrandFragment mFindBrandFragment;
    private FindFilterFragment mFindFilterFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_car;
    }

    @Override
    protected void initParams() {
        viewOnClick(fragment_find_tv_brand);

    }

    /**控件点击事件*/
    @OnClick({R.id.fragment_find_tv_brand, R.id.fragment_find_tv_filter})
    public void viewOnClick(View view){
        mFragmentTransaction = getChildFragmentManager().beginTransaction();

        switch (view.getId()){
            /**品牌找车*/
            case R.id.fragment_find_tv_brand:
                fragment_find_tv_brand.setTextColor(getResources().getColor(R.color.find_car_header_tv_pressed));
                fragment_find_tv_filter.setTextColor(getResources().getColor(R.color.find_car_header_tv_unpressed));
                if (mFindBrandFragment == null) mFindBrandFragment = new FindBrandFragment();
                mFragmentTransaction.replace(R.id.fragment_find_flyt_content, mFindBrandFragment);
                break;

            /**精准找车*/
            case R.id.fragment_find_tv_filter:
                fragment_find_tv_brand.setTextColor(getResources().getColor(R.color.find_car_header_tv_unpressed));
                fragment_find_tv_filter.setTextColor(getResources().getColor(R.color.find_car_header_tv_pressed));
                if (mFindFilterFragment == null) mFindFilterFragment = new FindFilterFragment();
                mFragmentTransaction.replace(R.id.fragment_find_flyt_content, mFindFilterFragment);
                break;

            default:
                break;

        }
      mFragmentTransaction.commit();

    }
}

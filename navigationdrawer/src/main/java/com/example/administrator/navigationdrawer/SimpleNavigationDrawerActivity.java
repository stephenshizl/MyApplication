package com.example.administrator.navigationdrawer;

import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;

public class SimpleNavigationDrawerActivity extends AppCompatActivity {

    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simple_navigation_drawer);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        // 设置菜单图标恢复原来的颜色
        mNavigationView.setItemIconTintList(null);

        removeNavigationViewScrollbar(mNavigationView);
    }


    // 隐藏NavigationView  的滚动条
    private void removeNavigationViewScrollbar(NavigationView navigationView){
        if (navigationView != null){
            NavigationMenuView navigationMenuView =  (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null){
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }
}

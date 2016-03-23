package com.yxhuang.carapplication.car.utils;

import android.app.Activity;

import java.util.Stack;

/**
 *  Activity 堆栈管理
 * Created by Administrator on 2015/10/10.
 */
public class ActivityStack {
    private static ActivityStack mSingleInstance;
    private Stack<Activity> mActivityStack;

    private  ActivityStack(){
        mActivityStack = new Stack<>();
    }

    public static ActivityStack getInstance(){
        if (null == mSingleInstance){
            mSingleInstance = new ActivityStack();
        }
        return  mSingleInstance;
    }

    public Stack<Activity> getStack(){
        return mActivityStack;
    }

    /**
     *  入栈
     * @param activity
     */
    public void addActivity(Activity activity){
        mActivityStack.push(activity);
    }

    /**
     *  删除Activity
     * @param activity
     */
    public void removeActivity(Activity activity){
        mActivityStack.remove(activity);
    }


}

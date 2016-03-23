package com.yxhuang.carapplication.car.activity;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.lidroid.xutils.ViewUtils;
import com.yxhuang.carapplication.car.callback.DialogCallBack;
import com.yxhuang.carapplication.car.utils.ActivityStack;
import com.yxhuang.carapplication.car.view.DialogMaker;

/**
 * Created by Administrator on 2015/10/10.
 */
public abstract class BaseActivity extends FragmentActivity implements DialogCallBack{

    protected Dialog mDialog;
    private boolean isCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(this);
        setContentView(getLayoutId());
        ViewUtils.inject(this);
        isCreate = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCreate){
            isCreate = false;
            initParams();
        }
    }

    protected synchronized void onDestroy(){
        dismissDialog();
        ActivityStack.getInstance().removeActivity(this);
        super.onDestroy();
    }

    /**
     *  显示等待对话框
     * @param msg   显示的内容
     * @param isCancelable        是否能取消
     * @param tag
     * @return
     */
    public Dialog showWaitDialog(String msg, boolean isCancelable, Object tag){
        if (mDialog == null || mDialog.isShowing())
            mDialog = DialogMaker.showCommentWaitDialog(this, msg, this, isCancelable, tag);

        return mDialog;
    }


    /**
     *  关闭对话框
     */
    public void dismissDialog(){
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    @Override
    public void onButtonClicked(Dialog dialog, int position, Object tag) {

    }

    @Override
    public void onCancelDialog(Dialog dialog, Object tag) {

    }

    /**
     *  初始化布局
     * @return
     */
    protected abstract int getLayoutId();


    /**
     *  初始化参数
     */
    protected abstract void initParams();
}



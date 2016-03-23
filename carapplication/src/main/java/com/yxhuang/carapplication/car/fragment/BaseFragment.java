package com.yxhuang.carapplication.car.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.yxhuang.carapplication.car.callback.DialogCallBack;
import com.yxhuang.carapplication.car.view.DialogMaker;

/**
 * Created by Administrator on 2015/10/10.
 */
public  abstract class BaseFragment extends Fragment {
    protected Context context;
    protected Dialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(getLayoutId(), container, false);
        ViewUtils.inject(this, view);
        initParams();
        return view;
    }


    /**
     *  初始化布局
     * @return
     */
    protected abstract int getLayoutId();

    /**
     *  参数设置
     */
    protected abstract void initParams();


    /**
     *  弹出对话框
     * @param title
     * @param msg
     * @param btns
     * @param callBack
     * @param isCanCancelabel
     * @param isDismiss
     * @return
     */
    public Dialog showAlertDialog(String title, String msg, String[] btns, DialogCallBack callBack, boolean isCanCancelabel, boolean isDismiss){
        if (dialog == null || !dialog.isShowing()){
            dialog = DialogMaker.showCommonAlerttDialog(context, title, msg, btns, callBack, isCanCancelabel, isDismiss);
        }
        return dialog;
    }

    /**
     *  等待对话框
     * @param msg
     * @param isCanCancelabel
     * @param tag
     * @return
     */
    public Dialog showWaitDialog(String msg, boolean isCanCancelabel, Object tag){
        if (dialog == null || !dialog.isShowing()){
            dialog = DialogMaker.showCommentWaitDialog(context, msg, null, isCanCancelabel, tag);
        }
        return dialog;
    }

    /**
     *   关闭对话框
     */
    public void dimissDialog(){
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }


}

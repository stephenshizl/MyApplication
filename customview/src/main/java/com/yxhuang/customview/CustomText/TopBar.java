package com.yxhuang.customview.CustomText;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxhuang.customview.R;

/**
 * Created by Administrator on 2015/11/18.
 */
public class TopBar extends RelativeLayout {
    private Context mContext;

    private String  mTitle;
    private String mLeftText;
    private String mRightText;

    private int mTitleTextColor;
    private int mLeftTextColor;
    private int mRightTextColor;

    private Drawable mLeftBackground;
    private Drawable mRightBackground;

    // 左按钮，右按钮， 标题
    private Button  mLeftButton;
    private Button mRightButton;
    private TextView mTitleTextView;

    private float mTitleTextSize;
    private float mLeftTextSize;
    private float mRightTextSize;

    // 布局属性，用来控制组件元素在ViewGroup中的位置
    private LayoutParams mLeftLayoutParams;
    private LayoutParams mRightLayoutParams;
    private LayoutParams mTitleLayoutParams;

    private topbarClickListener mTopbarClickListener;


    public TopBar(Context context) {
        super(context);
        mContext = context;
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        //获取atts.xml定义的属性值
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.ToBar);

        mTitle = ta.getString(R.styleable.ToBar_toBartitle);
        mLeftText = ta.getString(R.styleable.ToBar_leftText);
        mRightText = ta.getString(R.styleable.ToBar_rightText);


        mTitleTextColor = ta.getColor(R.styleable.ToBar_toBartitleTextColor, 0);
        mLeftTextColor = ta.getColor(R.styleable.ToBar_leftTextColor, 0);
        mRightTextColor = ta.getColor(R.styleable.ToBar_rightTextColor, 0);

        mLeftBackground = ta.getDrawable(R.styleable.ToBar_leftBackground);
        mRightBackground = ta.getDrawable(R.styleable.ToBar_rightBackground);

        mTitleTextSize = ta.getDimension(R.styleable.ToBar_leftTextSize, 10);
        mLeftTextSize = ta.getDimension(R.styleable.ToBar_leftTextSize, 10);
        mRightTextSize = ta.getDimension(R.styleable.ToBar_rightTextSize, 10);


        // 获取完TypedArray 的值后，一般调用recyle（）完成资源的回收
        ta.recycle();



        mTitleTextView = new TextView(mContext);
        mLeftButton = new Button(mContext);
        mRightButton = new Button(mContext);

        // 为创建的组件元素赋值，值来源arrt.xml 中对应属性的赋值
        mLeftButton.setText(mLeftText);
        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setTextSize(mLeftTextSize);
        mLeftButton.setBackgroundDrawable(mLeftBackground);

        mRightButton.setText(mRightText);
        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setTextSize(mRightTextSize);
        mRightButton.setBackgroundDrawable(mRightBackground);

        mTitleTextView.setText(mTitle);
        mTitleTextView.setTextColor(mTitleTextColor);
        mTitleTextView.setTextSize(mTitleTextSize);
        mTitleTextView.setGravity(Gravity.CENTER);

        // 为组件元素设置相应的布局文件
        mTitleLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mTitleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(mTitleTextView, mTitleLayoutParams);


        mLeftLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(mLeftButton, mLeftLayoutParams);

        mRightLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mRightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(mRightButton, mRightLayoutParams);


        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopbarClickListener.leftClick();
            }
        });
        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopbarClickListener.rightClick();
            }
        });

    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }


    /**
     *  回调接口
     * @param listener ToBarClickListener
     */
    public void setOnTopBarClickListener(topbarClickListener listener){
        mTopbarClickListener = listener;
    }


    /**
     *  设置按钮的显示与否通过id 区分按钮，flag 区分是否显示
     * @param id
     * @param flag 是否显示
     */
    public void setButtonVisiable(int id, boolean flag){
        if (flag){
            if (id == 0){
                mLeftButton.setVisibility(View.VISIBLE);
            } else {
                mRightButton.setVisibility(View.VISIBLE);
            }
        } else {
            if (id == 0){
                mLeftButton.setVisibility(View.GONE);
            } else {
                mRightButton.setVisibility(View.GONE);
            }
        }
    }


    /**
     *   接口对象，实现回调机制，在回调方法中，通过映射的接口对象调用接口中的方法，
     *   而不用扣率如何实现
     */
    public interface topbarClickListener{
        // 左按钮点击事件
        void leftClick();
        // 右按钮点击事件
        void rightClick();
    }
}

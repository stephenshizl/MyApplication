package com.yxhuang.listview.ViewPagerInListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yxhuang.listview.R;

import java.util.List;

/**
 * Created by Administrator on 2015/8/15.
 */
public class CustomTextView extends FrameLayout  implements View.OnClickListener{
    private TextView tv1, tv2, tv3;
    private List<String> mStrings;

    public CustomTextView(Context context) {
        super(context);
        intView(context);
    }
    public CustomTextView(Context context, List<String> lists) {
        super(context);
        mStrings = lists;
        intView(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intView(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intView(context);
    }

   private void intView(Context context){
       View view = inflate(context, R.layout.third_textview, this);
       tv1 = (TextView) view.findViewById(R.id.tv1);
       tv2 = (TextView) view.findViewById(R.id.tv2);
       tv3 = (TextView) view.findViewById(R.id.tv3);

       tv1.setOnClickListener(this);
       tv2.setOnClickListener(this);
       tv3.setOnClickListener(this);

       if ( !mStrings.isEmpty() && mStrings.size() >0 ){
           tv1.setText(mStrings.get(0));
           tv2.setText(mStrings.get(1));
           tv3.setText(mStrings.get(2));
       }
   }

    @Override
    public void onClick(View v) {

    }

    public  interface  CustomTextViewOnClickListenner{

            void onClickTextView(View view);

    }
}

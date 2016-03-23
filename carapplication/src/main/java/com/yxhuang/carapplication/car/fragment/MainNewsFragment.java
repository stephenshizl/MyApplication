package com.yxhuang.carapplication.car.fragment;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnScroll;
import com.lidroid.xutils.view.annotation.event.OnScrollStateChanged;
import com.yxhuang.carapplication.R;
import com.yxhuang.carapplication.car.adapter.NewsHeadAdapter;
import com.yxhuang.carapplication.car.adapter.NewsItemAdapter;
import com.yxhuang.carapplication.car.bean.NewsEntity;
import com.yxhuang.carapplication.car.bean.NewsItem;
import com.yxhuang.carapplication.car.utils.Constants;
import com.yxhuang.carapplication.car.utils.DisplayUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2015/10/18.
 */
public class MainNewsFragment extends BaseFragment {
    private NewsItemAdapter mNewsItemAdapter;

    // 加载更多布局
    private LinearLayout loading_llyt;
    // 头部滚动视图
    private FrameLayout fl_headerView;
    // 是否为最后一行
    private boolean isLastRow = false;
    // 是否还有更多数据
    private boolean isMore = true;
    // 是否正在加载数据
    private boolean isLoading = false;

    // 是否正在加载
    private boolean isUpdata = false;

    /**
     * 头部部分
     */
    private TextView tv_header_title;
    private ImageView iv_header_indicator1;
    private ImageView iv_header_indicator2;
    private ImageView iv_header_indicator3;
    private ImageView iv_header_indicator4;
    private ImageView iv_header_indicator5;

    private FrameLayout new_head_view;
    private ViewPager vp_news_header;
    private NewsHeadAdapter mNewsHeadAdapter;
    private List<NewsItem> mItemDatas = new ArrayList<>();

    @ViewInject(R.id.main_news_lv)
    ListView mListView;

    /**下拉刷新*/
    @ViewInject(R.id.ptr)
    PtrFrameLayout ptr;

    private List<NewsEntity> mDates = new ArrayList<>();

    // 标题

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_news;
    }

    @Override
    protected void initParams() {

        loading_llyt = (LinearLayout) getLayoutInflater(null).inflate(R.layout.listview_loading_view, null);

        /** 头部部分*/
        new_head_view = (FrameLayout) getLayoutInflater(null).inflate(R.layout.header_view_of_main_news_fragment, null);
        vp_news_header = (ViewPager) new_head_view.findViewById(R.id.vp_header);
        tv_header_title = (TextView) new_head_view.findViewById(R.id.tv_header_title);
        iv_header_indicator1 = (ImageView) new_head_view.findViewById(R.id.iv_header_indicator1);
        iv_header_indicator2 = (ImageView) new_head_view.findViewById(R.id.iv_header_indicator2);
        iv_header_indicator3 = (ImageView) new_head_view.findViewById(R.id.iv_header_indicator3);
        iv_header_indicator4 = (ImageView) new_head_view.findViewById(R.id.iv_header_indicator4);
        iv_header_indicator5 = (ImageView) new_head_view.findViewById(R.id.iv_header_indicator5);


        mNewsHeadAdapter = new NewsHeadAdapter(mItemDatas, context);
        mNewsItemAdapter = new NewsItemAdapter(context, mDates);

        vp_news_header.setAdapter(mNewsHeadAdapter);
        vp_news_header.addOnPageChangeListener(new NewsPageChangeListener());

//        mListView.addFooterView(loading_llyt);
        mListView.addHeaderView(new_head_view);
        mListView.setAdapter(mNewsItemAdapter);

        initPtr();
        getHeadViewDates();

    }

    /**
     * 获取列表数据
     */
    private void getHttpDatas() {

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET,  Constants.URL + "/Servlet/servlet/CorInformation",
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("yxh", "http success " + responseInfo.result);
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            int result = jsonObject.getInt("result");
                            String cars = jsonObject.getString("car");

                            if (result == 1) mDates = JSON.parseArray(cars, NewsEntity.class);
                            mNewsItemAdapter.refreshDates(mDates);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("yxh", "http failure " + error.toString() + "  msg " + msg.toString());
                    }
                });

    }

    /**
     * 获取列表头部数据
     */
    private void getHeadViewDates() {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, Constants.URL + "/Servlet/servlet/CarInfoHeadServlet",
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.i("yxh", "getHeadViewDates http success " + responseInfo.result);
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            int result = jsonObject.getInt("result");
                            String news = jsonObject.getString("mag");

                            if (result == 1) mItemDatas = JSON.parseArray(news, NewsItem.class);
                            mNewsHeadAdapter.refleshDates(mItemDatas);

                            vp_news_header.setCurrentItem(0);
                            tv_header_title.setText(mItemDatas.get(0).getNews_title());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("yxh", "http failure " + error.toString() + "  msg " + msg.toString());
                    }
                });

    }

    /**
     *  初始化下拉刷新
     */
    private void initPtr(){
        // header
        StoreHouseHeader header = new StoreHouseHeader(context);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DisplayUtil.dp2px(context, 15), 0, DisplayUtil.dp2px(context, 10));
        header.initWithString("CAR CAR");
        header.setTextColor(getResources().getColor(android.R.color.black));

        ptr.setHeaderView(header);
        ptr.addPtrUIHandler(header);
        ptr.disableWhenHorizontalMove(true);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                 isUpdata = true;
                getHttpDatas();


            }
        });

    }

    /**
     * @param view
     * @param firstVisibleItem 当前视图的第一个item
     * @param visibleItemCount 当前可见视图的最后一个item
     * @param totalItemCount   当前可见视图的总数
     */
    @OnScroll(R.id.main_news_lv)
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0)
            isLastRow = true;
    }

    @OnScrollStateChanged(R.id.main_news_lv)
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

            if (!isLoading && isMore) {

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        isLoading = false;
                        isMore = false;
                        mNewsItemAdapter.notifyDataSetChanged();
                    }
                }.execute();

            }
//            else {
//                // 移除加载
//                mListView.removeFooterView(loading_llyt);
//                Toast.makeText(getActivity(), "已经没有更多数据", Toast.LENGTH_SHORT).show();
//            }

            isLastRow = false;
        }

    }

    /**
     *  当ViewPager 中页面的状态改变时调用
     */
    private class NewsPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            tv_header_title.setText(mItemDatas.get(position % mItemDatas.size()).getNews_title());
            setIndicator(position % mItemDatas.size());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**设置viewPager滑动时indicator的变化*/
    private void setIndicator(int position){
        switch (position){
            case 0:
                iv_header_indicator1.setBackgroundColor(context.getResources().getColor(R.color.image_select_choose));
                iv_header_indicator2.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator3.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator4.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator5.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                break;
            case 1:
                iv_header_indicator1.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator2.setBackgroundColor(context.getResources().getColor(R.color.image_select_choose));
                iv_header_indicator3.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator4.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator5.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                break;
            case 2:
                iv_header_indicator1.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator2.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator3.setBackgroundColor(context.getResources().getColor(R.color.image_select_choose));
                iv_header_indicator4.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator5.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                break;
            case 3:
                iv_header_indicator1.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator2.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator3.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator4.setBackgroundColor(context.getResources().getColor(R.color.image_select_choose));
                iv_header_indicator5.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                break;
            case 4:
                iv_header_indicator1.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator2.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator3.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator4.setBackgroundColor(context.getResources().getColor(R.color.image_select_unchoose));
                iv_header_indicator5.setBackgroundColor(context.getResources().getColor(R.color.image_select_choose));
                break;
            default:
                break;
        }
    }
}

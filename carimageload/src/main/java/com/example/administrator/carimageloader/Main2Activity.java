package com.example.administrator.carimageloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.administrator.carimageloader.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private ListViewAdapter mListViewAdapter;
    private ListView mListView;
    private List<String> mDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mListView = (ListView) findViewById(R.id.listview);
        mListViewAdapter = new ListViewAdapter(this, getDates(), mListView);
        mListView.setAdapter(mListViewAdapter);

    }

    private List<String> getDates(){
        mDates = new ArrayList<>();
        String[] imageUrls = {
                "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
                "http://pic47.nipic.com/20140830/7487939_180041822000_2.jpg",
                "http://pic41.nipic.com/20140518/4135003_102912523000_2.jpg",
                "http://img2.imgtn.bdimg.com/it/u=1133260524,1171054226&fm=21&gp=0.jpg",
                "http://h.hiphotos.baidu.com/image/pic/item/3b87e950352ac65c0f1f6e9efff2b21192138ac0.jpg",
                "http://pic42.nipic.com/20140618/9448607_210533564001_2.jpg",
                "http://pic10.nipic.com/20101027/3578782_201643041706_2.jpg",
                "http://picview01.baomihua.com/photos/20120805/m_14_634797817549375000_37810757.jpg",
                "http://img2.3lian.com/2014/c7/51/d/26.jpg",
                "http://img3.3lian.com/2013/c1/34/d/93.jpg",
                "http://b.zol-img.com.cn/desk/bizhi/image/3/960x600/1375841395686.jpg",
                "http://picview01.baomihua.com/photos/20120917/m_14_634834710114218750_41852580.jpg",
                "http://cdn.duitang.com/uploads/item/201311/03/20131103171224_rr2aL.jpeg",
                "http://imgrt.pconline.com.cn/images/upload/upc/tx/wallpaper/1210/17/c1/spcgroup/14468225_1350443478079_1680x1050.jpg",
                "http://pic41.nipic.com/20140518/4135003_102025858000_2.jpg",
                "http://www.1tong.com/uploads/wallpaper/landscapes/200-4-730x456.jpg",
                "http://pic.58pic.com/58pic/13/00/22/32M58PICV6U.jpg",
                "http://picview01.baomihua.com/photos/20120629/m_14_634765948339062500_11778706.jpg",
                "http://h.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=429e7b1b92ef76c6d087f32fa826d1cc/7acb0a46f21fbe09cc206a2e69600c338744ad8a.jpg",
                "http://pica.nipic.com/2007-12-21/2007122115114908_2.jpg",
                "http://cdn.duitang.com/uploads/item/201405/13/20140513212305_XcKLG.jpeg",
                "http://photo.loveyd.com/uploads/allimg/080618/1110324.jpg",
                "http://img4.duitang.com/uploads/item/201404/17/20140417105820_GuEHe.thumb.700_0.jpeg",
                "http://cdn.duitang.com/uploads/item/201204/21/20120421155228_i52eX.thumb.600_0.jpeg",
                "http://img4.duitang.com/uploads/item/201404/17/20140417105856_LTayu.thumb.700_0.jpeg",
                "http://img04.tooopen.com/images/20130723/tooopen_20530699.jpg",
                "http://www.qjis.com/uploads/allimg/120612/1131352Y2-16.jpg",
                "http://pic.dbw.cn/0/01/33/59/1335968_847719.jpg",
                "http://a.hiphotos.baidu.com/image/pic/item/a8773912b31bb051a862339c337adab44bede0c4.jpg",
                "http://h.hiphotos.baidu.com/image/pic/item/f11f3a292df5e0feeea8a30f5e6034a85edf720f.jpg",
                "http://img0.pconline.com.cn/pconline/bizi/desktop/1412/ER2.jpg",
                "http://pic.58pic.com/58pic/11/25/04/91v58PIC6Xy.jpg",
                "http://img3.3lian.com/2013/c2/32/d/101.jpg",
                "http://pic25.nipic.com/20121210/7447430_172514301000_2.jpg",
                "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg",
                "http://www.renyugang.cn/emlog/content/plugins/kl_album/upload/201004/852706aad6df6cd839f1211c358f2812201004120651068641.jpg",
                "http://pic1.nipic.com/2009-02-20/2009220135032130_2.jpg",
                "http://imgsrc.baidu.com/forum/pic/item/e42b1dd8bc3eb135d193e747a61ea8d3fc1f4493.jpg",
                "http://imgsrc.baidu.com/forum/pic/item/09be3f094b36acaf0ad6eb717cd98d1000e99cde.jpg",
                "http://imgsrc.baidu.com/forum/pic/item/b497eeefce1b9d16b36f99ecf3deb48f8d546460.jpg",
                "http://img6.ph.126.net/MlnW-JTY4Ml6J1HORtgbjw==/606578574828541266.jpg",
                "http://pic.6188.com/upload_6188s/flashAll/s800/20130606/13704823489c8Siu.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760758_3497.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760755_6715.jpeg",
                "http://img.my.csdn.net/uploads/201508/05/1438760726_5120.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760726_8364.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760725_4031.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760724_9463.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760724_2371.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760707_4653.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760706_6864.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760706_9279.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760704_2341.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760704_5707.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760685_5091.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760685_4444.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760684_8827.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760683_3691.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760683_7315.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760663_7318.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760662_3454.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760662_5113.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760661_3305.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760661_7416.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760589_2946.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760589_1100.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760588_8297.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760587_2575.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760587_8906.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760550_2875.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760550_9517.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760549_7093.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760549_1352.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760548_2780.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760531_1776.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760531_1380.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760530_4944.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760530_5750.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760529_3289.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760500_7871.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760500_6063.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760499_6304.jpeg",
                "http://img.my.csdn.net/uploads/201508/05/1438760499_5081.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760498_7007.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760478_3128.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760478_6766.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760477_1358.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760477_3540.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760476_1240.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760446_7993.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760446_3641.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760445_3283.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760444_8623.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760444_6822.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760422_2224.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760421_2824.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760420_2660.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760420_7188.jpg",
                "http://img.my.csdn.net/uploads/201508/05/1438760419_4123.jpg"
        };
        for (String url : imageUrls) {
            mDates.add(url);
        }

        return mDates;
    }

}

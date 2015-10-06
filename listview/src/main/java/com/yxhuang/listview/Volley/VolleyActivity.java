package com.yxhuang.listview.Volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yxhuang.listview.R;

import org.json.JSONObject;

public class VolleyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        final TextView textView = (TextView) findViewById(R.id.tv_volley_result);


        findViewById(R.id.btn_start_volley).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.weather.com.cn/adat/sk/101010100.html";
                VolleyUtils.JsonRequestGet(VolleyActivity.this, url, new VolleyUtils.OnHttpListener<JSONObject>() {
                    @Override
                    public void onHttpListener(boolean httpSuccessed, JSONObject obj) {
                        if (httpSuccessed){
                            Log.i("yxh", "VolleyActivity JsonRequest : " + obj.toString());
                            textView.setText(obj.toString());
                        }
                    }
                });
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("city", "北京");
//
//                VolleyUtils.JsonRequestPost(VolleyActivity.this, url, params, new VolleyUtils.OnHttpListener<JSONObject>() {
//                    @Override
//                    public void onHttpListener(boolean httpSuccessed, JSONObject obj) {
//                        if (httpSuccessed){
//                            Log.i("yxh", "VolleyActivity JsonRequest : " + obj.toString());
//                            textView.setText(obj.toString());
//                        }
//                    }
//                });
            }
        });
    }
}

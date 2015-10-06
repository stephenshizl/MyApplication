package com.yxhuang.listview.Volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/28.
 */
public class VolleyUtils {
    private static final String HTTP_ERROR = "系统繁忙，请稍后再试";


    /**
     * 返回错误json
     * @param string
     * @return
     */
    private static JSONObject getErrorJson(String string){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("error", string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    };


    /**
     * Get请求json格式数据
     * @param context   应用的上下文
     * @param url        要请求的地址
     * @param onHttpListener          请求结果的回调
     */
    public static void JsonRequestGet(Context context, String url,  final OnHttpListener<JSONObject> onHttpListener){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
              if (onHttpListener != null) onHttpListener.onHttpListener(true, jsonObject);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (onHttpListener != null) onHttpListener.onHttpListener(false, getErrorJson(HTTP_ERROR));
            }
        });

        queue.add(request);
    }


    /**
     * Post 请求json格式数据
     * @param context   上下文
     * @param url       要请求的地址
     * @param params          请求的参数
     * @param onHttpListener     请求结果回调
     */
    public static void JsonRequestPost(Context context, String url, final Map<String, String> params, final OnHttpListener<JSONObject> onHttpListener){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if (onHttpListener != null) onHttpListener.onHttpListener(true, jsonObject);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (onHttpListener != null) onHttpListener.onHttpListener(false, getErrorJson(HTTP_ERROR));

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (!params.isEmpty()) {
                    return params;
                } else {
                    return null;
                }
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return params;
            }
        };
        queue.add(request);
    }


    /**********************************************************
     *
     * Interface
     *
     *********************************************************/
    public interface OnHttpListener<T>{
        public void onHttpListener(boolean httpSuccessed, T obj);
    }

}

package pda.nryuncang.com.myapplication.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * HTTP请求封装
 * Created by Wilk on 2015/6/23.
 */
public class RequestHelper {
    /**
     * volley请求队列
     */
    public static RequestQueue mRequestQueue;

    /**
     * 初始化请求helper
     *
     * @param context 引用对象
     */
    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }


    public static void getPiecesManager(String userId,String sortPortCode, String packageCode, Response.Listener<JSONObject> listener,
                             Response.ErrorListener errorListener) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("sortPortCode", sortPortCode);
//        params.put("packageCode",packageCode);
//        doRequest(API.getPiecesManagerUrl(), params, listener, errorListener);
        doJsonRequest(userId,sortPortCode,packageCode,listener,errorListener);
    }



    public static void getPiecesLoginManager(String uesrId, String password, Response.Listener<JSONObject> listener,
                                        Response.ErrorListener errorListener) {
        doJsonLoginRequest(API.getLoginUrl(),uesrId,password,listener,errorListener);
    }
    /**
     * 根据请求的tag取消请求
     *
     * @param tag url
     */
    public static void doCancelRequest(final String tag) {
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return request.getTag().toString().equals(tag);
            }
        });
    }
    /**
     * 创建请求并添加到请求队列开始请求
     *
     * @param url           请求url
     * @param params        参数列表
     * @param listener      返回监听
     * @param errorListener 错误监听
     */
    public static void doRequest(String url, HashMap<String, String> params, Response.Listener<JSONObject> listener,
                                 Response.ErrorListener errorListener) {
        doCancelRequest(url);
        Request<JSONObject> request = new NormalPostRequest(
                url, listener, errorListener, params);
        Log.w("kim",request.toString());
        request.setTag(url);
        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, 1.0f));
        mRequestQueue.add(request);
    }



    public static void doJsonRequest(String userId, String sortPortCode, String packageCode, Response.Listener<JSONObject> listener,
                                 Response.ErrorListener errorListener) {
        String url;
        if(!TextUtils.isEmpty(userId)){
            url=API.getPiecesManagerUserUrl();
        }else{
            url=API.getPiecesManagerUrl();
        }
        doCancelRequest(url);
        JsonObjectRequest jsonObjectRequest ;
        JSONObject jsonObject=new JSONObject() ;
        try {
            if(!TextUtils.isEmpty(userId)){
                jsonObject.put("user_id",userId);
            }
            jsonObject.put("sortPortCode",sortPortCode);
            jsonObject.put("packageCode",packageCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObjectRequest=new JsonObjectRequest( Request.Method.POST, url, jsonObject,
                listener,errorListener);
        jsonObjectRequest.setTag(url);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, 1.0f));
        mRequestQueue.add(jsonObjectRequest);
        mRequestQueue.start();
    }



    public static void doJsonLoginRequest(String url, String userId, String password, Response.Listener<JSONObject> listener,
                                     Response.ErrorListener errorListener) {
        doCancelRequest(url);
        JsonObjectRequest jsonObjectRequest ;
        JSONObject jsonObject=new JSONObject() ;
        try {
            jsonObject.put("user_id",userId);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObjectRequest=new JsonObjectRequest( Request.Method.POST, url, jsonObject,
                listener,errorListener);
        jsonObjectRequest.setTag(url);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, 1.0f));
        mRequestQueue.add(jsonObjectRequest);
        mRequestQueue.start();
    }


    /**
     * 将json格式化为指定对象
     *
     * @param jsonString json字符串
     * @param cls        要转为的对象
     * @param <T>        对象类型
     * @return 转换后的指定对象
     */
    public static <T> T parseToGson(String jsonString, Class<T> cls) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, cls);
    }
}




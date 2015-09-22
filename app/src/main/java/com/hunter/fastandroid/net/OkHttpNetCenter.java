package com.hunter.fastandroid.net;

import android.content.Context;

import com.hunter.fastandroid.app.AppManager;
import com.hunter.fastandroid.base.BaseRequest;
import com.hunter.fastandroid.utils.NetUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 基于OkHttp封装的网络访问类
 * 网络访问控制中心 用于统一管理网络访问及初始化网络相关配置
 *
 * @author Ht
 */
public class OkHttpNetCenter extends BaseNetCenter {
    private static OkHttpNetCenter instance;
    private OkHttpClient mOkHttpClient;

    private OkHttpNetCenter() {
        super();
    }

    public static OkHttpNetCenter getInstance() {
        if (instance == null) {
            instance = new OkHttpNetCenter();
        }

        return instance;
    }

    void initHttpClient() {
        mOkHttpClient = new OkHttpClient();
        // 设置连接超时时间
        mOkHttpClient.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    @Override
    void removeAllHeaders() {
        baseHeader.clear();
    }

    /**
     * 获取公共请求
     *
     * @param context
     * @param url
     */
    private Request getBaseRequest(String url, Context context, int type, Map<String, String> params) {
        Request.Builder builder = new Request.Builder();
        // 获取公共请求头
        Headers baseHeader = Headers.of(this.baseHeader);
        // 设置url、tag、公共请求头
        builder = builder.url(url).tag(context).headers(baseHeader);

        switch (type) {
            case GET:
                // 将参数拼装到url (get)
                StringBuilder getParams = new StringBuilder("?");
                Set<String> getKeys = params.keySet();
                Iterator<String> getIterator = getKeys.iterator();
                while (getIterator.hasNext()) {
                    String key = getIterator.next();
                    String value = params.get(key);
                    getParams.append(key + "=" + value + "&");
                }

                builder = builder.url(url + getParams).get();
                break;
            case POST:
                // 将参数转换成请求体 (post)
                FormEncodingBuilder postParams = new FormEncodingBuilder();
                Set<String> postKeys = params.keySet();
                Iterator<String> postIterator = postKeys.iterator();
                while (postIterator.hasNext()) {
                    String key = postIterator.next();
                    String value = params.get(key);
                    postParams.add(key, value);
                }

                builder = builder.post(postParams.build());
                break;
            case PUT:
                // 将参数转换成请求体 (put)
                FormEncodingBuilder putParams = new FormEncodingBuilder();
                Set<String> putKeys = params.keySet();
                Iterator<String> putIterator = putKeys.iterator();
                while (putIterator.hasNext()) {
                    String key = putIterator.next();
                    String value = params.get(key);
                    putParams.add(key, value);
                }

                builder = builder.put(putParams.build());
                break;
        }

        return builder.build();
    }

    /**
     * 发起不带参数get请求
     *
     * @param url      请求路径
     * @param callback 响应回调
     */
    public void get(String url, Callback callback) {
        // 获取一个空参数
        Map<String, String> emptyParams = Collections.emptyMap();

        get(url, emptyParams, callback);
    }

    /**
     * 发起带参数(请求实体类)的get请求
     *
     * @param url      请求路径
     * @param t        继承自BaseRequest的请求参数实体类
     * @param callback 响应回调
     */
    public <T extends BaseRequest> void get(String url, T t, Callback callback) {
        // 将实体类转换成Map
        Map<String, String> params = t.getMapParams();

        get(url, params, callback);
    }

    /**
     * 发起带参数(Map形式存储)的get请求
     *
     * @param url      请求路径
     * @param params   以map形式存储的参数
     * @param callback 响应回调
     */
    public void get(String url, Map<String, String> params, Callback callback) {

        sendRequest(GET, url, params, callback);
    }

    /**
     * 发起不带参数post请求
     *
     * @param url      请求路径
     * @param callback 响应回调
     */
    public void post(String url, Callback callback) {
        // 获取一个空参数
        Map<String, String> emptyParams = Collections.emptyMap();

        post(url, emptyParams, callback);
    }

    /**
     * 发起带参数(请求实体类)post请求
     *
     * @param url      请求路径
     * @param t        继承自BaseRequest的请求参数实体类
     * @param callback 响应回调
     */
    public <T extends BaseRequest> void post(String url, T t,
                                             Callback callback) {
        // 将实体类转换成Map
        Map<String, String> params = t.getMapParams();

        post(url, params, callback);
    }

    /**
     * 发起带参数post请求
     *
     * @param url      请求路径
     * @param params   以map形式存储的参数
     * @param callback 响应回调
     */
    public void post(String url, Map<String, String> params, Callback callback) {

        sendRequest(POST, url, params, callback);
    }

    /**
     * 发起可设置请求参数的网络请求
     *
     * @param type            请求类型
     * @param url             请求路径
     * @param params          请求参数
     * @param callback 响应回调
     */
    private void sendRequest(int type, String url, Map<String, String> params, Callback callback) {
        // 获取当前页面的Context
        Context context = AppManager.getAppManager().currentActivity();

        Request request = getBaseRequest(url, context, type, params);

        // 判断网络是否可用
        if (!NetUtils.isNetworkConnected(context)) {
            return;
        }

        mOkHttpClient.newCall(request).enqueue(callback);
    }

    @Override
    public void clearRequestQueue(Context context) {
        mOkHttpClient.cancel(context);
    }

}

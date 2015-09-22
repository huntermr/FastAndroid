package com.hunter.fastandroid.net;

import android.content.Context;

import com.hunter.fastandroid.app.AppManager;
import com.hunter.fastandroid.base.BaseRequest;
import com.hunter.fastandroid.utils.NetUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 基于AsyncHttp封装的网络访问类
 * 网络访问控制中心 用于统一管理网络访问及初始化网络相关配置
 *
 * @author Ht
 */
public class AsyncHttpNetCenter extends BaseNetCenter {

    private static AsyncHttpNetCenter instance;
    private AsyncHttpClient mAsyncHttpClient;

    private AsyncHttpNetCenter() {
        super();
    }

    public static AsyncHttpNetCenter getInstance() {
        if (instance == null) {
            instance = new AsyncHttpNetCenter();
        }

        return instance;
    }

    @Override
    void initHttpClient() {
        mAsyncHttpClient = new AsyncHttpClient();
        // 设置连接超时时间
        mAsyncHttpClient.setConnectTimeout(CONNECT_TIMEOUT);
        // 设置最大连接数
        mAsyncHttpClient.setMaxConnections(MAX_CONNECTIONS);
        // 设置重连次数以及间隔时间
        mAsyncHttpClient.setMaxRetriesAndTimeout(MAX_RETRIES, RETRIES_TIMEOUT);
        // 设置响应超时时间
        mAsyncHttpClient.setResponseTimeout(RESPONSE_TIMEOUT);

        insertAllHeaders();
    }

    /**
     * 放入所有公共请求头
     */
    private void insertAllHeaders(){
        Set<String> headerKey = baseHeader.keySet();
        Iterator<String> iterator = headerKey.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            String value = baseHeader.get(key);

            mAsyncHttpClient.addHeader(key, value);
        }
    }

    @Override
    void removeAllHeaders() {
        baseHeader.clear();
        mAsyncHttpClient.removeAllHeaders();
    }

    @Override
    public void setHeader(String header, String value) {
        super.setHeader(header, value);
        removeAllHeaders();
        insertAllHeaders();
    }

    /**
     * 发起不带参数get请求
     *
     * @param url             请求路径
     * @param responseHandler 响应回调
     */
    public void get(String url, AsyncHttpResponseHandler responseHandler) {
        // 获取一个空参数
        Map<String, String> emptyParams = Collections.emptyMap();

        get(url, emptyParams, responseHandler);
    }

    /**
     * 发起带参数(请求实体类)的get请求
     *
     * @param url             请求路径
     * @param t               继承自BaseRequest的请求参数实体类
     * @param responseHandler 响应回调
     */
    public <T extends BaseRequest> void get(String url, T t,
                                            AsyncHttpResponseHandler responseHandler) {
        // 将实体类转换成Map
        Map<String, String> params = t.getMapParams();

        get(url, params, responseHandler);
    }

    /**
     * 发起带参数(Map形式存储)的get请求
     *
     * @param url             请求路径
     * @param params          以map形式存储的参数
     * @param responseHandler 响应回调
     */
    public void get(String url, Map<String, String> params,
                    AsyncHttpResponseHandler responseHandler) {

        sendRequest(GET, url, params, responseHandler);
    }

    /**
     * 发起带请求体的get请求
     *
     * @param url             请求路径
     * @param json            以json字符串形式存储的参数
     * @param responseHandler 响应回调
     */
    public void get(String url, String json,
                    AsyncHttpResponseHandler responseHandler) {

        StringEntity stringEntity = null;

        try {
            stringEntity = new StringEntity(json, CONTENT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        sendRequest(GET, url, stringEntity, DEFAULT_CONTENT_TYPE, responseHandler);
    }

    /**
     * 发起不带参数post请求
     *
     * @param url             请求路径
     * @param responseHandler 响应回调
     */
    public void post(String url, AsyncHttpResponseHandler responseHandler) {
        // 获取一个空参数
        Map<String, String> emptyParams = Collections.emptyMap();

        post(url, emptyParams, responseHandler);
    }

    /**
     * 发起带参数(请求实体类)post请求
     *
     * @param url             请求路径
     * @param t               继承自BaseRequest的请求参数实体类
     * @param responseHandler 响应回调
     */
    public <T extends BaseRequest> void post(String url, T t,
                                             AsyncHttpResponseHandler responseHandler) {
        // 将实体类转换成Map
        Map<String, String> params = t.getMapParams();

        post(url, params, responseHandler);
    }

    /**
     * 发起带参数post请求
     *
     * @param url             请求路径
     * @param params          以map形式存储的参数
     * @param responseHandler 响应回调
     */
    public void post(String url, Map<String, String> params, AsyncHttpResponseHandler responseHandler) {

        sendRequest(POST, url, params, responseHandler);
    }

    /**
     * 发起带请求体的post请求
     *
     * @param url             请求路径
     * @param json            以json字符串形式存储的参数
     * @param responseHandler 响应回调
     */
    public void post(String url, String json, AsyncHttpResponseHandler responseHandler) {
        StringEntity stringEntity = null;

        try {
            stringEntity = new StringEntity(json, CONTENT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        sendRequest(POST, url, stringEntity, DEFAULT_CONTENT_TYPE, responseHandler);
    }

    /**
     * 发起可设置请求参数的网络请求
     *
     * @param type            请求类型
     * @param url             请求路径
     * @param params          请求参数
     * @param responseHandler 响应回调
     */
    void sendRequest(int type,
                     String url, Map<String, String> params, AsyncHttpResponseHandler responseHandler) {
        // 将Map转换成请求参数
        RequestParams requestParams = new RequestParams(params);
        requestParams.setContentEncoding(CONTENT_ENCODING);

        // 获取当前页面的Context
        Context context = AppManager.getAppManager().currentActivity();

        // 判断网络是否可用
        if (!NetUtils.isNetworkConnected(context)) {
            responseHandler.onFailure(ResponseCode.ERROR_NETWORK_NOT_AVAILABLE, null, null, null);
            return;
        }

        // 根据传入类型调用不同请求方法,可自行扩展
        // 传入Context以便与生命周期联动
        switch (type) {
            case GET:
                // 发起get请求,获取请求处理器
                mAsyncHttpClient.get(context, url, requestParams, responseHandler);
                break;
            case POST:
                // 发起post请求,获取请求处理器
                mAsyncHttpClient.post(context, url, requestParams, responseHandler);
                break;
            case PUT:
                // 发起put请求,获取请求处理器
                // .....
                mAsyncHttpClient.put(context, url, requestParams, responseHandler);
            default:
                // 默认发起get请求
                mAsyncHttpClient.get(context, url, requestParams, responseHandler);
                break;
        }
    }

    /**
     * 发起可设置请求体的网络请求
     *
     * @param type            请求类型
     * @param url             请求路径
     * @param entity          请求体
     * @param responseHandler 响应回调
     */
    void sendRequest(int type,
                     String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        // 获取当前页面的Context
        Context context = AppManager.getAppManager().currentActivity();

        // 判断网络是否可用
        if (!NetUtils.isNetworkConnected(context)) {
            responseHandler.onFailure(ResponseCode.ERROR_NETWORK_NOT_AVAILABLE, null, null, null);
            return;
        }

        // 根据传入类型调用不同请求方法,可自行扩展
        // 传入Context以便与生命周期联动
        switch (type) {
            case GET:
                // 发起get请求,获取请求处理器
                mAsyncHttpClient.get(context, url, entity, contentType, responseHandler);
                break;
            case POST:
                // 发起post请求,获取请求处理器
                mAsyncHttpClient.post(context, url, entity, contentType, responseHandler);
                break;
            case PUT:
                // 发起put请求,获取请求处理器
                // .....
                mAsyncHttpClient.put(context, url, entity, contentType, responseHandler);
            default:
                // 默认发起get请求
                mAsyncHttpClient.get(context, url, entity, contentType, responseHandler);
                break;
        }
    }

    @Override
    public void clearRequestQueue(Context context) {
        // 销毁指定Context的请求, 第二个参数true代表强制结束
        mAsyncHttpClient.cancelRequests(context, true);
    }
}

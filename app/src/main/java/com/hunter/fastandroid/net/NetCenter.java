package com.hunter.fastandroid.net;

import android.content.Context;

import com.hunter.fastandroid.app.AppManager;
import com.hunter.fastandroid.base.BaseRequest;
import com.hunter.fastandroid.base.BaseRequestHeader;
import com.hunter.fastandroid.utils.NetUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;

/**
 * 网络访问控制中心 用于统一管理网络访问及初始化网络相关配置
 *
 * @author Ht
 */
public class NetCenter {

    private static final int GET = 1;
    private static final int POST = 2;
    private static final int PUT = 3;

    // 连接超时时间
    private static final int CONNECT_TIMEOUT = 15 * 1000;
    // 最大连接数
    private static final int MAX_CONNECTIONS = 15;
    // 失败重连次数
    private static final int MAX_RETRIES = 3;
    // 失败重连间隔时间
    private static final int RETRIES_TIMEOUT = 5 * 1000;
    // 响应超时时间
    private static final int RESPONSE_TIMEOUT = 15 * 1000;
    // 默认编码
    public static final String CONTENT_ENCODING = HTTP.UTF_8;
    // 默认Content-Type
    public static final String DEFAULT_CONTENT_TYPE = "application/json";

    private static AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();

    static {
        // 设置连接超时时间
        mAsyncHttpClient.setConnectTimeout(CONNECT_TIMEOUT);
        // 设置最大连接数
        mAsyncHttpClient.setMaxConnections(MAX_CONNECTIONS);
        // 设置重连次数以及间隔时间
        mAsyncHttpClient.setMaxRetriesAndTimeout(MAX_RETRIES, RETRIES_TIMEOUT);
        // 设置响应超时时间
        mAsyncHttpClient.setResponseTimeout(RESPONSE_TIMEOUT);

        initBaseHeader();
    }

    /**
     * 初始化公共请求头
     */
    public static void initBaseHeader() {
//        mAsyncHttpClient.addHeader("Source", BaseRequestHeader.getSource());
    }

    /**
     * 设置一个请求头
     *
     * @param key
     * @param value
     */
    public static void setHeader(String key, String value) {
        mAsyncHttpClient.addHeader(key, value);
    }

    /**
     * 清除所有请求头
     */
    public static void removeAllHeaders() {
        mAsyncHttpClient.removeAllHeaders();
    }

    /**
     * 发起不带参数get请求
     *
     * @param url             请求路径
     * @param responseHandler 响应回调
     */
    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
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
    public static <T extends BaseRequest> void get(String url, T t,
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
    public static void get(String url, Map<String, String> params,
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
    public static void get(String url, String json,
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
    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
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
    public static <T extends BaseRequest> void post(String url, T t,
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
    public static void post(String url, Map<String, String> params, AsyncHttpResponseHandler responseHandler) {

        sendRequest(POST, url, params, responseHandler);
    }

    /**
     * 发起带请求体的post请求
     *
     * @param url             请求路径
     * @param json            以json字符串形式存储的参数
     * @param responseHandler 响应回调
     */
    public static void post(String url, String json, AsyncHttpResponseHandler responseHandler) {
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
    private static void sendRequest(int type,
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
    private static void sendRequest(int type,
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

    /**
     * 取消当前Context的请求队列
     */
    public static void clearRequestQueue() {
        Context context = AppManager.getAppManager().currentActivity();
        // 销毁指定Context的请求, 第二个参数true代表强制结束
        mAsyncHttpClient.cancelRequests(context, true);
    }
}

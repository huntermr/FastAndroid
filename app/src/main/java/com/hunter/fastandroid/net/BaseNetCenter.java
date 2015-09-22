package com.hunter.fastandroid.net;

import android.content.Context;

import org.apache.http.protocol.HTTP;

import java.util.HashMap;

/**
 * 基于第三方网络框架封装的网络访问类
 * 网络访问控制中心 用于统一管理网络访问及初始化网络相关配置
 *
 * @author Ht
 */
public abstract class BaseNetCenter {
    static final int GET = 1;
    static final int POST = 2;
    static final int PUT = 3;

    HashMap<String, String> baseHeader;

    // 连接超时时间
    static final int CONNECT_TIMEOUT = 15 * 1000;
    // 最大连接数
    static final int MAX_CONNECTIONS = 15;
    // 失败重连次数
    static final int MAX_RETRIES = 3;
    // 失败重连间隔时间
    static final int RETRIES_TIMEOUT = 5 * 1000;
    // 响应超时时间
    static final int RESPONSE_TIMEOUT = 15 * 1000;
    // 默认编码
    public static final String CONTENT_ENCODING = HTTP.UTF_8;
    // 默认Content-Type
    public static final String DEFAULT_CONTENT_TYPE = "application/json";

    BaseNetCenter() {
        initBaseHeaders();
        initHttpClient();
    }

    /**
     * 初始化网络请求工具类
     */
    abstract void initHttpClient();

    /**
     * 移除所有请求头
     */
    abstract void removeAllHeaders();

    /**
     * 初始化公共请求头
     */
    void initBaseHeaders() {
        baseHeader = new HashMap<>();
        baseHeader.put("base", "value");
    }

    /**
     * 设置一个请求头(如果存在则覆盖)
     *
     * @param header
     * @param value
     */
    void setHeader(String header, String value) {
        baseHeader.put(header, value);
    }

    /**
     * 取消指定Context的请求队列
     */
    abstract void clearRequestQueue(Context context);
}

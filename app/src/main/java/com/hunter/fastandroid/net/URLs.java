package com.hunter.fastandroid.net;

/**
 * URL路径处理类
 *
 * @author Ht
 */
public class URLs {
    public static final String HOST = "http://xxx.xxx.xxx";
    public static final String PROJECT_NAME = "";
    public static final String API = "";

    // 用户模块
    /**
     * 注册
     */
    public static final String USER_SIGNUP = "xxx";
    /**
     * 登陆
     */
    public static final String USER_SIGNIN = "xxx";
    /**
     * 第三方登陆
     */
    public static final String OAUTH_SIGNIN = "xxx";

    /**
     * 拼接请求路径
     *
     * @PARAM URI
     * @RETURN
     */
    public static String getURL(String uri) {
        return HOST + PROJECT_NAME + API + uri;
    }

}

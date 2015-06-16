package com.hunter.fastandroid.base;

import android.os.Build;
import android.text.TextUtils;

import com.hunter.fastandroid.app.MyApplication;

/**
 * 公共请求头
 *
 * @author user
 */
public class BaseRequestHeader {
    // 来源	web:1, android: 2, iOS: 3
    private static String source = "2";
    // 版本号	App版本号
    private static String appVersion = MyApplication.getVersion();
    // 设备型号
    private static String deviceModel = Build.MODEL;
    // 系统版本号
    private static String sysVersion = Build.VERSION.RELEASE;
    // 系统语言
    private static String language = MyApplication.getLanguage();
    // 用户Token
    private static String token = "";
    // 经度
    private static double longitude;
    // 纬度
    private static double latitude;

    public static String getSource() {
        return source;
    }

    public static String getAppVersion() {
        return appVersion;
    }

    public static void setSource(String source) {
        BaseRequestHeader.source = source;
    }

    public static void setAppVersion(String appVersion) {
        BaseRequestHeader.appVersion = appVersion;
    }

    public static void setDeviceModel(String deviceModel) {
        BaseRequestHeader.deviceModel = deviceModel;
    }

    public static void setSysVersion(String sysVersion) {
        BaseRequestHeader.sysVersion = sysVersion;
    }

    public static void setLanguage(String language) {
        BaseRequestHeader.language = language;
    }

    public static void setToken(String token) {
        BaseRequestHeader.token = token;
    }

    public static void setLongitude(double longitude) {
        BaseRequestHeader.longitude = longitude;
    }

    public static void setLatitude(double latitude) {
        BaseRequestHeader.latitude = latitude;
    }

    public static String getDeviceModel() {
        return deviceModel;
    }

    public static String getSysVersion() {
        return sysVersion;
    }

    public static String getLanguage() {
        return language;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static String getToken() {
        if (TextUtils.isEmpty(token)) {
            if (MyApplication.getCurrentUser() != null) {
                return MyApplication.getCurrentUser().getToken();
            }
        }

        return token;
    }
}

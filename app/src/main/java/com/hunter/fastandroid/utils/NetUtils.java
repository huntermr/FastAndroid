package com.hunter.fastandroid.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.hunter.fastandroid.R;
import com.hunter.fastandroid.app.AppManager;
import com.hunter.fastandroid.base.BaseActivity;
import com.hunter.fastandroid.base.BaseRequest;

/**
 * 网络相关工具类
 *
 * @author Ht
 */
public class NetUtils {

    /**
     * 判断当前网络是否可用
     *
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager == null) {
                Toast.makeText(context, R.string.network_not_available, Toast.LENGTH_SHORT).show();
                return false;
            }
            NetworkInfo[] infos = mConnectivityManager.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo info : infos) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }

        Toast.makeText(context, R.string.network_not_available, Toast.LENGTH_SHORT).show();
        return false;
    }

    private static void showNetErrorDialog(Context context) {
        try {
            BaseActivity activity = (BaseActivity) AppManager.getAppManager()
                    .currentActivity();
            activity.hideProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("当前网络不可用,请检查网络设置。");
        builder.setNegativeButton("去设置", new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        builder.setPositiveButton("取消", new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        builder.show();

    }

    /**
     * 判断当前wifi是否可用
     *
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @return
     */
    public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息 1 wifi 2 移动网络 -1 无网络
     *
     * @return
     */
    public int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI ? 1
                        : 2;
            }
        }
        return -1;
    }

    /**
     * 获取请求参数
     *
     * @param t
     * @return
     */
    public static <T extends BaseRequest> Map<String, String> getParams(T t) {
        Class<? extends BaseRequest> clazz = t.getClass();
        Class<? extends Object> superclass = clazz.getSuperclass();

        Field[] fields = clazz.getDeclaredFields();
        Field[] superFields = superclass.getDeclaredFields();

        if (fields == null || fields.length == 0) {
            return Collections.emptyMap();
        }

        Map<String, String> params = new HashMap<String, String>();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                params.put(field.getName(), String.valueOf(field.get(t)));
            }

            for (Field superField : superFields) {
                superField.setAccessible(true);
                params.put(superField.getName(), String.valueOf(superField.get(t)));
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return params;
    }

}

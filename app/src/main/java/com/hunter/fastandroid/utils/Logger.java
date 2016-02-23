package com.hunter.fastandroid.utils;

import android.content.Context;
import android.util.Log;


/**
 * Author：Ht
 * Create time：2015/8/19 9:03
 * Email：hunter_android@163.com
 * Description：日志管理器
 */

public class Logger {

    /**
     * 是否为开发者模式(开发模式打印LOG,非开发模式不打印LOG)
     */
    private static boolean mDebug = true;

    private Logger() {
    }

    /**
     * 打印info级别的log
     *
     * @param msg
     */
    public static void i(Context context, String msg) {
        if (mDebug) {
            Log.i("*** CurrentPage：" + context.getClass().getSimpleName() + "  Log", msg);
        }
    }

    /**
     * 打印info级别的log
     *
     * @param msg
     */
    public static void i(String msg) {
        if (mDebug) {
            Log.i("Log-----info", msg);
        }
    }

    /**
     * 打印error级别的log
     *
     * @param msg
     */
    public static void e(Context context, String msg) {
        if (mDebug) {
            Log.e("*** CurrentPage：" + context.getClass().getSimpleName() + "  Log", msg);
        }
    }

    /**
     * 打印error级别的log
     *
     * @param msg
     */
    public static void e(String msg) {
        if (mDebug) {
            Log.e("Log-----error", msg);
        }
    }
}

package com.hunter.fastandroid.app;

import android.app.Application;
import android.content.Context;

/**
 * 自定义应用入口
 *
 * @author Hunter
 */
public class BaseApplication extends Application {
    private static BaseApplication mInstance;

    public static Context getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

}

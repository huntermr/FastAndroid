package com.hunter.fastandroid.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;

import com.hunter.fastandroid.DaoMaster;
import com.hunter.fastandroid.DaoSession;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.Locale;

/**
 * 自定义应用入口
 *
 * @author Ht
 */
public class BaseApplication extends Application {
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;
    private static BaseApplication mInstance;

    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        initImageLoader();
        initScreenSize();
    }

    /**
     * 初始化imageloader
     */
    private void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), "imageloader/Cache");

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // 加载图片时会在内存中加载缓存
                .cacheOnDisk(true) // 加载图片时会在磁盘中加载缓存
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .threadPoolSize(3)
                        // default
                .threadPriority(Thread.NORM_PRIORITY - 2)
                        // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                        // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
                        // default
                .diskCache(new UnlimitedDiscCache(cacheDir))
                        // default
                .diskCacheSize(20 * 1024 * 1024).diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // default
                .defaultDisplayImageOptions(defaultOptions) // default
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (mDaoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    G.APPNAME, null);
            mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return mDaoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (mDaoSession == null) {
            if (mDaoMaster == null) {
                mDaoMaster = getDaoMaster(context);
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    public static Context getInstance() {
        return mInstance;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = mInstance.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mInstance.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前系统语言
     *
     * @return 当前系统语言
     */
    public static String getLanguage() {
        Locale locale = mInstance.getResources().getConfiguration().locale;
        String language = locale.getDefault().toString();
        return language;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }

}

package com.hunter.fastandroid.app;

import cache.greendao.DaoMaster;
import cache.greendao.DaoSession;
import cache.greendao.UserDao;

/**
 * GreenDao 操作管理器
 *
 * @author Hunter
 */
public class DaoManager {
    private static DaoManager instance;

    private DaoSession daoSession;

    private DaoManager() {
    }

    public static DaoManager getInstance() {
        if (instance == null) {
            synchronized (DaoManager.class) {
                if (instance == null)
                    instance = new DaoManager();
            }
        }

        return instance;
    }

    private DaoSession getDaoSession() {
        if (daoSession == null) {
            // 可在此自定义版本升级时的操作,当前默认为删除旧版本所有数据
            daoSession = DaoMaster.newDevSession(BaseApplication.getInstance(), Constants.DATABASE_NAME);
        }

        return daoSession;
    }

    public UserDao getUserDao() {
        return getDaoSession().getUserDao();
    }
}

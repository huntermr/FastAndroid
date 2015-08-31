package com.hunter.fastandroid.dao;

import android.content.Context;

import com.hunter.fastandroid.DaoSession;
import com.hunter.fastandroid.User;
import com.hunter.fastandroid.UserDao;
import com.hunter.fastandroid.app.BaseApplication;

import java.util.List;

/**
 * 测试数据库操作类,用于操作sqlite
 *
 * @author Ht
 */
public class CustomUserDao {
    private static CustomUserDao mInstance;
    private DaoSession mDaoSession;

    private UserDao mUserDao;

    private CustomUserDao() {
    }

    public static CustomUserDao getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CustomUserDao();
            mInstance.mDaoSession = BaseApplication.getDaoSession(context);
            mInstance.mUserDao = mInstance.mDaoSession.getUserDao();
        }

        return mInstance;
    }

    /**
     * 增加或修改某条数据
     *
     * @param user
     */
    public void saveUser(User user) {
        mUserDao.insertOrReplace(user);
    }

    /**
     * 批量添加数据(开启线程)
     *
     * @param list
     */
    public void saveUserLists(final List<User> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        mUserDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    User user = list.get(i);
                    // 查询要插入的数据是否已存在
                    User cache = queryUser("WHERE USERNAME=?",
                            user.getName());
                    if (cache != null) {
                        user.setId(cache.getId());
                    }

                    mUserDao.insertOrReplace(user);
                }
            }
        });
    }

    /**
     * 删除指定id的数据
     *
     * @param id
     */
    public void deleteUser(long id) {
        mUserDao.deleteByKey(id);
    }

    /**
     * 删除指定的数据
     *
     * @param user
     */
    public void deleteUser(User user) {
        mUserDao.delete(user);
    }

    /**
     * 删除全部数据
     */
    public void deleteAllUser() {
        mUserDao.getSession().runInTx(new Runnable() {

            @Override
            public void run() {
                mUserDao.deleteAll();
            }
        });
    }

    /**
     * 查找指定数据
     *
     * @param id
     * @return
     */
    public User queryUser(long id) {
        return mUserDao.load(id);
    }

    /**
     * 查找全部数据
     *
     * @return
     */
    public List<User> queryAllUser() {
        return mUserDao.loadAll();
    }

    /**
     * 根据条件查找多条数据
     *
     * @param where
     * @param params
     * @return
     */
    public List<User> queryUsers(String where, String... params) {
        return mUserDao.queryRaw(where, params);
    }

    /**
     * 根据条件查找单条数据
     *
     * @param where
     * @param params
     * @return
     */
    public User queryUser(String where, String... params) {
        List<User> queryRaw = mUserDao.queryRaw(where, params);
        if (queryRaw != null && queryRaw.size() > 0)
            return queryRaw.get(0);
        return null;
    }

}

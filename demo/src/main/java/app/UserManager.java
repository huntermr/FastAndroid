package demo.app;

import java.util.List;

import cache.greendao.UserDao;
import demo.vo.response.user.User;

/**
 * Created by Administrator on 2017/1/10.
 */
public class UserManager {
    private static UserManager instance;
    private User currentUser;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null)
                    instance = new UserManager();
            }
        }

        return instance;
    }

    /**
     * 当前是否已经登录
     *
     * @return
     */
    public boolean isLogin() {
        return getCurrentUser() != null;
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public User getCurrentUser() {
        if (currentUser == null) {
            DaoManager daoManager = DaoManager.getInstance();
            UserDao userDao = daoManager.getUserDao();
            List<User> users = userDao.loadAll();
            if (users != null && users.size() > 0) {
                currentUser = users.get(0);
            }
        }

        return currentUser;
    }

    /**
     * 保存用户信息,该方法一般用于登录成功后调用 (用户数据先放入内存,然后序列化到本地.如果本地已存在,则更新)
     *
     * @param user
     */
    public void setCurrentUser(User user) {
        currentUser = user;

        DaoManager daoManager = DaoManager.getInstance();
        UserDao userDao = daoManager.getUserDao();
        userDao.insertOrReplace(user);
    }

    /**
     * 清空用户信息,包括内存以及本地的数据
     */
    public void clearUserInfo() {
        currentUser = null;

        DaoManager daoManager = DaoManager.getInstance();
        UserDao userDao = daoManager.getUserDao();
        userDao.deleteAll();
    }

    /**
     * 获取用户ID
     * @return
     */
    public String getCurrentUserID() {
        if (getCurrentUser() == null) return "";

        return String.valueOf(getCurrentUser().getCustomerId());
    }

    /**
     * 获取用户令牌
     * @return
     */
    public String getCurrentUserToken() {
        if (getCurrentUser() == null) return "";

        return getCurrentUser().getToken();
    }
}

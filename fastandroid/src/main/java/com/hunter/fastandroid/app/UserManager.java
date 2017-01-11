package com.hunter.fastandroid.app;

import com.hunter.fastandroid.vo.User;

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

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public String getCurrentUserToken() {
        if (currentUser == null) return "";

//        return currentUser.getUserToken();
        return "";
    }
}

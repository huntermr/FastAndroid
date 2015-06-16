package com.hunter.fastandroid.presenters.interfaces.user;

import com.hunter.fastandroid.bean.request.RegisterUser;

/**
 * 演示Presenter接口
 */

public interface IRegisterPresenter {
    /**
     * 注册
     * @param registerUser
     */
    void register(RegisterUser registerUser);
}

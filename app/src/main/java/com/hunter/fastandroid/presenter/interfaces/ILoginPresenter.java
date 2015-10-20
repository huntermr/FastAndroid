package com.hunter.fastandroid.presenter.interfaces;

import com.hunter.fastandroid.vo.request.LoginRequest;

/**
 * Created by Administrator on 2015/10/20.
 */
public interface ILoginPresenter {
    /**
     * 登录
     * @param loginRequest
     */
    void login(LoginRequest loginRequest);
}

package com.hunter.fastandroid.model.interfaces;

import com.hunter.fastandroid.bean.request.RegisterUser;
import com.hunter.fastandroid.bean.response.Login;
import com.hunter.fastandroid.listener.TransactionListener;

/**
 * 用户模型接口
 */
public interface IUserModel {
    /**
     * 注册
     * @param registerUser  注册参数实体类
     * @param listener
     */
    void register(RegisterUser registerUser, TransactionListener listener);

    /**
     * 获取用户信息
     * @param listener
     */
    void getUserInfo(TransactionListener listener);
}

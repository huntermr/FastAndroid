package com.hunter.fastandroid.model.interfaces;

import com.hunter.fastandroid.net.JsonTransactionListener;
import com.hunter.fastandroid.vo.request.LoginRequest;

/**
 * 用户模型接口
 */
public interface IUserModel {
    void login(LoginRequest loginRequest, JsonTransactionListener transactionListener);
}

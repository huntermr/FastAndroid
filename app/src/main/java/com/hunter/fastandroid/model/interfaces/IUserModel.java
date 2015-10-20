package com.hunter.fastandroid.model.interfaces;

import com.hunter.fastandroid.net.TransactionListener;
import com.hunter.fastandroid.vo.request.LoginRequest;

/**
 * 用户模型接口
 */
public interface IUserModel {
    void login(LoginRequest loginRequest, TransactionListener transactionListener);
}

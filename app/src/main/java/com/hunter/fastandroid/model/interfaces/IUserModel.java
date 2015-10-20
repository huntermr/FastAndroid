package com.hunter.fastandroid.model.interfaces;

import com.hunter.fastandroid.net.TransactionListener;

/**
 * 用户模型接口
 */
public interface IUserModel {
    void login(String userName, String password, TransactionListener transactionListener);
}

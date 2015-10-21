package com.hunter.fastandroid.model.impl;

import com.hunter.fastandroid.base.BaseModel;
import com.hunter.fastandroid.model.interfaces.IUserModel;
import com.hunter.fastandroid.net.TransactionListener;
import com.hunter.fastandroid.vo.request.LoginRequest;

/**
 * 用户模型实现类
 */
public class UserModelImpl extends BaseModel implements IUserModel{
    @Override
    public void login(LoginRequest loginRequest, TransactionListener transactionListener) {
        post("http://www.baidu.com", loginRequest, transactionListener);
    }
}

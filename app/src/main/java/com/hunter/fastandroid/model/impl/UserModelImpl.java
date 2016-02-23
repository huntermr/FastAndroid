package com.hunter.fastandroid.model.impl;

import android.content.Context;

import com.hunter.fastandroid.base.BaseModel;
import com.hunter.fastandroid.model.interfaces.IUserModel;
import com.hunter.fastandroid.net.TransactionListener;
import com.hunter.fastandroid.vo.request.LoginRequest;

/**
 * 用户模型实现类
 */
public class UserModelImpl extends BaseModel implements IUserModel {

    public UserModelImpl(Context context) {
        super(context);
    }

    @Override
    public void login(LoginRequest loginRequest, TransactionListener transactionListener) {
        post(getContext(), "http://www.baidu.com", loginRequest, transactionListener);
    }
}

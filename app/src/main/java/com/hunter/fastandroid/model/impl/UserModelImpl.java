package com.hunter.fastandroid.model.impl;

import com.hunter.fastandroid.base.BaseModel;
import com.hunter.fastandroid.model.interfaces.IUserModel;
import com.hunter.fastandroid.net.TransactionListener;
import com.hunter.fastandroid.vo.request.LoginRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户模型实现类
 */
public class UserModelImpl extends BaseModel implements IUserModel{
    @Override
    public void login(LoginRequest loginRequest, TransactionListener transactionListener) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", loginRequest.userName);
        params.put("password", loginRequest.password);

        post("http://www.baidu.com", params, transactionListener);
    }
}

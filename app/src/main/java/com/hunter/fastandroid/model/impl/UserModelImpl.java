package com.hunter.fastandroid.model.impl;

import com.hunter.fastandroid.base.BaseModel;
import com.hunter.fastandroid.model.interfaces.IUserModel;
import com.hunter.fastandroid.net.TransactionListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户模型实现类
 */
public class UserModelImpl extends BaseModel implements IUserModel{
    @Override
    public void login(String userName, String password, TransactionListener transactionListener) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);

        get("http://www.baidu.com", transactionListener);
    }
}

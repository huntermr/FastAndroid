package com.hunter.fastandroid.model.impl;

import com.hunter.fastandroid.base.Response;
import com.hunter.fastandroid.bean.request.RegisterUser;
import com.hunter.fastandroid.bean.response.Login;
import com.hunter.fastandroid.bean.response.User;
import com.hunter.fastandroid.listener.TransactionListener;
import com.hunter.fastandroid.model.interfaces.IUserModel;
import com.hunter.fastandroid.net.NetCenter;
import com.hunter.fastandroid.net.ResponseCode;
import com.hunter.fastandroid.net.TransactionHttpResponseHandler;
import com.hunter.fastandroid.net.URLs;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;

/**
 * 用户模型实现类
 */
public class UserModelImpl implements IUserModel {

    @Override
    public void register(RegisterUser registerUser, TransactionListener listener) {
        NetCenter.post(URLs.getURL(URLs.USER_SIGNUP), registerUser, new TransactionHttpResponseHandler(listener));
    }

    /**
     * 获取用户信息
     *
     * @param listener
     */
    public void getUserInfo(final TransactionListener listener) {
        NetCenter.get("url", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFailure(ResponseCode.ERROR_NETWORK);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Response response = Response.getResponse(responseString);
                if (response.getCode() == ResponseCode.SUCCESS) {
                    User bean = response.getBean(User.class);
                    listener.onSuccess(bean);
                } else {
                    listener.onFailure(response.getCode());
                }
            }
        });
    }

}

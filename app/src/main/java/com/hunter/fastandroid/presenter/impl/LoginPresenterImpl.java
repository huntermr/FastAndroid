package com.hunter.fastandroid.presenter.impl;

import android.text.TextUtils;

import com.hunter.fastandroid.base.BasePresenter;
import com.hunter.fastandroid.model.impl.UserModelImpl;
import com.hunter.fastandroid.model.interfaces.IUserModel;
import com.hunter.fastandroid.net.TransactionListener;
import com.hunter.fastandroid.presenter.interfaces.ILoginPresenter;
import com.hunter.fastandroid.ui.view.interfaces.ILoginView;
import com.hunter.fastandroid.utils.CommonUtils;
import com.hunter.fastandroid.vo.request.LoginRequest;
import com.hunter.fastandroid.vo.response.UserInfo;

public class LoginPresenterImpl extends BasePresenter<ILoginView> implements ILoginPresenter {

    IUserModel userModel;

    public LoginPresenterImpl(ILoginView view) {
        super(view);
    }

    @Override
    public void initModel() {
        userModel = new UserModelImpl();
    }

    @Override
    public void login(LoginRequest loginRequest) {
        if(TextUtils.isEmpty(loginRequest.userName)){
            mView.showToast("用户名不能为空");
            return;
        }

        if(TextUtils.isEmpty(loginRequest.password)){
            mView.showToast("密码不能为空");
            return;
        }

        userModel.login(loginRequest, new TransactionListener() {
            @Override
            public void onSuccess(String data) {
                UserInfo userInfo = CommonUtils.getGson().fromJson(data, UserInfo.class);
                mView.loginCallback(userInfo);
            }
        });
    }
}

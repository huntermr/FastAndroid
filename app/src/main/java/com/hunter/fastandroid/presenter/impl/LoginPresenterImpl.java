package com.hunter.fastandroid.presenter.impl;

import com.hunter.fastandroid.base.BasePresenter;
import com.hunter.fastandroid.model.impl.UserModelImpl;
import com.hunter.fastandroid.model.interfaces.IUserModel;
import com.hunter.fastandroid.net.TransactionListener;
import com.hunter.fastandroid.presenter.interfaces.ILoginPresenter;
import com.hunter.fastandroid.ui.view.interfaces.ILoginView;

/**
 * Created by Administrator on 2015/10/20.
 */
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
    public void login(String userName, String password) {
        userModel.login(userName, password, new TransactionListener() {
            @Override
            public void onSuccess(String data) {

                mView.loginCallback(data);
                super.onSuccess(data);
            }
        });
    }
}

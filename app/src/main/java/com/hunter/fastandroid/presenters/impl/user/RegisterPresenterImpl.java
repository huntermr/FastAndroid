package com.hunter.fastandroid.presenters.impl.user;

import android.text.TextUtils;

import com.hunter.fastandroid.app.MyApplication;
import com.hunter.fastandroid.base.BasePresenter;
import com.hunter.fastandroid.base.IBaseView;
import com.hunter.fastandroid.bean.request.RegisterUser;
import com.hunter.fastandroid.bean.response.Login;
import com.hunter.fastandroid.listener.TransactionListener;
import com.hunter.fastandroid.model.impl.UserModelImpl;
import com.hunter.fastandroid.model.interfaces.IUserModel;
import com.hunter.fastandroid.net.NetCenter;
import com.hunter.fastandroid.presenters.interfaces.user.IRegisterPresenter;
import com.hunter.fastandroid.ui.views.user.IRegisterView;
import com.hunter.fastandroid.utils.CommonUtils;
import com.hunter.fastandroid.utils.ErrorCodeUtils;

/**
 * 演示Presenter实现类
 */
public class RegisterPresenterImpl extends BasePresenter<IRegisterView> implements IRegisterPresenter {
    IUserModel mModel;

    public RegisterPresenterImpl(IRegisterView view) {
        super(view);
    }

    @Override
    public void initModel() {
        mModel = new UserModelImpl();
    }

    @Override
    public void register(RegisterUser registerUser) {
        // 校验用户名
        if (TextUtils.isEmpty(registerUser.getUsername())) {
            mView.showToast("用户名不能为空");
            return;
        }

        // 校验密码
        if (TextUtils.isEmpty(registerUser.getPassword())) {
            mView.showToast("密码不能为空");
            return;
        }

        registerUser.setPassword(CommonUtils.getMD5(registerUser.getPassword()));

        mView.showProgress();

        mModel.register(registerUser, new TransactionListener() {
            @Override
            public void onSuccess() {
                mView.hideProgress();

                // 注册成功回调
                mView.registerSuccess();
            }

            @Override
            public void onFailure(int errorCode) {
                super.onFailure(errorCode);

                int res = ErrorCodeUtils.getRes(errorCode);
                mView.showToast(res);
                mView.hideProgress();
            }
        });
    }
}

package com.hunter.fastandroid.ui.activity;

import com.hunter.fastandroid.R;
import com.hunter.fastandroid.base.BaseActivity;
import com.hunter.fastandroid.bean.request.RegisterUser;
import com.hunter.fastandroid.presenters.impl.user.RegisterPresenterImpl;
import com.hunter.fastandroid.presenters.interfaces.user.IRegisterPresenter;
import com.hunter.fastandroid.ui.custom.HeaderLayout;
import com.hunter.fastandroid.ui.views.user.IRegisterView;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements IRegisterView {
    private IRegisterPresenter mPresenter;

    HeaderLayout titleBar;

    @Override
    public void initContentView() {
        // 设置布局文件
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initTitleBar() {
        titleBar = getTitleBar();
        titleBar.setTitle("xxx");
    }

    @Override
    public void initView() {
        RegisterUser hunter = new RegisterUser("hunter", "hunter_android@163.com", "123456");
        mPresenter.register(hunter);
    }

    @Override
    public void initPresenter() {
        mPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    public void registerSuccess() {
        // TODO 注册成功,去登陆
    }
}

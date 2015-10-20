package com.hunter.fastandroid.ui.activity;

import android.widget.TextView;

import com.hunter.fastandroid.R;
import com.hunter.fastandroid.base.BaseActivity;
import com.hunter.fastandroid.presenter.impl.LoginPresenterImpl;
import com.hunter.fastandroid.presenter.interfaces.ILoginPresenter;
import com.hunter.fastandroid.ui.custom.TitleBar;
import com.hunter.fastandroid.ui.view.interfaces.ILoginView;
import com.hunter.fastandroid.vo.request.LoginRequest;
import com.hunter.fastandroid.vo.response.UserInfo;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements ILoginView {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.tv_content)
    TextView tvContent;

    ILoginPresenter loginPresenter;

    @Override
    public void initContentView() {
        // 设置布局文件
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        titleBar.setTitle("测试页面");
        tvContent.setText("登录中...");
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.userName = "";
        loginRequest.password = "";
        loginPresenter.login(loginRequest);
    }

    @Override
    public void initPresenter() {
        loginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void loginCallback(UserInfo userInfo) {
        tvContent.setText(userInfo.toString());
    }
}

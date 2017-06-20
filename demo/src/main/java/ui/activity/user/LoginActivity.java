package demo.ui.activity.user;

import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.tbl.android.R;
import demo.app.UserManager;
import demo.base.BaseActivity;
import demo.presenter.UserPresenter;
import demo.ui.interfaces.user.ILoginView;
import demo.ui.widget.TitleBar;
import demo.vo.UserPwd;
import demo.vo.response.user.User;

/**
 * Created by Administrator on 2017/5/5.
 */

public class LoginActivity extends BaseActivity implements ILoginView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_pwd)
    EditText etPwd;

    UserPresenter userPresenter;

    @Override
    public void initView() {
        titleBar.setTitle(R.string.login_title);
        TextView register = new TextView(this);
        register.setText(R.string.quick_register);
        register.setTextColor(getResources().getColor(R.color.colorAccent));
        register.setTextSize(16);
        register.setGravity(Gravity.CENTER_VERTICAL);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(RegisterActivity.class);
            }
        });
        titleBar.setRightView(register);
    }

    @OnClick(R.id.btn_login)
    void goLogin() {
        UserPwd userPwd = new UserPwd();
        String mobile = etUser.getText().toString();
        String pwd = etPwd.getText().toString();

        userPwd.setMobile(mobile);
        userPwd.setPwd(pwd);

        userPresenter.login(this, userPwd);
    }

    @OnClick(R.id.tv_forget)
    void forgetPwd() {
        openPage(ForgetActivity.class);
    }

    @Override
    public void initPresenter() {
        userPresenter = new UserPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_login;
    }

    @Override
    public void uiLogin(User user) {
        // 登录成功,开始存储用户信息
        UserManager.getInstance().setCurrentUser(user);

        goHome();
    }
}

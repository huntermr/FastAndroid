package demo.ui.activity.user;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import demo.app.UserManager;
import demo.base.BaseActivity;
import demo.presenter.UserPresenter;
import demo.ui.interfaces.user.ILoginView;
import demo.ui.interfaces.user.IRegisterView;
import demo.ui.interfaces.user.ISendMsgView;
import demo.ui.widget.CountDownButton;
import demo.ui.widget.PasswordEditText;
import demo.ui.widget.TitleBar;
import demo.vo.UserPwd;
import demo.vo.request.SendMsgRequest;
import demo.vo.response.user.User;

/**
 * Created by Administrator on 2017/5/5.
 */

public class RegisterActivity extends BaseActivity implements IRegisterView, ILoginView, ISendMsgView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.et_user)
    EditText etMobile;
    @BindView(R.id.et_verif_code)
    EditText etVerifCode;
    @BindView(R.id.et_pwd)
    PasswordEditText etPwd;
    @BindView(R.id.btn_send_msg)
    CountDownButton btnSendMsg;

    UserPresenter userPresenter;
    UserPwd userPwd;

    @Override
    public void initView() {
        titleBar.setTitle(R.string.register_title);
        titleBar.setLeftView(fillBackButton());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = etMobile.getText().toString();
                String verifCode = etVerifCode.getText().toString();
                String pwd = etPwd.getInputPassword();

                userPwd = new UserPwd();
                userPwd.setMobile(mobile);
                userPwd.setCaptcha(verifCode);
                userPwd.setPwd(pwd);

                userPresenter.register(RegisterActivity.this, userPwd);
            }
        });

        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = etMobile.getText().toString();

                SendMsgRequest request = new SendMsgRequest();
                request.setType(0);
                request.setMobile(mobile);
                userPresenter.sendMsg(RegisterActivity.this, request);
            }
        });
    }

    @Override
    public void initPresenter() {
        userPresenter = new UserPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_register;
    }

    @Override
    public void uiRegister() {
        // 注册成功,调用登录接口
        userPresenter.login(this, userPwd);
    }

    @Override
    public void uiLogin(User user) {
        // 登录成功,开始存储用户信息
        UserManager.getInstance().setCurrentUser(user);

        goHome();
    }

    @Override
    public void uiSendMsg() {
        btnSendMsg.startCountDown();
    }
}

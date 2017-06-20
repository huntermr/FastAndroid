package demo.ui.activity.user;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import demo.app.UserManager;
import demo.base.BaseActivity;
import demo.presenter.UserPresenter;
import demo.ui.interfaces.user.ISendMsgView;
import demo.ui.interfaces.user.IUpdatePwdView;
import demo.ui.widget.CountDownButton;
import demo.ui.widget.PasswordEditText;
import demo.ui.widget.TitleBar;
import demo.vo.request.SendMsgRequest;
import demo.vo.request.UpdatePwdRequest;
import demo.vo.response.user.User;

/**
 * Created by Administrator on 2017/5/12.
 */

public class UpdatePwdActivity extends BaseActivity implements ISendMsgView, IUpdatePwdView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_verif_code)
    EditText etVerifCode;
    @BindView(R.id.et_pwd)
    PasswordEditText etPwd;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_send_msg)
    CountDownButton btnSendMsg;

    User currentUser = UserManager.getInstance().getCurrentUser();
    String mobile = currentUser.getMobile();

    UserPresenter userPresenter;

    @Override
    public void initView() {
        titleBar.setTitle(R.string.update_pwd);
        titleBar.setLeftView(fillBackButton());

        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePwd();
            }
        });
    }

    @Override
    public void initPresenter() {
        userPresenter = new UserPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_update_pwd;
    }

    private void sendMsg(){
        SendMsgRequest request = new SendMsgRequest();
        request.setMobile(mobile);
        request.setType(1);

        userPresenter.sendMsg(this, request);
    }

    private void updatePwd(){
        String verifCode = etVerifCode.getText().toString();
        String pwd = etPwd.getInputPassword();

        UpdatePwdRequest request = new UpdatePwdRequest();
        request.setMobile(mobile);
        request.setCaptcha(verifCode);
        request.setPassword(pwd);

        userPresenter.updatePwd(this, request);
    }

    @Override
    public void uiSendMsg() {
        btnSendMsg.startCountDown();
    }

    @Override
    public void uiUpdatePwd() {
        showToast("密码修改成功，请重新登录");
        clearUser();
    }
}

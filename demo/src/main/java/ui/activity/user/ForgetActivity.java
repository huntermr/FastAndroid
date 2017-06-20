package demo.ui.activity.user;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.tbl.android.R;
import demo.base.BaseActivity;
import demo.presenter.UserPresenter;
import demo.ui.interfaces.user.ISendMsgView;
import demo.ui.interfaces.user.IUpdatePwdView;
import demo.ui.widget.CountDownButton;
import demo.ui.widget.PasswordEditText;
import demo.ui.widget.TitleBar;
import demo.vo.request.SendMsgRequest;
import demo.vo.request.UpdatePwdRequest;

/**
 * Created by Administrator on 2017/5/5.
 */

public class ForgetActivity extends BaseActivity implements ISendMsgView, IUpdatePwdView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_verif_code)
    EditText etVerifCode;
    @BindView(R.id.et_pwd)
    PasswordEditText etPwd;
    @BindView(R.id.btn_send_msg)
    CountDownButton btnSendMsg;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    UserPresenter userPresenter;

    @Override
    public void initView() {
        titleBar.setTitle(R.string.forget_pwd);
        titleBar.setLeftView(fillBackButton());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePwd();
            }
        });
    }

    @OnClick(R.id.btn_send_msg)
    void sendVerifCode() {
        String mobile = etMobile.getText().toString();

        SendMsgRequest request = new SendMsgRequest();
        request.setMobile(mobile);
        request.setType(1);

        userPresenter.sendMsg(this, request);
    }

    @Override
    public void initPresenter() {
        userPresenter = new UserPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_forget;
    }

    private void updatePwd(){
        String mobile = etMobile.getText().toString();
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
        showToast("修改成功");
        close();
    }
}

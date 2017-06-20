package demo.ui.interfaces.user;

import demo.base.IBaseView;

/**
 * Created by Administrator on 2017/5/5.
 */

public interface IVerifCodeView extends IBaseView {
    /**
     * 发送验证码成功
     */
    void uiVerifCode();

    /**
     * 获取验证码失败
     */
    void failVerifCode();
}

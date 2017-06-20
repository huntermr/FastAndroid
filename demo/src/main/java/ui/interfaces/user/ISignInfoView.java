package demo.ui.interfaces.user;

import demo.base.IBaseView;
import demo.vo.response.user.SignIn;

/**
 * Created by Administrator on 2017/5/27.
 */

public interface ISignInfoView extends IBaseView {
    /**
     * 获取签到信息成功
     */
    void uiSignInfo(SignIn signIn);
}

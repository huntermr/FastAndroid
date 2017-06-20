package demo.ui.interfaces.user;

import demo.base.IBaseView;
import demo.vo.response.user.UserInfo;

/**
 * Created by Administrator on 2017/5/27.
 */

public interface IUserInfoView extends IBaseView {
    /**
     * 获取用户信息成功
     * @param userInfo
     */
    void uiUserInfo(UserInfo userInfo);
}

package com.hunter.fastandroid.ui.view.interfaces;

import com.hunter.fastandroid.base.IBaseView;
import com.hunter.fastandroid.vo.response.UserInfo;

/**
 * 用户登录View接口
 */
public interface ILoginView extends IBaseView {
    /**
     * 登录成功视图回调
     * @param userInfo
     */
    void loginCallback(UserInfo userInfo);
}

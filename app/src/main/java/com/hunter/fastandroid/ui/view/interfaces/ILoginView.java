package com.hunter.fastandroid.ui.view.interfaces;

import com.hunter.fastandroid.base.IBaseView;

/**
 * Created by Administrator on 2015/10/20.
 */
public interface ILoginView extends IBaseView {
    /**
     *  登录成功视图回调
     */
    void loginCallback(String message);
}

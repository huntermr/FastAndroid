package com.hunter.fastandroid.presenter.interfaces;

import com.hunter.fastandroid.ui.view.interfaces.ITestView;
import com.hunter.fastandroid.vo.request.QueryParameter;

/**
 * Created by Administrator on 2016/2/23.
 */
public interface ITestPresenter {
    /**
     * 归属地查询
     *
     * @param testView
     * @param queryParameter
     */
    void attributionToInquiries(ITestView testView, QueryParameter queryParameter);
}

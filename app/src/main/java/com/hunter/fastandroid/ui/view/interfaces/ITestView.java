package com.hunter.fastandroid.ui.view.interfaces;

import com.hunter.fastandroid.base.IBaseView;
import com.hunter.fastandroid.vo.response.QueryResult;

/**
 * Created by Administrator on 2016/2/23.
 */
public interface ITestView extends IBaseView {
    /**
     * 获取归属地查询结果
     *
     * @param result
     */
    void queryResult(QueryResult result);
}

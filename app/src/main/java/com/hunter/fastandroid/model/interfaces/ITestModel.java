package com.hunter.fastandroid.model.interfaces;

import com.hunter.fastandroid.net.StringTransactionListener;
import com.hunter.fastandroid.vo.request.QueryParameter;

/**
 * Created by Administrator on 2016/2/23.
 */
public interface ITestModel {
    /**
     * 归属地查询
     *
     * @param queryParameter
     * @param transactionListener
     */
    void attributionToInquiries(QueryParameter queryParameter, StringTransactionListener transactionListener);
}

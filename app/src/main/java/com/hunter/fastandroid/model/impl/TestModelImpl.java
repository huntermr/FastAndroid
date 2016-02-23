package com.hunter.fastandroid.model.impl;

import android.content.Context;

import com.hunter.fastandroid.base.BaseModel;
import com.hunter.fastandroid.model.interfaces.ITestModel;
import com.hunter.fastandroid.net.StringTransactionListener;
import com.hunter.fastandroid.net.URLs;
import com.hunter.fastandroid.vo.request.QueryParameter;

/**
 * Created by Administrator on 2016/2/23.
 */
public class TestModelImpl extends BaseModel implements ITestModel {

    public TestModelImpl(Context context) {
        super(context);
    }

    @Override
    public void attributionToInquiries(QueryParameter queryParameter, StringTransactionListener transactionListener) {
        get(getContext(), URLs.getURL(URLs.TEST_API), queryParameter, transactionListener);
    }
}

package com.hunter.fastandroid.net;

import com.loopj.android.http.TextHttpResponseHandler;
import com.hunter.fastandroid.base.Response;

import org.apache.http.Header;

public class TransactionHttpResponseHandler extends TextHttpResponseHandler {
    private TransactionListener mTransactionListener;

    /**
     * 自定义http响应处理器
     *
     * @param mTransactionListener
     */
    public TransactionHttpResponseHandler(TransactionListener mTransactionListener) {
        this.mTransactionListener = mTransactionListener;
        setCharset(NetCenter.CONTENT_ENCODING);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        mTransactionListener.onFailure(ResponseCode.ERROR_NETWORK);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Response response = Response.getResponse(responseString);
        if (response.getCode() == ResponseCode.SUCCESS) {
            mTransactionListener.onSuccess(response.getData());
        } else {
            mTransactionListener.onFailure(response.getCode());
        }
    }
}

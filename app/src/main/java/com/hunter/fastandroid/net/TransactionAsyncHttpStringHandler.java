package com.hunter.fastandroid.net;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

public class TransactionAsyncHttpStringHandler extends TextHttpResponseHandler {
    TransactionListener mTransactionListener;

    /**
     * 自定义http响应处理器
     *
     * @param mTransactionListener
     */
    public TransactionAsyncHttpStringHandler(TransactionListener mTransactionListener) {
        this.mTransactionListener = mTransactionListener;
        setCharset(AsyncHttpNetCenter.CONTENT_ENCODING);
    }

    void sendResponse(String responseString){
        mTransactionListener.onSuccess(responseString);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        mTransactionListener.onFailure(ResponseCode.ERROR_NETWORK);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        sendResponse(responseString);
    }
}

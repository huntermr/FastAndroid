package com.hunter.fastandroid.net;

import com.hunter.fastandroid.base.JsonResponse;
import com.hunter.fastandroid.utils.Logger;
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

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        mTransactionListener.onFailure(ResponseCode.ERROR_NETWORK);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Logger.e("HTTP-Response,data：" + responseString);

        JsonResponse response = JsonResponse.getResponse(responseString);
        mTransactionListener.onSuccess(response);
    }
}

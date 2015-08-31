package com.hunter.fastandroid.net;

import com.loopj.android.http.TextHttpResponseHandler;
import com.hunter.fastandroid.base.Response;

import org.apache.http.Header;

public class TransactionHttpResponseHandler extends TextHttpResponseHandler {
    private TransactionListener listener;

    /**
     * 自定义http响应处理器
     *
     * @param listener
     */
    public TransactionHttpResponseHandler(TransactionListener listener) {
        this.listener = listener;
        setCharset(NetCenter.CONTENT_ENCODING);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        listener.onFailure(ResponseCode.ERROR_NETWORK);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Response response = Response.getResponse(responseString);
        if (response.getCode() == ResponseCode.SUCCESS) {
            listener.onSuccess(response.getData());
        } else {
            listener.onFailure(response.getCode());
        }
    }
}

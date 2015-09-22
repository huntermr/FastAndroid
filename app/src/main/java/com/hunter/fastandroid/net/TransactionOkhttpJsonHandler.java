package com.hunter.fastandroid.net;

import com.hunter.fastandroid.base.JsonResponse;

/**
 * 该类用于封装Okhttp回调方法,直接返回服务器的响应信息
 */
public class TransactionOkhttpJsonHandler extends TransactionOkhttpStringHandler {

    public TransactionOkhttpJsonHandler(TransactionListener mTransactionListener) {
        super(mTransactionListener);
    }

    @Override
    void sendResponse(String responseString) {
        JsonResponse response = JsonResponse.getResponse(responseString);
        if (response.getCode() == ResponseCode.SUCCESS) {
            super.sendResponse(response.getData());
        } else {
            mTransactionListener.onFailure(response.getCode());
        }
    }
}

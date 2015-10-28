package com.hunter.fastandroid.base;

import com.hunter.fastandroid.net.AsyncHttpNetCenter;
import com.hunter.fastandroid.net.TransactionAsyncHttpStringHandler;
import com.hunter.fastandroid.net.TransactionListener;
import com.loopj.android.http.RequestParams;

import java.util.Collections;
import java.util.Map;

/**
 * 公共Model,所有Model继承自此类
 */
public abstract class BaseModel {
    public void get(String url, TransactionListener transactionListener) {
        get(url, Collections.<String, String>emptyMap(), transactionListener);
    }

    public <T extends BaseRequest> void get(String url, T t, TransactionListener transactionListener) {
        AsyncHttpNetCenter.getInstance().get(url, t, new TransactionAsyncHttpStringHandler(transactionListener));
    }

    public void get(String url, Map<String, String> params, TransactionListener transactionListener) {
        AsyncHttpNetCenter.getInstance().get(url, params, new TransactionAsyncHttpStringHandler(transactionListener));
    }

    public void post(String url, TransactionListener transactionListener) {
        post(url, Collections.<String, String>emptyMap(), transactionListener);
    }

    public <T extends BaseRequest> void post(String url, T t, TransactionListener transactionListener) {
        AsyncHttpNetCenter.getInstance().post(url, t, new TransactionAsyncHttpStringHandler(transactionListener));
    }

    public void post(String url, RequestParams params, TransactionListener transactionListener) {
        AsyncHttpNetCenter.getInstance().post(url, params, new TransactionAsyncHttpStringHandler(transactionListener));
    }

    public void post(String url, Map<String, String> params, TransactionListener transactionListener) {
        AsyncHttpNetCenter.getInstance().post(url, params, new TransactionAsyncHttpStringHandler(transactionListener));
    }

    public void setHeader(String header, String value) {
        AsyncHttpNetCenter.getInstance().setHeader(header, value);
    }

    public void removeHeader(String header) {
        AsyncHttpNetCenter.getInstance().removeHeader(header);
    }

}

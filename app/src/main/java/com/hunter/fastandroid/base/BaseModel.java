package com.hunter.fastandroid.base;

import com.hunter.fastandroid.net.OkHttpNetCenter;
import com.hunter.fastandroid.net.TransactionListener;
import com.hunter.fastandroid.net.TransactionOkhttpStringHandler;

import java.util.Collections;
import java.util.Map;

/**
 * 公共Model,所有Model继承自此类
 */
public abstract class BaseModel {
    public void get(String url, TransactionListener transactionListener){
        get(url, Collections.<String, String>emptyMap(), transactionListener);
    }

    public <T extends BaseRequest> void get(String url, T t, TransactionListener transactionListener){
        OkHttpNetCenter.getInstance().get(url, t, new TransactionOkhttpStringHandler(transactionListener));
    }

    public void get(String url, Map<String, String> params, TransactionListener transactionListener){
        OkHttpNetCenter.getInstance().get(url, params, new TransactionOkhttpStringHandler(transactionListener));
    }

    public void post(String url, TransactionListener transactionListener){
        post(url, Collections.<String, String>emptyMap(), transactionListener);
    }

    public <T extends BaseRequest> void post(String url, T t, TransactionListener transactionListener){
        OkHttpNetCenter.getInstance().post(url, t, new TransactionOkhttpStringHandler(transactionListener));
    }

    public void post(String url, Map<String, String> params, TransactionListener transactionListener){
        OkHttpNetCenter.getInstance().post(url, params, new TransactionOkhttpStringHandler(transactionListener));
    }

    public void setHeader(String header, String value){
        OkHttpNetCenter.getInstance().setHeader(header, value);
    }

    public void removeHeader(String header){
        OkHttpNetCenter.getInstance().removeHeader(header);
    }

}

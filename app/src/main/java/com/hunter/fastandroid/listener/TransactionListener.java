package com.hunter.fastandroid.listener;

/**
 * 事务处理监听
 */
public abstract class TransactionListener<T> {
    /**
     * 无参数成功回调
     */
    public void onSuccess() {

    }

    /**
     * 带参数的成功回调
     *
     * @param t
     */
    public void onSuccess(T t) {

    }

    /**
     * 带错误码的失败回调
     *
     * @param errorCode
     */
    public void onFailure(int errorCode) {
        switch (errorCode) {
            // TODO 自定义处理
        }
    }
}

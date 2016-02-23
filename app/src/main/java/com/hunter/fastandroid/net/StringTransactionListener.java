package com.hunter.fastandroid.net;

/**
 * 事务处理监听
 */
public abstract class StringTransactionListener {
    /**
     * 带字符串数据的成功回调
     *
     * @param response
     */
    public abstract void onSuccess(String response);

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

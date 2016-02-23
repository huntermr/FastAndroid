package com.hunter.fastandroid.net;

import com.hunter.fastandroid.base.BaseResponse;

/**
 * 事务处理监听
 */
public abstract class TransactionListener<T> {
    /**
     * 带数据的成功回调
     *
     * @param response
     */
    public void onSuccess(BaseResponse response) {

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

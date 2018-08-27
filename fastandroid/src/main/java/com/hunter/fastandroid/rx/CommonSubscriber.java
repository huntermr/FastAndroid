package com.hunter.fastandroid.rx;


import com.hunter.fastandroid.base.IBaseView;
import com.hunter.fastandroid.exception.CommonException;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * RxJava 自定义Subscriber
 *
 * @param <T>
 * @author Hunter
 */
public abstract class CommonSubscriber<T> implements Observer<T> {
    private IBaseView mBaseView;
    // 是否显示加载进度条
    private boolean mIsShowLoading = true;

    public CommonSubscriber(IBaseView baseView) {
        mBaseView = baseView;
    }

    public CommonSubscriber(IBaseView baseView, boolean isShowLoading) {
        mBaseView = baseView;
        mIsShowLoading = isShowLoading;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mIsShowLoading) mBaseView.showProgress("加载中···");
    }

    @Override
    public void onError(Throwable e) {
        mBaseView.hideProgress();

        if (e instanceof CommonException) {
            mBaseView.showToast(e.getMessage());
        }else{
            Logger.e(e.getMessage());
            mBaseView.showToast("系统异常,请重试");
        }
    }

    @Override
    public void onComplete() {
        mBaseView.hideProgress();
    }
}

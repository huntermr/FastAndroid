package com.hunter.fastandroid.rx;

import android.content.DialogInterface;

import com.hunter.fastandroid.base.IBaseView;
import com.orhanobut.logger.Logger;

import rx.Subscriber;

/**
 * RxJava 自定义Subscriber
 *
 * @param <T>
 * @author Hunter
 */
public abstract class ResponseSubscriber<T> extends Subscriber<T> {
    private static final String TAG = "ResponseSubscriber";
    private IBaseView mBaseView;

    public ResponseSubscriber(IBaseView baseView) {
        mBaseView = baseView;
        mBaseView.setProgressCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ResponseSubscriber.this.unsubscribe();
            }
        });
    }

    @Override
    public void onStart() {
        mBaseView.showProgress("");
    }

    @Override
    public void onCompleted() {
        mBaseView.hideProgress();
    }

    @Override
    public void onError(Throwable e) {
        mBaseView.hideProgress();
        Logger.e(TAG, e.getMessage());
    }
}

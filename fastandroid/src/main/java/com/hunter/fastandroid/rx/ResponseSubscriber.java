package com.hunter.fastandroid.rx;

import android.content.DialogInterface;

import com.hunter.fastandroid.base.IBaseView;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * RxJava 自定义Subscriber  (使用背压时使用)
 *
 * @param <T>
 * @author Hunter
 */
public abstract class ResponseSubscriber<T> implements Subscriber<T> {
    private static final String TAG = "ResponseObserver";
    private IBaseView mBaseView;
    private Subscription subscription;

    public ResponseSubscriber(IBaseView baseView) {
        mBaseView = baseView;
        mBaseView.setProgressCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(subscription != null){
                    subscription.cancel();
                }
            }
        });
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        mBaseView.showProgress("");
    }

    @Override
    public void onError(Throwable e) {
        mBaseView.hideProgress();
        if(null != e){
            mBaseView.showToast(e.getMessage());
            Logger.e(e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        mBaseView.hideProgress();
    }
}

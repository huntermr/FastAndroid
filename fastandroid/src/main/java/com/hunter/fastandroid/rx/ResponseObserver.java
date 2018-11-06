package com.hunter.fastandroid.rx;

import com.hunter.fastandroid.R;
import com.hunter.fastandroid.base.IBaseView;
import com.hunter.fastandroid.exception.ApiException;
import com.hunter.fastandroid.vo.JsonResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * RxJava 自定义Subscriber
 *
 * @param <T>
 * @author Hunter
 */
public abstract class ResponseObserver<T> implements Observer<T> {
    private static final String TAG = "ResponseObserver";
    private IBaseView mBaseView;
    private Disposable disposable;

    public ResponseObserver(IBaseView baseView) {
        mBaseView = baseView;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
        mBaseView.showProgress(R.string.loading);
    }

    @Override
    public void onError(Throwable e) {
        mBaseView.hideProgress();
        mBaseView.showToast(e.getMessage());

        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;

            JsonResponse jsonResponse = apiException.getJsonResponse();

            if(jsonResponse.isTokenValid()){
                mBaseView.showToast(R.string.user_valid);
                mBaseView.clearUser();
            }else{
                mBaseView.showToast(jsonResponse.getMessage());
            }
        }else{
            mBaseView.showToast(R.string.system_error);
        }
    }

    @Override
    public void onComplete() {
        mBaseView.hideProgress();
    }
}

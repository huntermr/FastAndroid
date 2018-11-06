package com.hunter.fastandroid.rx;


import com.hunter.fastandroid.base.IBaseView;
import com.hunter.fastandroid.exception.ApiException;
import com.hunter.fastandroid.vo.JsonResponse;

public abstract class JsonResponseObserver<T> extends ResponseObserver<T> {
    public JsonResponseObserver(IBaseView baseView) {
        super(baseView);
    }

    @Override
    public void onNext(T t) {
        if (t instanceof JsonResponse) {
            JsonResponse response = (JsonResponse) t;
            if (!response.isSuccess()) {
                onError(new ApiException(response));
            }else{
                onSuccess(t);
            }
        }
    }

    public abstract void onSuccess(T t);
}

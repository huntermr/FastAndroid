package com.hunter.fastandroid.base;

import com.hunter.fastandroid.app.ServiceManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter基类
 *
 * @author Hunter
 */
public abstract class BasePresenter {

    public BasePresenter() {
        initService();
    }

    protected abstract void initService();

    public <T> T getService(Class<T> clazz) {
        ServiceManager serviceManager = ServiceManager.getInstance();
        return serviceManager.getService(clazz);
    }

    public <T> void subscribe(IBaseView view, Observable<T> observable, Subscriber<T> subscriber) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<T>bind())
                .subscribe(subscriber);
    }
}

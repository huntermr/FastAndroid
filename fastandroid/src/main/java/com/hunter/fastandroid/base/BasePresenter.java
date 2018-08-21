package com.hunter.fastandroid.base;

import com.hunter.fastandroid.app.ServiceManager;

import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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

    /**
     * 普通订阅
     * @param view
     * @param observable
     * @param observer
     * @param <T>
     */
    public <T> void subscribe(IBaseView view, Observable<T> observable, Observer<T> observer) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<T>bind())
                .subscribe(observer);
    }

    /**
     * 支持背压的订阅
     * @param view
     * @param flowable
     * @param subscriber
     * @param <T>
     */
    public <T> void flowableSubscribe(IBaseView view, Flowable<T> flowable, Subscriber<T> subscriber) {
        flowable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(view.<T>bind())
                .subscribe(subscriber);
    }
}

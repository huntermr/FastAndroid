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
     * 普通简单订阅
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

}

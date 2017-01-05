package com.hunter.fastandroid.base;

import android.text.TextUtils;

import com.hunter.fastandroid.app.ServiceManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 用于实现常用数据校验方法
 */
public abstract class BasePresenter {

    public BasePresenter() {
        initService();
    }

    protected abstract void initService();

    /**
     * 校验指定的字符串是否为空,如果为空则弹出指定内容的Toast
     *
     * @param verifData
     * @param view
     * @param showMessage
     * @return
     */
    public boolean isEmpty(String verifData, IBaseView view, String showMessage) {
        if (TextUtils.isEmpty(verifData)) {
            view.showToast(showMessage);
            return true;
        }

        return false;
    }

    public <T> T getService(Class<T> clazz){
        ServiceManager serviceManager = ServiceManager.getInstance();
        return serviceManager.getService(clazz);
    }

    public <T> void subscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}

package com.hunter.fastandroid.base;

/**
 * 公共Presenter,所有Presenter继承自此类
 * @param <V>
 */
public abstract class BasePresenter<V extends IBaseView> {
    public V mView;

    public BasePresenter(V view) {
        initView(view);
        initModel();
    }

    public void initView(V view) {
        this.mView = view;
    }

    public abstract void initModel();
}

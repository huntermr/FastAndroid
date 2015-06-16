package com.hunter.fastandroid.base;

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

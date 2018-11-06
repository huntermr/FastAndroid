package com.hunter.fastandroid.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.fastandroid.R;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Fragment基类
 *
 * @author Hunter
 */
public abstract class BaseFragment extends RxFragment implements IBaseView {
    private View mLayoutView;

    /**
     * 初始化布局
     */
    public abstract int getLayoutRes();

    /**
     * 初始化视图
     */
    public abstract void initView();

    @Override
    public Context getContext() {
        return getBaseActivity().getContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mLayoutView != null) {
            ViewGroup parent = (ViewGroup) mLayoutView.getParent();
            if (parent != null) {
                parent.removeView(mLayoutView);
            }
        } else {
            mLayoutView = getCreateView(inflater, container);
            ButterKnife.bind(this, mLayoutView);
            initView();     //初始化布局
        }

        return mLayoutView;
    }

    /**
     * 获取Fragment布局文件的View
     *
     * @param inflater
     * @param container
     * @return
     */
    private View getCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    /**
     * 获取当前Fragment状态
     *
     * @return true为正常 false为未加载或正在删除
     */
    private boolean getStatus() {
        return (isAdded() && !isRemoving());
    }

    /**
     * 获取Activity
     *
     * @return
     */
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void showProgress(boolean flag, String message) {
        if (getStatus()) {
            getBaseActivity().showProgress(flag, message);
        }
    }

    @Override
    public void showProgress(String message) {
        showProgress(true, message);
    }

    @Override
    public void showProgress(int strRes) {
        showProgress(getString(strRes));
    }

    @Override
    public void hideProgress() {
        if (getStatus()) {
            getBaseActivity().hideProgress();
        }
    }

    @Override
    public void setProgressCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        getBaseActivity().setProgressCancelListener(onCancelListener);
    }

    @Override
    public void showToast(String msg) {
        if (getStatus()) {
            getBaseActivity().showToast(msg);
        }
    }

    @Override
    public void showToast(int res) {
        showToast(getString(res));
    }

    @Override
    public void openPage(Class clazz) {
        getBaseActivity().openPage(clazz);
    }

    @Override
    public void openPage(Intent intent) {
        getBaseActivity().openPage(intent);
    }

    @Override
    public void openPageForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        getBaseActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void close() {
        getBaseActivity().close();
    }

    @Override
    public <T> void startAsync(Observable<T> observable, Observer<T> observer) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<T>bind())
                .subscribe(observer);
    }

    @Override
    public void clearUser() {
        getBaseActivity().clearUser();
    }

    @Override
    public <T> LifecycleTransformer<T> bind() {
        return bindToLifecycle();
    }

    @Override
    public void startDelayAsync(long delay, TimeUnit unit, Observer<Long> observer) {
        startAsync(Observable.timer(delay, unit), observer);
    }

    @Override
    public void startIntervalAsync(long period, TimeUnit unit, Observer<Long> observer) {
        startAsync(Observable.interval(period, unit), observer);
    }
}

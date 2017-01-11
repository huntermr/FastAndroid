package com.hunter.fastandroid.base;

import android.content.DialogInterface;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * View层接口基类
 *
 * @author Hunter
 */
public interface IBaseView {

    /**
     * 显示进度条
     *
     * @param flag    是否可取消
     * @param message 要显示的信息
     */
    void showProgress(boolean flag, String message);

    /**
     * 显示可取消的进度条
     *
     * @param message 要显示的信息
     */
    void showProgress(String message);

    /**
     * 设置取消进度条监听
     *
     * @param onCancelListener
     */
    void setProgressCancelListener(DialogInterface.OnCancelListener onCancelListener);

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 根据字符串弹出toast
     *
     * @param msg 提示内容
     */
    void showToast(String msg);

    /**
     * 关联RxLifecycle
     *
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bind();
}

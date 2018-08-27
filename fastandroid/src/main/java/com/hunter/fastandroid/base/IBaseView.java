package com.hunter.fastandroid.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;
import io.reactivex.Observer;


/**
 * View层接口基类
 *
 * @author Hunter
 */
public interface IBaseView {

    /**
     * 获取上下文对象
     * @return
     */
    Context getContext();

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
     * 显示可取消的进度条
     * @param strRes
     */
    void showProgress(int strRes);

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 设置取消进度条监听
     *
     * @param onCancelListener
     */
    void setProgressCancelListener(DialogInterface.OnCancelListener onCancelListener);

    /**
     * 根据字符串弹出toast
     *
     * @param msg 提示内容
     */
    void showToast(String msg);

    /**
     * 根据字符串资源弹出toast
     * @param res
     */
    void showToast(int res);

    /**
     * 打开指定页面
     * @param clazz
     */
    void openPage(Class clazz);

    /**
     * 根据意图打开页面
     * @param intent
     */
    void openPage(Intent intent);

    /**
     * 带请求的打开页面
     * @param intent
     * @param requestCode
     */
    void openPageForResult(Intent intent, int requestCode);

    /**
     * 关闭当前页面
     */
    void close();

    /**
     * 执行一个基于rxjava的异步任务
     * @param observable
     */
    <T> void startAsync(Observable<T> observable, Observer<T> observer);

    /**
     * 关联RxLifecycle
     *
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bind();
}

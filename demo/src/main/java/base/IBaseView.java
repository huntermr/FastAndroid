package demo.base;

import android.content.Context;
import android.content.Intent;

import com.trello.rxlifecycle.LifecycleTransformer;

import rx.Observable;
import rx.Subscriber;

/**
 * View层接口基类
 * <p/>
 * 为统一风格,便于区分普通方法
 * 所有继承该接口的方法前缀增加'ui'
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

    void showProgress(int strRes);

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
     * @param subscriber
     */
    <T> void startAsync(Observable<T> observable, Subscriber<T> subscriber);

    /**
     * 清除当前登录用户,并跳转到登录界面
     */
    void clearUser();

    /**
     * 关联RxLifecycle
     *
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bind();
}

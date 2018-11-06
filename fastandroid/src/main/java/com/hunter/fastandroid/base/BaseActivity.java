package com.hunter.fastandroid.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hunter.fastandroid.R;
import com.hunter.fastandroid.app.ActivityManager;
import com.hunter.fastandroid.app.UserManager;
import com.hunter.fastandroid.ui.activity.LoginActivity;
import com.hunter.fastandroid.ui.activity.MainActivity;
import com.hunter.fastandroid.ui.widget.CustomProgress;
import com.hunter.fastandroid.utils.CommonUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *  Activity基类
 *
 *  @author Hunter
 */
public abstract class BaseActivity extends RxAppCompatActivity implements IBaseView {
    private Toast toast;
    private ProgressDialog mProgressDialog;

    /**
     * 填充一个后退按钮
     *
     * @return
     */
    public View fillBackButton() {
        return fillBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }

    /**
     * 填充一个后退按钮
     *
     * @return
     */
    public View fillBackButton(View.OnClickListener onClickListener) {
        View backButton = View.inflate(this, R.layout.layout_back, null);

        backButton.setOnClickListener(onClickListener);

        return backButton;
    }

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化控制中心
     */
    public abstract void initPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResId());
        initStatusBar();
        // 初始化View注入
        ButterKnife.bind(this);
        initPresenter();
        initView();

        ActivityManager.getInstance().pushActivity(this);
    }

    public void goHome() {
        CommonUtils.hideSoftInput(this);
        finish();
        openPage(MainActivity.class);
        ActivityManager.getInstance().finishAllActivityExceptOne(MainActivity.class);
    }

    public void backHome() {
        finish();
        ActivityManager.getInstance().finishAllActivityExceptOne(MainActivity.class);
    }

    /**
     * 是否启用沉浸式
     *
     * @return
     */
    public boolean isImmersion() {
        return true;
    }

    /**
     * 初始化沉侵式状态栏
     */
    public void initStatusBar() {
        if (isImmersion()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window window = getWindow();
                // Translucent status bar
                window.setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                ViewGroup contentLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
                View parentView = contentLayout.getChildAt(0);
                if (parentView != null) {
                    parentView.setFitsSystemWindows(true);
                }
            }

            initStatsBarColor();
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void initStatsBarColor() {
        initStatsBarColor(0);
    }

    public void initStatsBarColor(int color) {
        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
        systemBarTintManager.setStatusBarTintEnabled(true);
        if (color == 0) {
            systemBarTintManager.setStatusBarTintResource(R.color.colorPrimary);
        } else {
            systemBarTintManager.setStatusBarTintResource(color);
        }
    }

    protected abstract int getContentResId();

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finish() {
        super.finish();
        ActivityManager.getInstance().popActivity(this);
    }

    @Override
    public void showProgress(final boolean flag, final String message) {
        showLoading(flag, message);
    }

    private void showLoading(boolean flag, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgress(this, R.style.CustomDialog);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.setCancelable(flag);
        mProgressDialog.setMessage(message);

        if(!mProgressDialog.isShowing()){
            mProgressDialog.show();
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
        if (mProgressDialog == null)
            return;

        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void setProgressCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (mProgressDialog != null) {
            mProgressDialog.setOnCancelListener(onCancelListener);
        }
    }

    @Override
    public void showToast(String msg) {
        if (!isFinishing()) {
            if (toast == null) {
                toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }

            toast.show();
        }
    }

    @Override
    public void showToast(int res) {
        showToast(getString(res));
    }

    @Override
    public void openPage(Class clazz) {
        openPage(new Intent(this, clazz));
    }

    public void finishAndOpenPage(Class clazz){
        finish();
        openPage(clazz);
    }

    @Override
    public void openPage(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void openPageForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void close() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 开始执行一个延时任务
     * @param delay
     * @param unit
     * @param observer
     */
    @Override
    public void startDelayAsync(long delay, TimeUnit unit, Observer<Long> observer) {
        startAsync(Observable.timer(delay, unit), observer);
    }

    /**
     * 开始执行一个定时任务
     * @param period
     * @param unit
     * @param observer
     */
    @Override
    public void startIntervalAsync(long period, TimeUnit unit, Observer<Long> observer) {
        startAsync(Observable.interval(period, unit), observer);
    }

    /**
     * 开始执行一个异步任务
     *
     * @param observable
     * @param observer
     * @param <T>
     */
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
        UserManager.getInstance().clearUserInfo();
        openPage(LoginActivity.class);
        ActivityManager.getInstance().finishAllActivityExceptOne(LoginActivity.class);
    }

    @Override
    public <T> LifecycleTransformer<T> bind() {
        return bindToLifecycle();
    }
}

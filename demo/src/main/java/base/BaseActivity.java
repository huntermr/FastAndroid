package demo.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import demo.app.ActivityManager;
import demo.app.UserManager;
import demo.ui.activity.MainActivity;
import demo.ui.activity.user.LoginActivity;
import demo.ui.widget.CustomProgress;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Activity基类
 *
 * @author Hunter
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

    public void goHome(){
        openPage(MainActivity.class);
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
            systemBarTintManager.setStatusBarTintResource(R.color.white);
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

        mProgressDialog.show();
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
     * 开始执行一个异步任务
     *
     * @param observable
     * @param subscriber
     * @param <T>
     */
    @Override
    public <T> void startAsync(Observable<T> observable, Subscriber<T> subscriber) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<T>bind())
                .subscribe(subscriber);
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

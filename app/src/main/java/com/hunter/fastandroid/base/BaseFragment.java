package com.hunter.fastandroid.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.fastandroid.ui.custom.HeaderLayout;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements IBaseView {
    private BaseActivity mActivity;
    private View layoutView;

    @Override
    public boolean canGoBack() {
        return false;
    }

    @Override
    public void goBack() {
    }

    /**
     * 初始化布局
     */
    public abstract int getLayoutRes();

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 初始化标题栏
     */
    public abstract void initTitleBar();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutView = getCreateView(inflater, container);
        ButterKnife.inject(this, layoutView);
        if (getTitleBar() != null) {
            initTitleBar();
        }
        initView();
        return layoutView;
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
        if (mActivity == null) {
            mActivity = (BaseActivity) getActivity();
        }
        return mActivity;
    }

    /**
     * 初始化标题栏
     */
    public HeaderLayout getTitleBar() {
        return getBaseActivity().getTitleBar();
    }

    @Override
    public void showProgress(boolean flag, String message) {
        if (getStatus()) {
            BaseActivity activity = getBaseActivity();
            if (activity != null) {
                activity.showProgress(flag, message);
            }
        }
    }

    @Override
    public void showProgress(String message) {
        showProgress(true, message);
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void showProgress(boolean flag) {
        showProgress(flag, "");
    }

    @Override
    public void hideProgress() {
        if (getStatus()) {
            BaseActivity activity = getBaseActivity();
            if (activity != null) {
                activity.hideProgress();
            }
        }
    }

    @Override
    public void showToast(int resId) {
        if (getStatus()) {
            BaseActivity activity = getBaseActivity();
            if (activity != null) {
                activity.showToast(resId);
            }
        }
    }

    @Override
    public void showToast(String msg) {
        if (getStatus()) {
            BaseActivity activity = getBaseActivity();
            if (activity != null) {
                activity.showToast(msg);
            }
        }
    }

    @Override
    public void showNetError() {
        if (getStatus()) {
            BaseActivity activity = getBaseActivity();
            if (activity != null) {
                activity.showNetError();
            }
        }
    }

    @Override
    public void showParseError() {
        if (getStatus()) {
            BaseActivity activity = getBaseActivity();
            if (activity != null) {
                activity.showParseError();
            }
        }
    }

    @Override
    public void close() {
    }
}

package demo.rx;


import com.orhanobut.logger.Logger;

import demo.base.IBaseView;
import demo.exception.CommonException;
import rx.Subscriber;

/**
 * RxJava 自定义Subscriber
 *
 * @param <T>
 * @author Hunter
 */
public abstract class CommonSubscriber<T> extends Subscriber<T> {
    private IBaseView mBaseView;
    // 是否显示加载进度条
    private boolean mIsShowLoading = true;

    public CommonSubscriber(IBaseView baseView) {
        mBaseView = baseView;
    }

    public CommonSubscriber(IBaseView baseView, boolean isShowLoading) {
        mBaseView = baseView;
        mIsShowLoading = isShowLoading;
    }

    @Override
    public void onStart() {
        if (mIsShowLoading) mBaseView.showProgress("加载中···");
    }

    @Override
    public void onCompleted() {
        mBaseView.hideProgress();
    }

    @Override
    public void onError(Throwable e) {
        mBaseView.hideProgress();

        if (e instanceof CommonException) {
            mBaseView.showToast(e.getMessage());
        }else{
            Logger.e(e.getMessage());
            mBaseView.showToast("系统异常,请重试");
        }
    }
}

package demo.rx;


import com.orhanobut.logger.Logger;

import cn.tbl.android.R;
import demo.base.IBaseView;
import demo.exception.ApiException;
import demo.vo.JsonResponse;
import rx.Subscriber;

/**
 * RxJava 自定义Subscriber
 *
 * @param <T>
 * @author Hunter
 */
public abstract class ResponseSubscriber<T> extends Subscriber<T> {
    private static final String TAG = "ResponseSubscriber";
    private IBaseView mBaseView;
    // 是否显示加载进度条
    private boolean mIsShowLoading = true;

    public ResponseSubscriber(IBaseView baseView) {
        mBaseView = baseView;
    }

    public ResponseSubscriber(IBaseView baseView, boolean isShowLoading) {
        mBaseView = baseView;
        mIsShowLoading = isShowLoading;
    }

    @Override
    public void onStart() {
        if (mIsShowLoading) mBaseView.showProgress(R.string.loading);
    }

    @Override
    public void onCompleted() {
        mBaseView.hideProgress();
    }

    @Override
    public void onError(Throwable e) {
        mBaseView.hideProgress();
        Logger.e(e.getMessage());
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;

            JsonResponse jsonResponse = apiException.getJsonResponse();

            if(jsonResponse.isTokenValid()){
                mBaseView.showToast(R.string.user_valid);
                mBaseView.clearUser();
            }else{
                mBaseView.showToast(jsonResponse.getMessage());
            }

        }
    }
}

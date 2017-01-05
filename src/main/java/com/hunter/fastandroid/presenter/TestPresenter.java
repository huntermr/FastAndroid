package com.hunter.fastandroid.presenter;

import com.hunter.fastandroid.base.BasePresenter;
import com.hunter.fastandroid.vo.JsonResponse;
import com.hunter.fastandroid.rx.JsonResponseFunc;
import com.hunter.fastandroid.rx.ResponseSubscriber;
import com.hunter.fastandroid.service.TestService;
import com.hunter.fastandroid.ui.interfaces.ITestView;

import rx.Observable;

/**
 * Created by Administrator on 2017/1/4.
 */
public class TestPresenter extends BasePresenter {
    TestService service;

    @Override
    protected void initService() {
        service = getService(TestService.class);
    }

    public void test(final ITestView testView) {
        Observable<JsonResponse<String>> observable = service.test();

        Observable<String> map = observable.map(new JsonResponseFunc<String>());

        subscribe(map, new ResponseSubscriber<String>(testView) {
            @Override
            public void onNext(String response) {
                testView.showToast(response);
            }
        });
    }
}

package com.hunter.fastandroid.presenter;

import com.hunter.fastandroid.base.BasePresenter;
import com.hunter.fastandroid.rx.ResponseObserver;
import com.hunter.fastandroid.service.TestService;
import com.hunter.fastandroid.ui.interfaces.ITestView;
import com.hunter.fastandroid.vo.DoubanResponse;


/**
 * Created by Administrator on 2017/1/4.
 */
public class TestPresenter extends BasePresenter {
    TestService service;

    @Override
    protected void initService() {
        service = getService(TestService.class);
    }

    public void test(String keyword, final ITestView testView) {

        subscribe(testView, service.test(keyword), new ResponseObserver<DoubanResponse>(testView) {
            @Override
            public void onNext(DoubanResponse response) {
                testView.showData(response.getBooks());
            }
        });
    }

}

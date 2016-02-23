package com.hunter.fastandroid.presenter.impl;

import com.hunter.fastandroid.base.BasePresenter;
import com.hunter.fastandroid.model.impl.TestModelImpl;
import com.hunter.fastandroid.model.interfaces.ITestModel;
import com.hunter.fastandroid.net.StringTransactionListener;
import com.hunter.fastandroid.presenter.interfaces.ITestPresenter;
import com.hunter.fastandroid.ui.view.interfaces.ITestView;
import com.hunter.fastandroid.utils.CommonUtils;
import com.hunter.fastandroid.vo.request.QueryParameter;
import com.hunter.fastandroid.vo.response.QueryResult;

/**
 * Created by Administrator on 2016/2/23.
 */
public class TestPresenterImpl extends BasePresenter implements ITestPresenter {
    @Override
    public void attributionToInquiries(final ITestView testView, QueryParameter queryParameter) {
        ITestModel testModel = new TestModelImpl(testView.getContext());
        testModel.attributionToInquiries(queryParameter, new StringTransactionListener() {
            @Override
            public void onSuccess(String response) {
                QueryResult queryResult = CommonUtils.getGson().fromJson(response, QueryResult.class);
                if (queryResult.resultcode == 200) {
                    testView.queryResult(queryResult);
                } else {
                    testView.showToast(queryResult.reason);
                }
            }

            @Override
            public void onFailure(int errorCode) {
                super.onFailure(errorCode);
            }
        });
    }
}

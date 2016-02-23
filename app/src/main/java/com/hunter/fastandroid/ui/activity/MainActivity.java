package com.hunter.fastandroid.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hunter.fastandroid.R;
import com.hunter.fastandroid.base.BaseActivity;
import com.hunter.fastandroid.presenter.impl.LoginPresenterImpl;
import com.hunter.fastandroid.presenter.impl.TestPresenterImpl;
import com.hunter.fastandroid.presenter.interfaces.ILoginPresenter;
import com.hunter.fastandroid.presenter.interfaces.ITestPresenter;
import com.hunter.fastandroid.ui.custom.TitleBar;
import com.hunter.fastandroid.ui.view.interfaces.ILoginView;
import com.hunter.fastandroid.ui.view.interfaces.ITestView;
import com.hunter.fastandroid.vo.request.LoginRequest;
import com.hunter.fastandroid.vo.request.QueryParameter;
import com.hunter.fastandroid.vo.response.QueryResult;
import com.hunter.fastandroid.vo.response.ResultInfo;
import com.hunter.fastandroid.vo.response.UserInfo;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements ITestView {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.et_input)
    EditText etInput;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.tv_result)
    TextView tvResult;

    ITestPresenter testPresenter;

    @Override
    public void initContentView() {
        // 设置布局文件
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        titleBar.setTitle("测试页面");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryParameter queryParameter = new QueryParameter();
                queryParameter.phone = etInput.getText().toString();

                testPresenter.attributionToInquiries(MainActivity.this, queryParameter);
            }
        });
    }

    @Override
    public void initPresenter() {
        testPresenter = new TestPresenterImpl();
    }

    @Override
    public void queryResult(QueryResult result) {
        ResultInfo resultInfo = result.result;
        if (resultInfo != null) {
            tvResult.setText("您的手机归属地信息是:\n" + resultInfo.province + resultInfo.city + resultInfo.company + resultInfo.card);
        }
    }
}

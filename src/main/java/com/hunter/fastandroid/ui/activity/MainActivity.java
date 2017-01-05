package com.hunter.fastandroid.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hunter.fastandroid.R;
import com.hunter.fastandroid.base.BaseActivity;
import com.hunter.fastandroid.presenter.TestPresenter;
import com.hunter.fastandroid.ui.widget.TitleBar;
import com.hunter.fastandroid.ui.interfaces.ITestView;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements ITestView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_result)
    TextView tvResult;

    TestPresenter testPresenter;

    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        titleBar.setTitle("测试页面");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
    }

    private void send() {
        testPresenter.test(this);
    }

    @Override
    public void initPresenter() {

        testPresenter = new TestPresenter();
    }

    @Override
    public void update() {

    }
}

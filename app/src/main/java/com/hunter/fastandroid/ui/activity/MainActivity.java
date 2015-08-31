package com.hunter.fastandroid.ui.activity;

import android.widget.TextView;

import com.hunter.fastandroid.R;
import com.hunter.fastandroid.base.BaseActivity;
import com.hunter.fastandroid.ui.custom.TitleBar;

import butterknife.InjectView;

public class MainActivity extends BaseActivity {
    @InjectView(R.id.title_bar)
    TitleBar titleBar;
    @InjectView(R.id.tv_content)
    TextView tvContent;

    @Override
    public void initContentView() {
        // 设置布局文件
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        titleBar.setTitle("首页");
        tvContent.setText("Hello FastAndroid!");
    }

    @Override
    public void initPresenter() {

    }
}

package demo.ui.activity.user;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import demo.base.BaseActivity;
import demo.presenter.UserPresenter;
import demo.ui.interfaces.user.IFeedbackView;
import demo.ui.widget.TitleBar;
import demo.utils.DialogUtils;
import demo.vo.request.FeedbackVo;

/**
 * Created by Administrator on 2017/5/16.
 */

public class FeedbackActivity extends BaseActivity implements IFeedbackView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    UserPresenter userPresenter;
    private boolean isEdit;
    @NonNull
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setStateEdit();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void initView() {
        titleBar.setTitle(R.string.feedback);
        titleBar.setLeftView(fillBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        }));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();

                FeedbackVo request = new FeedbackVo();
                request.setTitle(title);
                request.setContent(content);
                userPresenter.feedback(FeedbackActivity.this, request);
            }
        });

        etTitle.addTextChangedListener(textWatcher);
        etContent.addTextChangedListener(textWatcher);
    }

    @Override
    public void initPresenter() {
        userPresenter = new UserPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_feedback;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            showConfirmDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showConfirmDialog() {
        if (isEdit) {
            DialogUtils.showConfirmDialog(this, getString(R.string.back_confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    close();
                }
            });
        } else {
            close();
        }
    }

    /**
     * 设置当前页码为已编辑状态
     */
    private void setStateEdit() {
        isEdit = true;
    }

    @Override
    public void uiFeedback() {
        showToast("提交成功");
        close();
    }


}

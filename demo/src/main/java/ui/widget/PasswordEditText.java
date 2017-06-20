package demo.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/25.
 */

public class PasswordEditText extends RelativeLayout {
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_eye)
    ImageView btnEye;

    private boolean isHide = true;

    public PasswordEditText(Context context) {
        super(context);
        init();
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View contentView = layoutInflater.inflate(R.layout.layout_password, null);
        ButterKnife.bind(this, contentView);

        btnEye.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHide) {
                    //设置EditText文本为可见的
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnEye.setImageResource(R.mipmap.login_icon_unsee);
                } else {
                    //设置EditText文本为隐藏的
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnEye.setImageResource(R.mipmap.login_icon_see);
                }
                isHide = !isHide;

                etPwd.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etPwd.getText();
                if (charSequence != null) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });

        setEdNoChinaese(etPwd);

        addView(contentView);
    }

    /**
     * 限制edittext 不能输入中文
     * @param editText
     */
    public static void setEdNoChinaese(final EditText editText){
        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txt = s.toString();
                //注意返回值是char数组
                char[] stringArr = txt.toCharArray();
                for (int i = 0; i < stringArr.length; i++) {
                    //转化为string
                    String value = new String(String.valueOf(stringArr[i]));
                    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                    Matcher m = p.matcher(value);
                    if (m.matches()) {
                        editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() - 1));
                        editText.setSelection(editText.getText().toString().length());
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editText.addTextChangedListener(textWatcher);
    }

    public void setEditHint(String text) {
        etPwd.setHint(text);
    }

    public String getInputPassword() {
        return etPwd.getText().toString();
    }
}

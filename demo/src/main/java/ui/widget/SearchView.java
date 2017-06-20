package demo.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tbl.android.R;
import demo.utils.CommonUtils;

/**
 * Created by Administrator on 2017/5/11.
 */

public class SearchView extends RelativeLayout {
    @BindView(R.id.et_keywork)
    EditText etKeyWord;
    @BindView(R.id.btn_clear)
    ImageView btnClear;

    private LayoutInflater layoutInflater;
    private OnSearchListener mOnSearchListener;

    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        layoutInflater = LayoutInflater.from(getContext());
        View search = layoutInflater.inflate(R.layout.search_view, null);
        ButterKnife.bind(this, search);

        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etKeyWord.getText().clear();
                if (mOnSearchListener != null) mOnSearchListener.onClear();
            }
        });

        etKeyWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    if (mOnSearchListener != null) {
                        mOnSearchListener.onTextChange(etKeyWord.getText().toString());
                    }
                } else {
                    if (mOnSearchListener != null) mOnSearchListener.onClear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etKeyWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    CommonUtils.closeKeybord(etKeyWord, getContext());
                    if (mOnSearchListener != null) {
                        String keyword = etKeyWord.getText().toString();
                        if(!TextUtils.isEmpty(keyword.trim()))mOnSearchListener.onSearch(keyword.trim());
                    }
                    return true;
                }

                return false;
            }
        });

        addView(search);
    }

    public void setInputHint(CharSequence hint) {
        etKeyWord.setHint(hint);
    }

    public void setInputContent(CharSequence content){
        etKeyWord.setText(content);
    }

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        mOnSearchListener = onSearchListener;
    }

    public interface OnSearchListener {
        void onClear();

        void onTextChange(String keyword);

        void onSearch(String keyword);
    }
}

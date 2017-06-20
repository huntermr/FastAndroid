package demo.ui.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;


/**
 * Created by Administrator on 2017/5/25.
 */

public class CustomProgress extends ProgressDialog {
    private AVLoadingIndicatorView loading;
    private TextView tvMessage;
    private CharSequence mMessage;

    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        setContentView(R.layout.progress);

        tvMessage = (TextView) findViewById(R.id.tv_message);
        if(mMessage != null){
            tvMessage.setText(mMessage);
        }

        loading = (AVLoadingIndicatorView) findViewById(R.id.avi);
        loading.smoothToShow();

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void setMessage(CharSequence message) {
        if(tvMessage != null){
            tvMessage.setText(message);
        }else{
            mMessage = message;
        }
    }

    @Override
    public void show() {
        super.show();
    }
}

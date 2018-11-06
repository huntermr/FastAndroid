package com.hunter.fastandroid.ui.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.hunter.fastandroid.R;


/**
 * Created by Administrator on 2017/5/5.
 */

public class CountDownButton extends AppCompatTextView {
    private String oldText = "";
    static final int MAX_COUNT_DOWN = 60000;
    static final int COUNT_DOWN_INTERVAL = 1000;

    private CountDownTimer countDownTimer;

    public CountDownButton(Context context) {
        super(context);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 开始进行倒计时
     */
    public void startCountDown(){
        oldText = getText().toString();
        setEnabled(false);
        countDownTimer = new CountDownTimer(MAX_COUNT_DOWN, COUNT_DOWN_INTERVAL){

            @Override
            public void onTick(long millisUntilFinished) {
                setText(getContext().getString(R.string.countdown, millisUntilFinished / COUNT_DOWN_INTERVAL));
            }

            @Override
            public void onFinish() {
                setText(oldText);
                setEnabled(true);
            }
        }.start();
    }

    /**
     * 停止倒计时
     */
    public void stopCountDown(){
        countDownTimer.cancel();
        countDownTimer.onFinish();
    }
}

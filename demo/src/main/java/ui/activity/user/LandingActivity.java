package demo.ui.activity.user;

import android.os.Build;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import demo.app.UserManager;
import demo.base.BaseActivity;
import demo.presenter.UserPresenter;
import demo.ui.activity.MainActivity;
import demo.ui.interfaces.user.ILandingView;
import demo.utils.ImageLoadUtils;
import demo.vo.response.store.Ads;

/**
 * Created by Administrator on 2017/5/5.
 */

public class LandingActivity extends BaseActivity implements ILandingView {
    static final int MAX_COUNT_DOWN = 5000;
    static final int COUNT_DOWN_INTERVAL = 1000;

    @BindView(R.id.iv_landing)
    ImageView ivLanding;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;

    CountDownTimer countDownTimer;
    UserPresenter userPresenter;

    @Override
    public void initView() {
        userPresenter.welcome(this);

        tvCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterHome();
            }
        });
    }

    /**
     * 开始倒计时
     */
    private void startCountDown() {
        countDownTimer = new CountDownTimer(MAX_COUNT_DOWN, COUNT_DOWN_INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText(getString(R.string.landing_txt, millisUntilFinished / COUNT_DOWN_INTERVAL));
            }

            @Override
            public void onFinish() {
                enterHome();
            }
        }.start();
    }

    /**
     * 进入首页(如果已经登录过,则进入首页,否则跳转到登录页面)
     */
    private void enterHome() {
        if(countDownTimer != null) countDownTimer.cancel();

        UserManager userManager = UserManager.getInstance();

        if (userManager.isLogin()) {
            openPage(MainActivity.class);
        } else {
            openPage(LoginActivity.class);
        }

        finish();
    }

    @Override
    public void initPresenter() {
        userPresenter = new UserPresenter();
    }

    @Override
    protected int getContentResId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        return R.layout.activity_landing;
    }

    @Override
    public boolean isImmersion() {
        return false;
    }

    @Override
    public void uiAds(List<Ads> ads) {
        if(ads != null && ads.size() > 0){
            Ads ad = ads.get(0);
            ImageLoadUtils.loadImage(this, ad.getImgUrl(), ivLanding);
            startCountDown();
        }
    }

    @Override
    public void failAd() {
        enterHome();
    }
}

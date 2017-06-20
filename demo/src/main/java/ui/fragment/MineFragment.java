package demo.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.tbl.android.R;
import demo.app.ActivityManager;
import demo.app.CacheDataManager;
import demo.app.Constants;
import demo.app.UserManager;
import demo.base.BaseFragment;
import demo.presenter.UserPresenter;
import demo.ui.activity.store.IntegralRecordActivity;
import demo.ui.activity.user.CollectionActivity;
import demo.ui.activity.user.FeedbackActivity;
import demo.ui.activity.user.LoginActivity;
import demo.ui.activity.user.UpdatePwdActivity;
import demo.ui.activity.user.UpdateUserInfoActivity;
import demo.ui.interfaces.user.ILogoffView;
import demo.ui.interfaces.user.ISignInfoView;
import demo.ui.interfaces.user.ISignView;
import demo.ui.interfaces.user.IUserInfoView;
import demo.ui.widget.pulltozoomview.PullToZoomScrollViewEx;
import demo.utils.DialogUtils;
import demo.utils.ImageLoadUtils;
import demo.utils.PixelUtils;
import demo.vo.response.user.SignIn;
import demo.vo.response.user.User;
import demo.vo.response.user.UserInfo;

/**
 * Created by Administrator on 2017/5/9.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener, IUserInfoView, ISignInfoView, ISignView, ILogoffView {

    @BindView(R.id.scrollviewex)
    PullToZoomScrollViewEx scrollViewEx;
    @BindView(R.id.tv_sign_success)
    TextView tvSignSuccess;
    @BindView(R.id.sign_success)
    RelativeLayout signSuccess;
    @BindView(R.id.sign)
    RelativeLayout sign;

    HeadViewHolder headViewHolder;
    ContentViewHolder contentViewHolder;

    UserPresenter userPresenter;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        userPresenter = new UserPresenter();

        int headWidth = PixelUtils.getWindowWidth();
        int headHeight = (int) (headWidth * 0.64f);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(headWidth, headHeight);

        LayoutInflater layoutInflater = LayoutInflater.from(getBaseActivity());
        View headView = layoutInflater.inflate(R.layout.layout_mine_head, null);
        headViewHolder = new HeadViewHolder(headView);
        scrollViewEx.setHeaderView(headView);

        View zoomView = layoutInflater.inflate(R.layout.layout_mine_zoom, null);
        scrollViewEx.setZoomView(zoomView);

        View contentView = layoutInflater.inflate(R.layout.layout_mine_content, null);
        contentViewHolder = new ContentViewHolder(contentView);
        scrollViewEx.setScrollContentView(contentView);

        scrollViewEx.setHeaderLayoutParams(layoutParams);

        headViewHolder.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPresenter.signin(MineFragment.this);
            }
        });

        headViewHolder.tvUpdateUserinfo.setOnClickListener(this);
        headViewHolder.tvIntegralRecord.setOnClickListener(this);
        contentViewHolder.myCollect.setOnClickListener(this);
        contentViewHolder.myStore.setOnClickListener(this);
        contentViewHolder.updatePwd.setOnClickListener(this);
        contentViewHolder.feedback.setOnClickListener(this);
        contentViewHolder.btnLogout.setOnClickListener(this);
        contentViewHolder.clearCache.setOnClickListener(this);

        getCacheSize();

        initUserInfo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoUpdate(UserInfo userInfo){
        uiUserInfo(userInfo);
    }

    /**
     * 获取缓存大小并显示
     */
    private void getCacheSize() {
        String totalCacheSize = "";
        try {
            totalCacheSize = CacheDataManager.getTotalCacheSize(getBaseActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        contentViewHolder.tvClearSize.setText(totalCacheSize);
    }

    private void initUserInfo() {
        UserManager userManager = UserManager.getInstance();
        User currentUser = userManager.getCurrentUser();

        headViewHolder.tvNickName.setText(currentUser.getName());
        ImageLoadUtils.loadImageDontAnim(getBaseActivity(), currentUser.getImgUrl(), R.mipmap.head_small, headViewHolder.ivAvatar);

        userPresenter.getUserInfo(this);
        userPresenter.getSignInfo(this);
    }

    /**
     * 显示签到提示框
     */
    private void showSignDialog() {
        if (sign.getVisibility() == View.INVISIBLE) {
            sign.setVisibility(View.VISIBLE);

            Animation animation = AnimationUtils.loadAnimation(getBaseActivity(), R.anim.sign_anim);
            signSuccess.startAnimation(animation);

            startParticleAnimations();

            sign.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideSignDialog();
                }
            }, 3000);
        }
    }

    /**
     * 开始播放粒子动画
     */
    private void startParticleAnimations() {
        new ParticleSystem(getBaseActivity(), 18, R.mipmap.user_diamond, 3000)
                .setScaleRange(0.5f, 2f)
                .setSpeedRange(0.0f, 0.2f)
                .setRotationSpeed(300)
                .oneShot(signSuccess, 6);

        new ParticleSystem(getBaseActivity(), 120, R.mipmap.star_pink, 3000)
                .setScaleRange(0.5f, 1.5f)
                .setSpeedRange(0.1f, 0.3f)
                .setRotationSpeed(300)
                .oneShot(signSuccess, 40);

        new ParticleSystem(getBaseActivity(), 30, R.mipmap.square, 3000)
                .setScaleRange(0.5f, 1.5f)
                .setSpeedRange(0.1f, 0.3f)
                .setRotationSpeed(300)
                .oneShot(signSuccess, 10);

        new ParticleSystem(getBaseActivity(), 30, R.mipmap.square_yellow, 3000)
                .setScaleRange(0.5f, 1.5f)
                .setSpeedRange(0.1f, 0.3f)
                .setRotationSpeed(300)
                .oneShot(signSuccess, 10);
    }

    /**
     * 隐藏签到提示框
     */
    private void hideSignDialog() {
        Animation animation = AnimationUtils.loadAnimation(getBaseActivity(), R.anim.sign_anim_hide);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sign.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        signSuccess.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_collect:
                CollectionActivity.actionActivity(getBaseActivity(), Constants.COLLECTION_PRODUCT);
                break;
            case R.id.my_store:
                CollectionActivity.actionActivity(getBaseActivity(), Constants.COLLECTION_STORE);
                break;
            case R.id.update_pwd:
                openPage(UpdatePwdActivity.class);
                break;
            case R.id.feedback:
                openPage(FeedbackActivity.class);
                break;
            case R.id.tv_update_userinfo:
                openPage(UpdateUserInfoActivity.class);
                break;
            case R.id.tv_integral_record:
                openPage(IntegralRecordActivity.class);
                break;
            case R.id.btn_logout:
                DialogUtils.showConfirmDialog(getBaseActivity(), getString(R.string.logout_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userPresenter.logoff(MineFragment.this);
                    }
                });
                break;
            case R.id.clear_cache:
                DialogUtils.showConfirmDialog(getBaseActivity(), getString(R.string.clear_cache), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CacheDataManager.clearAllCache(getBaseActivity());
                        getCacheSize();

                    }
                });
                break;
        }
    }

    @Override
    public void uiUserInfo(UserInfo userInfo) {
        if(userInfo == null) return;

        if(!TextUtils.isEmpty(userInfo.getName())){
            headViewHolder.tvNickName.setText(userInfo.getName());
        }

        if(!TextUtils.isEmpty(userInfo.getImgUrl())){
            ImageLoadUtils.loadImageDontAnim(getBaseActivity(), userInfo.getImgUrl(), R.mipmap.head_small, headViewHolder.ivAvatar);
        }

        if(!TextUtils.isEmpty(userInfo.getScore())){
            headViewHolder.tvIntegral.setText(userInfo.getScore());
        }
    }

    @Override
    public void uiSignInfo(SignIn signIn) {
        headViewHolder.btnSign.setEnabled(!signIn.getSigned());
        headViewHolder.btnSign.setImageResource(signIn.getSigned() ? R.mipmap.sign_sel : R.mipmap.sign_nor);
        headViewHolder.tvSignDay.setText(getString(R.string.sign_day, signIn.getDays()));
        headViewHolder.tvTmrSign.setText(getString(R.string.tmr_sign_day, signIn.getTmrScore()));
        tvSignSuccess.setText(getString(R.string.sign_success, signIn.getTodScore()));
    }

    @Override
    public void uiSign() {
        headViewHolder.btnSign.setEnabled(false);
        headViewHolder.btnSign.setImageResource(R.mipmap.sign_sel);
        showSignDialog();
    }

    @Override
    public void uiLogoff() {
        UserManager.getInstance().clearUserInfo();
        openPage(LoginActivity.class);
        ActivityManager.getInstance().finishAllActivityExceptOne(LoginActivity.class);
    }

    static class ContentViewHolder {
        @BindView(R.id.my_collect)
        RelativeLayout myCollect;
        @BindView(R.id.my_store)
        RelativeLayout myStore;
        @BindView(R.id.update_pwd)
        RelativeLayout updatePwd;
        @BindView(R.id.feedback)
        RelativeLayout feedback;
        @BindView(R.id.btn_logout)
        Button btnLogout;
        @BindView(R.id.clear_cache)
        RelativeLayout clearCache;
        @BindView(R.id.tv_clear_size)
        TextView tvClearSize;

        ContentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class HeadViewHolder {
        @BindView(R.id.mine_head)
        RelativeLayout mineHead;
        @BindView(R.id.btn_sign)
        ImageView btnSign;
        @BindView(R.id.avatar)
        ImageView ivAvatar;
        @BindView(R.id.tv_nick_name)
        TextView tvNickName;
        @BindView(R.id.tv_update_userinfo)
        TextView tvUpdateUserinfo;
        @BindView(R.id.tv_integral)
        TextView tvIntegral;
        @BindView(R.id.tv_sign_day)
        TextView tvSignDay;
        @BindView(R.id.tv_tmr_sign)
        TextView tvTmrSign;
        @BindView(R.id.tv_integral_record)
        TextView tvIntegralRecord;

        HeadViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

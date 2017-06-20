package demo.presenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import demo.base.BasePresenter;
import demo.rx.JsonResponseSubscriber;
import demo.rx.ResponseSubscriber;
import demo.service.CommonService;
import demo.service.UserService;
import demo.ui.interfaces.common.IAdsView;
import demo.ui.interfaces.user.IAddFavorView;
import demo.ui.interfaces.user.IDelFavorView;
import demo.ui.interfaces.user.IFavorProductView;
import demo.ui.interfaces.user.IFavorStoreView;
import demo.ui.interfaces.user.IFeedbackView;
import demo.ui.interfaces.user.ILandingView;
import demo.ui.interfaces.user.ILoginView;
import demo.ui.interfaces.user.ILogoffView;
import demo.ui.interfaces.user.IRegisterView;
import demo.ui.interfaces.user.IScoreFlowView;
import demo.ui.interfaces.user.ISendMsgView;
import demo.ui.interfaces.user.ISignInfoView;
import demo.ui.interfaces.user.ISignView;
import demo.ui.interfaces.user.IUpdatePwdView;
import demo.ui.interfaces.user.IUpdateUserInfoView;
import demo.ui.interfaces.user.IUserInfoView;
import demo.utils.CommonUtils;
import demo.vo.FavorStoreChange;
import demo.vo.JsonResponse;
import demo.vo.UserPwd;
import demo.vo.request.FavorRequest;
import demo.vo.request.FeedbackVo;
import demo.vo.request.GetFavorStoreRequest;
import demo.vo.request.LoginRequest;
import demo.vo.request.PageRequest;
import demo.vo.request.RegisterRequest;
import demo.vo.request.SendMsgRequest;
import demo.vo.request.UpdatePwdRequest;
import demo.vo.response.store.Ads;
import demo.vo.response.store.NewProduct;
import demo.vo.response.store.ScoreFlow;
import demo.vo.response.store.Store;
import demo.vo.response.user.SignIn;
import demo.vo.response.user.User;
import demo.vo.response.user.UserInfo;

/**
 * Created by Administrator on 2017/1/4.
 */
public class UserPresenter extends BasePresenter {
    CommonService commonService;
    UserService service;

    @Override
    protected void initService() {
        commonService = getService(CommonService.class);
        service = getService(UserService.class);
    }

    /**
     * 加载开机广告
     *
     * @param landingView
     */
    public void welcome(final ILandingView landingView) {
        subscribe(landingView, convertResponse(commonService.welcome()), new ResponseSubscriber<List<Ads>>(landingView, false) {
            @Override
            public void onNext(List<Ads> adsList) {
                landingView.uiAds(adsList);
            }

            @Override
            public void onError(Throwable e) {
                landingView.failAd();
                super.onError(e);
            }
        });
    }

    /**
     * 加载广告图
     *
     * @param adsView
     * @param storeId
     */
    public void getAds(final IAdsView adsView, String storeId) {
        subscribe(adsView, convertResponse(commonService.advertList(storeId)), new ResponseSubscriber<List<Ads>>(adsView, false) {
            @Override
            public void onNext(List<Ads> ads) {
                adsView.uiAds(ads);
            }
        });
    }

    /**
     * 取消收藏
     *
     * @param delFavorView
     * @param request
     */
    public void delFavor(final IDelFavorView delFavorView, FavorRequest request) {
        subscribe(delFavorView, service.delFavor(converParams(request)), new JsonResponseSubscriber<JsonResponse>(delFavorView) {
            @Override
            public void onSuccess(JsonResponse o) {
                delFavorView.uiDelFavor();
            }
        });
    }

    /**
     * 新增收藏
     *
     * @param addFavorView
     * @param request
     */
    public void addFavor(final IAddFavorView addFavorView, FavorRequest request) {
        subscribe(addFavorView, service.addFavor(converParams(request)), new JsonResponseSubscriber<JsonResponse>(addFavorView) {
            @Override
            public void onSuccess(JsonResponse o) {
                addFavorView.uiAddFavor();
            }
        });
    }

    public void favorChangeStore(IAddFavorView addFavorView, IDelFavorView delFavorView, boolean isFavor, String storeId){
        FavorStoreChange change = new FavorStoreChange();
        change.setFavor(isFavor);
        change.setStoreId(storeId);
        EventBus.getDefault().post(change);

        FavorRequest request = new FavorRequest();
        request.setRefId(storeId);
        request.setType(0);
        if(isFavor){
            addFavor(addFavorView, request);
        }else{
            delFavor(delFavorView, request);
        }
    }

    public void favorChangeProduct(IAddFavorView addFavorView, IDelFavorView delFavorView, boolean isFavor, String productId){
        FavorRequest request = new FavorRequest();
        request.setRefId(productId);
        request.setType(1);
        if(isFavor){
            addFavor(addFavorView, request);
        }else{
            delFavor(delFavorView, request);
        }
    }

    /**
     * 获取收藏的产品列表
     *
     * @param favorProductView
     */
    public void favorProduct(final IFavorProductView favorProductView) {
        subscribe(favorProductView, convertResponse(service.favorProduct()), new ResponseSubscriber<List<NewProduct>>(favorProductView) {
            @Override
            public void onNext(List<NewProduct> products) {
                favorProductView.uiFavorProduct(products);
            }
        });
    }

    /**
     * 获取收藏的店铺列表
     *
     * @param storeView
     */
    public void favorStore(final IFavorStoreView storeView, GetFavorStoreRequest request) {
        subscribe(storeView, convertResponse(service.favorStore(converParams(request))), new ResponseSubscriber<List<Store>>(storeView) {
            @Override
            public void onNext(List<Store> stores) {
                storeView.uiFavorStore(stores);
            }
        });
    }

    /**
     * 留言反馈
     *
     * @param feedbackView
     * @param request
     */
    public void feedback(final IFeedbackView feedbackView, FeedbackVo request) {

        if (isEmpty(feedbackView, request.getTitle(), R.string.feedback_title_empty) ||
                isEmpty(feedbackView, request.getContent(), R.string.feedback_content_empty))
            return;

        subscribe(feedbackView, service.feedback(converParams(request)), new JsonResponseSubscriber<JsonResponse>(feedbackView) {
            @Override
            public void onSuccess(JsonResponse response) {
                feedbackView.uiFeedback();
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param userInfoView
     */
    public void getUserInfo(final IUserInfoView userInfoView) {
        subscribe(userInfoView, convertResponse(service.getUserInfo()), new ResponseSubscriber<UserInfo>(userInfoView) {
            @Override
            public void onNext(UserInfo userInfo) {
                userInfoView.uiUserInfo(userInfo);
            }
        });
    }

    /**
     * 更新用户信息
     *
     * @param updateUserInfoView
     * @param request
     */
    public void updateUserInfo(final IUpdateUserInfoView updateUserInfoView, UserInfo request) {
        // 请求参数校验
        if (isEmpty(updateUserInfoView, request.getName(), R.string.userinfo_nickname_empty) ||
                isEmpty(updateUserInfoView, request.getAge(), R.string.userinfo_age_empty) ||
                isEmpty(updateUserInfoView, request.getCity(), R.string.userinfo_city_empty))
            return;


        subscribe(updateUserInfoView, service.updateUserInfo(converParams(request)), new JsonResponseSubscriber<JsonResponse>(updateUserInfoView) {
            @Override
            public void onSuccess(JsonResponse response) {
                updateUserInfoView.uiUpdateUserInfo();
            }
        });
    }

    /**
     * 更新密码/找回密码
     *
     * @param updatePwdView
     * @param request
     */
    public void updatePwd(final IUpdatePwdView updatePwdView, UpdatePwdRequest request) {
        // 请求参数校验
        if (isEmpty(updatePwdView, request.getMobile(), R.string.mobile_empty) ||
                !isMobile(updatePwdView, request.getMobile()) ||
                isEmpty(updatePwdView, request.getCaptcha(), R.string.captcha_empty) ||
                isEmpty(updatePwdView, request.getPassword(), R.string.password_empty))
            return;

        request.setPassword(CommonUtils.EncryptMD5(request.getPassword()));

        subscribe(updatePwdView, service.updatePwd(converParams(request)), new JsonResponseSubscriber<JsonResponse>(updatePwdView) {

            @Override
            public void onSuccess(JsonResponse response) {
                updatePwdView.uiUpdatePwd();
            }
        });
    }

    /**
     * 获取用户积分记录
     *
     * @param scoreFlowView
     */
    public void getScoreFlow(final IScoreFlowView scoreFlowView, PageRequest request) {
        subscribe(scoreFlowView, convertResponse(service.getScoreFlow(converParams(request))), new ResponseSubscriber<ScoreFlow>(scoreFlowView) {
            @Override
            public void onNext(ScoreFlow scoreFlow) {
                scoreFlowView.uiScoreFlow(scoreFlow);
            }
        });
    }

    /**
     * 获取用户签到信息
     *
     * @param signInfoView
     */
    public void getSignInfo(final ISignInfoView signInfoView) {
        subscribe(signInfoView, convertResponse(service.getSignInfo()), new ResponseSubscriber<SignIn>(signInfoView) {
            @Override
            public void onNext(SignIn signIn) {
                signInfoView.uiSignInfo(signIn);
            }
        });
    }

    /**
     * 用户签到
     *
     * @param signView
     */
    public void signin(final ISignView signView) {
        subscribe(signView, service.signin(), new JsonResponseSubscriber<JsonResponse>(signView) {
            @Override
            public void onSuccess(JsonResponse response) {
                signView.uiSign();
            }
        });
    }

    /**
     * 用户登录
     *
     * @param loginView
     * @param userPwd
     */
    public void login(final ILoginView loginView, UserPwd userPwd) {
        // 请求参数校验
        if (isEmpty(loginView, userPwd.getMobile(), R.string.mobile_empty) ||
                !isMobile(loginView, userPwd.getMobile()) ||
                isEmpty(loginView, userPwd.getPwd(), R.string.password_empty))
            return;

        LoginRequest request = new LoginRequest();
        request.setMobile(userPwd.getMobile());
        request.setPassword(CommonUtils.EncryptMD5(userPwd.getPwd()));

        subscribe(loginView, convertResponse(service.login(converParams(request))), new ResponseSubscriber<User>(loginView) {
            @Override
            public void onNext(User user) {
                loginView.uiLogin(user);
            }
        });
    }

    /**
     * 用户注册
     *
     * @param registerView
     * @param userPwd
     */
    public void register(final IRegisterView registerView, UserPwd userPwd) {
        // 请求参数校验
        if (isEmpty(registerView, userPwd.getMobile(), R.string.mobile_empty) ||
                !isMobile(registerView, userPwd.getMobile()) ||
                isEmpty(registerView, userPwd.getCaptcha(), R.string.captcha_empty) ||
                isEmpty(registerView, userPwd.getPwd(), R.string.password_empty))
            return;

        if(userPwd.getPwd().length() < 6){
            registerView.showToast(R.string.password_too_short);
            return;
        }

        RegisterRequest request = new RegisterRequest();
        request.setMobile(userPwd.getMobile());
        request.setCaptcha(userPwd.getCaptcha());
        request.setPassword(CommonUtils.EncryptMD5(userPwd.getPwd()));

        subscribe(registerView, service.register(converParams(request)), new JsonResponseSubscriber<JsonResponse>(registerView) {
            @Override
            public void onSuccess(JsonResponse response) {
                registerView.uiRegister();
            }
        });
    }

    /**
     * 用户注销
     *
     * @param logoffView
     */
    public void logoff(final ILogoffView logoffView) {
        subscribe(logoffView, service.logoff(), new JsonResponseSubscriber<JsonResponse>(logoffView) {
            @Override
            public void onSuccess(JsonResponse response) {
                logoffView.uiLogoff();
            }
        });
    }

    /**
     * 获取短信验证码
     *
     * @param sendMsgView
     * @param request
     */
    public void sendMsg(final ISendMsgView sendMsgView, SendMsgRequest request) {
        if(isEmpty(sendMsgView, request.getMobile(), R.string.mobile_empty) ||
                !isMobile(sendMsgView, request.getMobile()))
            return;

        subscribe(sendMsgView, service.sendMsg(converParams(request)), new JsonResponseSubscriber<JsonResponse>(sendMsgView) {
            @Override
            public void onSuccess(JsonResponse response) {
                sendMsgView.uiSendMsg();
            }
        });
    }


}

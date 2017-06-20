package demo.service;

import java.util.List;
import java.util.Map;

import demo.app.URLs;
import demo.vo.JsonResponse;
import demo.vo.response.store.NewProduct;
import demo.vo.response.store.ScoreFlow;
import demo.vo.response.store.Store;
import demo.vo.response.user.SignIn;
import demo.vo.response.user.User;
import demo.vo.response.user.UserInfo;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/1/4.
 */
public interface UserService {

    /**
     * 取消用户收藏项
     *
     * @param requestParams
     * @return
     */
    @DELETE(URLs.CUSTOMER + URLs.CUSTOMER_FAVOR)
    Observable<JsonResponse> delFavor(@QueryMap Map<String, String> requestParams);

    /**
     * 新增用户收藏项
     *
     * @param requestParams
     * @return
     */
    @FormUrlEncoded
    @POST(URLs.CUSTOMER + URLs.CUSTOMER_FAVOR)
    Observable<JsonResponse> addFavor(@FieldMap Map<String, String> requestParams);

    /**
     * 获取用户收藏的商品列表
     *
     * @return
     */
    @GET(URLs.CUSTOMER + URLs.CUSTOMER_FAVOR_PRODUCT)
    Observable<JsonResponse<List<NewProduct>>> favorProduct();

    /**
     * 获取用户收藏的店铺列表
     *
     * @return
     */
    @GET(URLs.CUSTOMER + URLs.CUSTOMER_FAVOR_STORE)
    Observable<JsonResponse<List<Store>>> favorStore(@QueryMap Map<String, String> requestParams);

    /**
     * 用户意见反馈
     *
     * @param requestParams
     * @return
     */
    @FormUrlEncoded
    @POST(URLs.CUSTOMER + URLs.CUSTOMER_FEEDBACK)
    Observable<JsonResponse> feedback(@FieldMap Map<String, String> requestParams);

    /**
     * 获取用户的个人信息
     *
     * @return
     */
    @GET(URLs.CUSTOMER + URLs.CUSTOMER_INFO)
    Observable<JsonResponse<UserInfo>> getUserInfo();

    /**
     * 更新用户的个人信息
     *
     * @return
     */
    @FormUrlEncoded
    @PUT(URLs.CUSTOMER + URLs.CUSTOMER_INFO)
    Observable<JsonResponse> updateUserInfo(@FieldMap Map<String, String> requestParams);

    /**
     * 更新用户的密码
     *
     * @param requestParams
     * @return
     */
    @FormUrlEncoded
    @PUT(URLs.CUSTOMER + URLs.CUSTOMER_PWD)
    Observable<JsonResponse> updatePwd(@FieldMap Map<String, String> requestParams);

    /**
     * 获取用户的积分记录
     *
     * @return
     */
    @GET(URLs.CUSTOMER + URLs.CUSTOMER_SCORE_FLOW)
    Observable<JsonResponse<ScoreFlow>> getScoreFlow(@QueryMap Map<String, String> requestParams);

    /**
     * 获取签到信息
     * @return
     */
    @GET(URLs.CUSTOMER + URLs.CUSTOMER_SIGNIN)
    Observable<JsonResponse<SignIn>> getSignInfo();

    /**
     * 用户签到
     *
     * @return
     */
    @POST(URLs.CUSTOMER + URLs.CUSTOMER_SIGNIN)
    Observable<JsonResponse> signin();

    /**
     * 用户登录
     *
     * @param requestParams
     * @return
     */
    @GET(URLs.LOGIN)
    Observable<JsonResponse<User>> login(@QueryMap Map<String, String> requestParams);

    /**
     * 用户注销
     *
     * @return
     */
    @PUT(URLs.LOGOFF)
    Observable<JsonResponse> logoff();

    /**
     * 用户注册
     *
     * @param requestParams
     * @return
     */
    @FormUrlEncoded
    @POST(URLs.REGISTER)
    Observable<JsonResponse> register(@FieldMap Map<String, String> requestParams);

    /**
     * 获取短信验证码
     *
     * @param requestParams
     * @return
     */
    @FormUrlEncoded
    @POST(URLs.MESSAGE + URLs.SEND_SMS)
    Observable<JsonResponse> sendMsg(@FieldMap Map<String, String> requestParams);
}

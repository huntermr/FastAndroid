package demo.app;

/**
 * URL路径处理类
 *
 * @author Hunter
 */
public class URLs {
    public static final String BASE_URL = "http://host:port/xxx/api/v1/";

    /**
     * 用户模块
     */
    public static final String CUSTOMER = "customer/";
    // 取消/新增用户收藏项
    public static final String CUSTOMER_FAVOR = "favor";
    // 收藏的新款推荐
    public static final String CUSTOMER_FAVOR_PRODUCT = "favor_product";
    // 收藏的店铺
    public static final String CUSTOMER_FAVOR_STORE = "favor_store";
    // 留言反馈
    public static final String CUSTOMER_FEEDBACK = "feedback";
    // 获取/更新用户信息
    public static final String CUSTOMER_INFO = "info";
    // 更新密码
    public static final String CUSTOMER_PWD = "pwd";
    // 签到记录
    public static final String CUSTOMER_SCORE_FLOW = "score_flow";
    // 用户签到
    public static final String CUSTOMER_SIGNIN = "signin";
    /**
     * 登录模块
     */
    // 开机广告
    public static final String WELCOME = "welcome";
    // 登录
    public static final String LOGIN = "login";
    // 注销
    public static final String LOGOFF = "logoff";
    // 注册
    public static final String REGISTER = "register";

    /**
     * 短信模块
     */
    public static final String MESSAGE = "message/";
    // 获取短信验证码
    public static final String SEND_SMS = "sms";

    /**
     * 广告模块
     */
    public static final String ADVERT = "advert/";
    // 获取平台或店铺的广告列表
    public static final String ADVERT_LIST = "list";
    /**
     * 店铺模块
     */
    public static final String STORE = "store/";
    // 获取店铺列表
    public static final String STORE_LIST = "list";
    // 获取店铺详细信息
    public static final String STORE_DETAIL = "detail";
    // 根据关键字搜索店铺
    public static final String SEARCH_STORE = "search";
    // 获取城市列表
    public static final String CITY_LIST = "cities";
}

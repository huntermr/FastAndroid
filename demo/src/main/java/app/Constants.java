package demo.app;

/**
 * 全局变量存储类
 *
 * @author Hunter
 */
public class Constants {

    // 数据库名称
    public static final String DATABASE_NAME = "xxx";

    public static final String BASE_STORE_ID = "1";

    public static final String TOKEN_KEY = "X-User-Token";
    public static final String UID_KEY = "X-User-ID";

    public static final int PAGE_SIZE = 20;
    public static final int PAGE_NUM = 1;

    public static final int HTTP_TIMEOUT_SECONDS = 60;

    public static final int MAP_ZOOM_LEVEL = 12;

    // 自动分页间隔时间
    public static final int AUTO_SCROLL_INTERVAL = 3000;
    // 店铺id
    public static final String STORE_ID = "store_id";

    // 城市名称
    public static final int SELECT_CITY_REQ_CODE = 0x01;
    public static final int SELECT_CITY_RES_CODE = 0x02;
    public static final String CITY_NAME = "city_name";
    public static final String CITY_CODE = "city_code";

    // 滑动变色的最大高度
    public static final int SCROLL = 150;

    // 定位间隔时间
    public static final int LOCATION_INTERVAL = 10000;

    // 收藏类型
    public static final String COLLECTION_TYPE = "collection_type";
    public static final int COLLECTION_PRODUCT = 1;
    public static final int COLLECTION_STORE = 2;

    // 获取相册图片或拍照
    public static final int IMAGE_REQUEST = 0x01;

    // 高德导航的开始和结束坐标key
    public static final String AMAP_NAV_START = "amap_nav_start";
    public static final String AMAP_NAV_END = "amap_nav_end";

    // 高德导航类型
    public static final String AMAP_NAV_TYPE = "amap_nav_type";
    /**驾车*/
    public static final int ROUTE_TYPE_DRIVE = 2;
    /**步行*/
    public static final int ROUTE_TYPE_WALK = 3;
    /**骑行*/
    public static final int ROUTE_TYPE_RIDE = 4;

}

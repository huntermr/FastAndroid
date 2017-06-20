package demo.service;

import java.util.List;
import java.util.Map;

import demo.app.URLs;
import demo.vo.JsonResponse;
import demo.vo.response.store.City;
import demo.vo.response.store.Store;
import demo.vo.response.store.StoreDetail;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface StoreService {

    /**
     * 按条件获取店铺列表
     *
     * @param requestParams
     * @return
     */
    @GET(URLs.STORE + URLs.STORE_LIST)
    Observable<JsonResponse<List<Store>>> storeList(@QueryMap Map<String, String> requestParams);

    /**
     * 获取店铺详情
     * @param storeId
     * @return
     */
    @GET(URLs.STORE + URLs.STORE_DETAIL)
    Observable<JsonResponse<StoreDetail>> storeDetail(@Query("storeId") String storeId);

    /**
     * 按条件获取店铺列表
     *
     * @param requestParams
     * @return
     */
    @GET(URLs.STORE + URLs.SEARCH_STORE)
    Observable<JsonResponse<List<Store>>> searchStore(@QueryMap Map<String, String> requestParams);

    /**
     * 获取城市列表
     * @return
     */
    @GET(URLs.STORE + URLs.CITY_LIST)
    Observable<JsonResponse<List<City>>> cities();

}

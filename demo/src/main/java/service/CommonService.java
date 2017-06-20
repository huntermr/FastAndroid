package demo.service;

import java.util.List;

import demo.app.URLs;
import demo.vo.JsonResponse;
import demo.vo.response.store.Ads;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface CommonService {
    /**
     * 获取广告图地址
     *
     * @return
     */
    @GET(URLs.ADVERT + URLs.ADVERT_LIST)
    Observable<JsonResponse<List<Ads>>> advertList(@Query("storeId") String storeId);

    /**
     * 开机广告
     *
     * @return
     */
    @GET(URLs.WELCOME)
    Observable<JsonResponse<List<Ads>>> welcome();
}

package com.hunter.fastandroid.service;

import com.hunter.fastandroid.app.URLs;
import com.hunter.fastandroid.vo.DoubanResponse;
import com.hunter.fastandroid.vo.JsonResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/1/4.
 */
public interface TestService {
    @GET(URLs.MODUEL_BOOK + URLs.SEARCH)
    Observable<DoubanResponse> test(@Query("q") String keyword);
}

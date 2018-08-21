package com.hunter.fastandroid.service;

import com.hunter.fastandroid.app.URLs;
import com.hunter.fastandroid.vo.JsonResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/1/4.
 */
public interface TestService {
    @POST(URLs.MODUEL_USER + URLs.login)
    Observable<JsonResponse<String>> test();

    @POST(URLs.MODUEL_USER + URLs.login)
    Flowable<JsonResponse<String>> test2();
}

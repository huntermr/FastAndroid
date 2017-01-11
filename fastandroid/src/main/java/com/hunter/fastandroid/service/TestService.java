package com.hunter.fastandroid.service;

import com.hunter.fastandroid.app.URLs;
import com.hunter.fastandroid.vo.JsonResponse;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/1/4.
 */
public interface TestService {
    @POST(URLs.MODUEL_USER + URLs.login)
    Observable<JsonResponse<String>> test();
}

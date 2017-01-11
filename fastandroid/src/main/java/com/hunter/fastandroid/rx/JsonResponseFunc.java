package com.hunter.fastandroid.rx;

import com.hunter.fastandroid.exception.ApiException;
import com.hunter.fastandroid.vo.JsonResponse;

import rx.functions.Func1;

/**
 * RxJava map转换
 *
 * @param <T>
 * @author Hunter
 */
public class JsonResponseFunc<T> implements Func1<JsonResponse<T>, T> {
    @Override
    public T call(JsonResponse<T> tJsonResponse) {
        if (tJsonResponse == null) return null;

        if (tJsonResponse.getCode() != 1000) {
            throw new ApiException(tJsonResponse);
        }

        return tJsonResponse.getData();
    }
}

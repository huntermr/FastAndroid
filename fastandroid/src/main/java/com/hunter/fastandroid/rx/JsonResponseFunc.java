package com.hunter.fastandroid.rx;

import com.hunter.fastandroid.exception.ApiException;
import com.hunter.fastandroid.vo.JsonResponse;

import io.reactivex.functions.Function;


/**
 * RxJava map转换
 *
 * @param <T>
 * @author Hunter
 */
public class JsonResponseFunc<T> implements Function<JsonResponse<T>, T> {
    @Override
    public T apply(JsonResponse<T> tJsonResponse) throws Exception {
        if (tJsonResponse == null) return null;

        if (tJsonResponse.getStatus() != 10000) {
            throw new ApiException(tJsonResponse);
        }

        return tJsonResponse.getData();
    }
}

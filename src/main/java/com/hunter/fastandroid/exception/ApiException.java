package com.hunter.fastandroid.exception;

import com.hunter.fastandroid.vo.JsonResponse;

/**
 * 自定义异常
 *
 * @author Hunter
 */
public class ApiException extends RuntimeException {
    private JsonResponse tJsonResponse;

    public ApiException(JsonResponse jsonResponse) {
        super(jsonResponse.getMsg());
    }

    public void setJsonResponse(JsonResponse jsonResponse) {
        tJsonResponse = jsonResponse;
    }

    public JsonResponse gettJsonResponse() {
        return tJsonResponse;
    }
}

package demo.exception;

import demo.vo.JsonResponse;

/**
 * 自定义异常
 *
 * @author Hunter
 */
public class ApiException extends RuntimeException {
    private JsonResponse tJsonResponse;

    public ApiException(JsonResponse jsonResponse) {
        tJsonResponse = jsonResponse;
    }

    @Override
    public String getMessage() {
        return tJsonResponse.getMessage();
    }

    public JsonResponse getJsonResponse(){
        return tJsonResponse;
    }

}

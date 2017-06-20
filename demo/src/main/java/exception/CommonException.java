package demo.exception;

/**
 * 自定义异常
 *
 * @author Hunter
 */
public class CommonException extends RuntimeException {
    private String mErrorMsg;

    public CommonException(String errorMsg) {
        mErrorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return mErrorMsg;
    }

}

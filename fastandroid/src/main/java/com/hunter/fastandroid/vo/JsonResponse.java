package com.hunter.fastandroid.vo;

/**
 * 服务器响应实体类
 *
 * @param <T>
 * @author Hunter
 */
public class JsonResponse<T> {
    public final static int SUCCESS = 10000;
    public final static int TOKEN_EXPIRE = 99997;


    private int status;
    private String message;
    private T data;


    /**
     * TOKEN是否过期
     * @return true 过期 flase 未过期
     */
    public boolean isTokenValid() {
        return TOKEN_EXPIRE == status;
    }

    public boolean isSuccess() {
        return SUCCESS == status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

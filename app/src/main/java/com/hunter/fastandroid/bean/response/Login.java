package com.hunter.fastandroid.bean.response;

/**
 * 登陆实体类(响应)
 */
public class Login {
    private String token;
    private User user;
    private long expires_in;

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken() {

        return token;
    }

    public User getUser() {
        return user;
    }

    public long getExpires_in() {
        return expires_in;
    }
}

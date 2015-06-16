package com.hunter.fastandroid.bean.request;

import com.hunter.fastandroid.base.BaseRequest;

/**
 * 用户注册请求实体类
 */
public class RegisterUser extends BaseRequest {
    private String username;
    private String email;
    private String password;

    public RegisterUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

package demo.vo.request;

import demo.base.BaseRequestParams;

/**
 * Created by Administrator on 2017/5/4.
 */

public class LoginRequest extends BaseRequestParams {
    private String mobile;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

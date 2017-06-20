package demo.vo.request;

import demo.base.BaseRequestParams;

/**
 * Created by Administrator on 2017/5/26.
 */

public class UpdatePwdRequest extends BaseRequestParams {
    private String mobile;
    private String captcha;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

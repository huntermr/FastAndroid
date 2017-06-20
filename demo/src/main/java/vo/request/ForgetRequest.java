package demo.vo.request;

import demo.base.BaseRequestParams;

/**
 * Created by Administrator on 2017/5/12.
 */

public class ForgetRequest extends BaseRequestParams {
    private String mobile;
    private String verifCode;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifCode() {
        return verifCode;
    }

    public void setVerifCode(String verifCode) {
        this.verifCode = verifCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

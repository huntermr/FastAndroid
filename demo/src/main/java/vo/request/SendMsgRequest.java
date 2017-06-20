package demo.vo.request;

import demo.base.BaseRequestParams;

/**
 * Created by Administrator on 2017/6/7.
 */

public class SendMsgRequest extends BaseRequestParams {
    private String mobile;
    private int type; // 0 注册 1 更新密码

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

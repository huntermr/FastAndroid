package demo.vo.request;

import demo.base.BaseRequestParams;

/**
 * Created by Administrator on 2017/5/26.
 */

public class FavorRequest extends BaseRequestParams {
    private long customerId;
    private String refId;
    private int type; // 0 店铺 1 商品

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

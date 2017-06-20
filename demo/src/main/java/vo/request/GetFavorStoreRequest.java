package demo.vo.request;

import demo.base.BaseRequestParams;

/**
 * Created by Administrator on 2017/6/9.
 */

public class GetFavorStoreRequest extends BaseRequestParams {
    private double customerLng;
    private double customerLat;

    public double getCustomerLng() {
        return customerLng;
    }

    public void setCustomerLng(double customerLng) {
        this.customerLng = customerLng;
    }

    public double getCustomerLat() {
        return customerLat;
    }

    public void setCustomerLat(double customerLat) {
        this.customerLat = customerLat;
    }
}

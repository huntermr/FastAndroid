package demo.vo.request;

import demo.base.BaseRequestParams;

/**
 * Created by Administrator on 2017/6/9.
 */

public class SearchStoreRequest extends BaseRequestParams {
    private double customerLng;
    private double customerLat;
    private String keyword;

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}

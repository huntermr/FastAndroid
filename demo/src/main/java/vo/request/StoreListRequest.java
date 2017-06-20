package demo.vo.request;

import demo.base.BaseRequestParams;

/**
 * Created by Administrator on 2017/6/7.
 */

public class StoreListRequest extends BaseRequestParams {
    private String distance;
    private String cityCode;
    private double customerLng;
    private double customerLat;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

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

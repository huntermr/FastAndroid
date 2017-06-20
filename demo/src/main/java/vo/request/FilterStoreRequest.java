package demo.vo.request;

import demo.base.BaseRequestParams;

/**
 * Created by Administrator on 2017/5/26.
 */

public class FilterStoreRequest extends BaseRequestParams {
    private String distance;
    private String longitude;
    private String latitude;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}

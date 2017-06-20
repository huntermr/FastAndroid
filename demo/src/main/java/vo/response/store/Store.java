package demo.vo.response.store;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;

import demo.app.LocationManager;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Store {
    private String storeId;
    private String storeName;
    private String address;
    private boolean favor;
    private double latitude;
    private double longitude;
    private String telephone;
    private String imgUrl;
    private String distance;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isFavor() {
        return favor;
    }

    public void setFavor(boolean favor) {
        this.favor = favor;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDistance() {
        LatLng latLng = new LatLng(latitude, longitude);
        LatLng currentLatLng = LocationManager.getInstance().getLatLng();
        return String.valueOf(AMapUtils.calculateLineDistance(latLng, currentLatLng));
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}

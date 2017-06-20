package demo.vo.response.store;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class StoreDetail {
    private List<SpecialOffer> activities;
    private List<NewProduct> products;
    private String address;
    private String city;
    private String county;
    private String ctry;
    private boolean favor;
    private String province;
    private String storeId;
    private String storeName;
    private String telephone;
    private double latitude;
    private double longitude;

    public List<SpecialOffer> getActivities() {
        return activities;
    }

    public void setActivities(List<SpecialOffer> activities) {
        this.activities = activities;
    }

    public List<NewProduct> getProducts() {
        return products;
    }

    public void setProducts(List<NewProduct> products) {
        this.products = products;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCtry() {
        return ctry;
    }

    public void setCtry(String ctry) {
        this.ctry = ctry;
    }

    public boolean isFavor() {
        return favor;
    }

    public void setFavor(boolean favor) {
        this.favor = favor;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
}

package demo.vo.response.store;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import demo.utils.CommonUtils;

/**
 * Created by Administrator on 2017/5/10.
 */
@Entity
public class City {
    @Id(autoincrement = true)
    private Long id;
    private String cityName;//城市名称
    private String sortLetters;//显示数据拼音的首字母
    private String cityCode;
    private String adCode;
    @Generated(hash = 141410055)
    public City(Long id, String cityName, String sortLetters, String cityCode,
            String adCode) {
        this.id = id;
        this.cityName = cityName;
        this.sortLetters = sortLetters;
        this.cityCode = cityCode;
        this.adCode = adCode;
    }
    @Generated(hash = 750791287)
    public City() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCityName() {
        return CommonUtils.formatCityName(this.cityName);
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getSortLetters() {
        return this.sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
    public String getCityCode() {
        return this.cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getAdCode() {
        return this.adCode;
    }
    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }



}

package demo.vo.response.user;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Administrator on 2017/1/10.
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    private Long customerId;
    private String token = "";
    private String name = "";
    private String imgUrl = "";
    private String userSex = "";
    private String mobile = "";

    @Generated(hash = 1586640604)
    public User(Long id, Long customerId, String token, String name, String imgUrl,
            String userSex, String mobile) {
        this.id = id;
        this.customerId = customerId;
        this.token = token;
        this.name = name;
        this.imgUrl = imgUrl;
        this.userSex = userSex;
        this.mobile = mobile;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

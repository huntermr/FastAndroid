package com.hunter.fastandroid.bean.response;
/**
 * 用户实体类(响应)
 */
public class User {
    private String id;
    private String username;
    private String email;
    private boolean email_verified;
    private String mobile;
    private boolean mobile_verified;
    private long created_at;
    private long update_at;
    private String nickname;
    private String avatar;
    private int followers_count;
    private int friends_count;
    private int role;
    private int level;
    private int gender;
    private boolean anonymous;
    private String name;
    private long birthday;
    private String status;
    private String profile_cover;
    private boolean is_follow;

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProfile_cover(String profile_cover) {
        this.profile_cover = profile_cover;
    }

    public String getStatus() {

        return status;
    }

    public String getProfile_cover() {
        return profile_cover;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setMobile_verified(boolean mobile_verified) {
        this.mobile_verified = mobile_verified;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public void setUpdate_at(long update_at) {
        this.update_at = update_at;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public void setFriends_count(int friends_count) {
        this.friends_count = friends_count;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setIs_follow(boolean is_follow) {
        this.is_follow = is_follow;
    }

    public String getId() {

        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public String getMobile() {
        return mobile;
    }

    public boolean isMobile_verified() {
        return mobile_verified;
    }

    public long getCreated_at() {
        return created_at;
    }

    public long getUpdate_at() {
        return update_at;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public int getRole() {
        return role;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public boolean is_follow() {
        return is_follow;
    }
}

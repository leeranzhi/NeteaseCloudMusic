package com.demo.neteasecloudmusic.gson.user;

import java.io.Serializable;

public class Profile implements Serializable {
    public String nickname;
    public int userType;
    public String backgroundUrl;
    public int userId;
    public int gender;
    public String avatarUrl;
    public String signature;
    public int followeds;
    public int follows;
    public int playlistCount;
    public int eventCount;
    public long province;
    public long city;
    public long birthday;
    public int playlistBeSubscribedCount;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getFolloweds() {
        return followeds;
    }

    public void setFolloweds(int followeds) {
        this.followeds = followeds;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getPlaylistCount() {
        return playlistCount;
    }

    public void setPlaylistCount(int playlistCount) {
        this.playlistCount = playlistCount;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public long getProvince() {
        return province;
    }

    public void setProvince(long province) {
        this.province = province;
    }

    public long getCity() {
        return city;
    }

    public void setCity(long city) {
        this.city = city;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getPlaylistBeSubscribedCount() {
        return playlistBeSubscribedCount;
    }

    public void setPlaylistBeSubscribedCount(int playlistBeSubscribedCount) {
        this.playlistBeSubscribedCount = playlistBeSubscribedCount;
    }
}

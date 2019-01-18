package com.demo.neteasecloudmusic.gson.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{
    public int level;
    public int listenSongs;
    public Profile profile;
    @SerializedName("bindings")
    public List<Bindings> bindingsList;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getListenSongs() {
        return listenSongs;
    }

    public void setListenSongs(int listenSongs) {
        this.listenSongs = listenSongs;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Bindings> getBindingsList() {
        return bindingsList;
    }

    public void setBindingsList(List<Bindings> bindingsList) {
        this.bindingsList = bindingsList;
    }
}

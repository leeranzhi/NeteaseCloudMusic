package com.demo.neteasecloudmusic.gson.playList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserPlayList {
    public boolean more;
    public int code;
    @SerializedName("playlist")
    public List<PlayList> playLists;
}

package com.demo.neteasecloudmusic.gson.playList;

import com.google.gson.annotations.SerializedName;

public class PlayList {

    @SerializedName("coverImgUrl")
    public String ImageUrl;
    @SerializedName("name")
    public String playListName;
    public long playCount;
    public long trackCount;
    public Creator creator;

    public class Creator {
        public String nickName;
        public String avatarUrl;
        public String backgroundUrl;
        public long province;
        public long city;
        public long birthday;
    }

}

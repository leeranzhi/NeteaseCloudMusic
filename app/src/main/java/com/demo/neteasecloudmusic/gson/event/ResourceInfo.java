package com.demo.neteasecloudmusic.gson.event;

import com.google.gson.annotations.SerializedName;

public class ResourceInfo {

    public int eventType;
    @SerializedName("name")
    public String eventName;
}

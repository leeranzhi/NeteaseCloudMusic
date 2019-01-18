package com.demo.neteasecloudmusic.gson.event;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommentThread implements Serializable {

    public long resourceId;
    public String resourceTitle;
    @SerializedName("resourceInfo")
    public ResourceInfo resourceInfo;

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceTitle() {
        return resourceTitle;
    }

    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }
}

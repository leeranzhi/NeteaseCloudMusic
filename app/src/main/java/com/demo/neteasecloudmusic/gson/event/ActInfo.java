package com.demo.neteasecloudmusic.gson.event;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ActInfo implements Serializable {

    @SerializedName("json")
    public JSONMessage jsonMessage;
    public int likedCount;
    public int shareCount;
    public int commentCount;
    @SerializedName("commentThread")
    public CommentThread commentThread;

    public JSONMessage getJsonMessage() {
        return jsonMessage;
    }

    public void setJsonMessage(JSONMessage jsonMessage) {
        this.jsonMessage = jsonMessage;
    }

    public int getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public CommentThread getCommentThread() {
        return commentThread;
    }

    public void setCommentThread(CommentThread commentThread) {
        this.commentThread = commentThread;
    }
}

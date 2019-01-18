package com.demo.neteasecloudmusic.gson.event;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Events implements Serializable {

    public String actName;
    public long eventTime;
    @SerializedName("id")
    public long eventId;
    @SerializedName("info")
    public ActInfo actInfo;
    public List<Pictures> picturesList;
    @SerializedName("user")
    public UserMessage userMessage;

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public ActInfo getActInfo() {
        return actInfo;
    }

    public void setActInfo(ActInfo actInfo) {
        this.actInfo = actInfo;
    }

    public List<Pictures> getPicturesList() {
        return picturesList;
    }

    public void setPicturesList(List<Pictures> picturesList) {
        this.picturesList = picturesList;
    }

    public UserMessage getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(UserMessage userMessage) {
        this.userMessage = userMessage;
    }
}

package com.demo.neteasecloudmusic.gson.event;

import java.io.Serializable;

public class JSONMessage implements Serializable {

    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

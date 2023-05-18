package com.lisi4ka.utils;

import java.io.Serializable;

public class PackagedResponse implements Serializable {
    private String message;
    public PackagedResponse(String message, ResponseStatus status){
        this.message = message;
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    ResponseStatus status;
}


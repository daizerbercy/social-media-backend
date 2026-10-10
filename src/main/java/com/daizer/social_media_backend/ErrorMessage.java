package com.daizer.social_media_backend;
import java.util.Date;

public class ErrorMessage {
    private Date timestamp;
    private int statusCode;
    private String error;
    private String message;

    public ErrorMessage(Date timestamp, int statusCode, String error, String message) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getError(){
        return error;
    }

    public String getMessage() {
        return message;
    }
}

package com.example.whatsapp.model;

public class MessageModel {


    String uId;
    String message;
    Long timestamp;
    public MessageModel(String userId, String message, Long time) {
        this.uId = userId;
        this.message = message;
        this.timestamp = time;
    }

    public MessageModel(String userId, String message){
        this.uId = userId;
        this.message =message;
    }

    public MessageModel(){}


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

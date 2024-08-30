package com.example.whatsapp.model;

public class Users {

    String profile;
    String userName;
    String mail;
    String password;
    String userId;
    String lastMessage;
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Users(String profile, String userName, String mail, String password, String userId, String lastMessage) {
        this.profile = profile;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    public Users(){
        //default constructor
    }

    public Users( String userName, String mail, String password) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }

}

package com.example.newchatbot;

public class user {
    public String fullName;
    public String email;
    public String password;
    public String userType;
    public String userStatus;

    public user(String fullName, String email, String password, String userType, String userStatus) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.userStatus = userStatus;
    }

    public user(){

    }
}

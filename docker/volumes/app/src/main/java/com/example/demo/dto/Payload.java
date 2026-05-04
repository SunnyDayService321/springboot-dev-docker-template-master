package com.example.demo.dto;

import com.example.demo.models.UserEntity;

public class Payload {
    private String token;
    private UserEntity user;

    public Payload() {
    }

    public Payload(String token, UserEntity user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
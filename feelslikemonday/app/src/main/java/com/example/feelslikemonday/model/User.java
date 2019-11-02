package com.example.feelslikemonday.model;

import java.util.List;

//Assumes that usernames passwords and emails cannot be altered
public class User {
    //Used so it's clear in firebase as to what version of Users are being stored
    final int version = 0;
    private String username;
    //Todo: encrypt password?
    private String password;

    //private MoodHistory moodHistory;
    private List<MoodEvent> moodHistory;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
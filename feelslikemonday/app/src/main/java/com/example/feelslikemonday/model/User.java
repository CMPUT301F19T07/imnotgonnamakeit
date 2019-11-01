package com.example.feelslikemonday.model;

import java.util.ArrayList;
import java.util.List;

//Assumes that usernames passwords and emails cannot be altered
public class User {
    //Used so it's clear in firebase as to what version of Users are being stored
    final int version = 0;
    private String username;
    //Todo: encrypt password?
    private String password;
    private List<MoodEvent> moodHistory;

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.moodHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<MoodEvent> getMoodHistory(){return moodHistory;}


}
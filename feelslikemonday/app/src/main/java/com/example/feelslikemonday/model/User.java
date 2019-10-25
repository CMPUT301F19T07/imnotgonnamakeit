package com.example.feelslikemonday.model;

//Assumes that usernames passwords and emails cannot be altered
public class User {
    //Used so it's clear in firebase as to what version of Users are being stored
    final int version = 0;
    private String username;
    //Todo: encrypt password?
    private String password;
    private String email;
    private MoodHistory moodHistory;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
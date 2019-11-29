package com.example.feelslikemonday.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for storing information about the user
 */
//Assumes that usernames passwords and emails cannot be altered

public class User {

    final int version = 0;
    private String username;
    private String password;
    private List<MoodEvent> moodHistory;

    public User() {
    }

    /**
     * This is a class that keeps track of a user
     *
     * @param username This is a candidate username
     * @param password This is a user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.moodHistory = new ArrayList<>();
    }

    /**
     * This returns a username of user
     *
     * @return return a String username
     */
    public String getUsername() {
        return username;
    }

    /**
     * this returns the password of user
     *
     * @return return a String password
     */
    public String getPassword() {
        return password;
    }

    /**
     * this returns the list of mood events of user
     *
     * @return return the mood event list
     */
    public List<MoodEvent> getMoodHistory() {
        return moodHistory;
    }

    /**
     * this sets up the username for the user
     *
     * @param username This is a candidate username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * this sets up the password for the user
     *
     * @param password This is a user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * this sets up the mood event list to the user
     *
     * @param moodHistory This is a mood event list of the user
     */
    public void setMoodHistory(List<MoodEvent> moodHistory) {
        this.moodHistory = moodHistory;
    }
}
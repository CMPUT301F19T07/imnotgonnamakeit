package com.example.feelslikemonday.model;

import java.util.ArrayList;
import java.util.List;

//Assumes that usernames passwords and emails cannot be altered

/**
 * This is a class that store User's information
 */
public class User {

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

    /**
     * This return a username of user
     * @return
     *    String username
     */
    public String getUsername() { return username; }

    /**
     * this return the password of user
     * @return
     *   String password
     */
    public String getPassword() {return password; }

    /**
     * this return the information/detail of user's mood history
     * @return
     *    return the mood event list
     */
    public List<MoodEvent> getMoodHistory(){return moodHistory;}

    /**
     * this set up the username for the user
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * this set up the password for the user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * this set up the mood event list to the user
     * @param moodHistory
     */
    public void setMoodHistory(List<MoodEvent> moodHistory) {
        this.moodHistory = moodHistory;
    }
}
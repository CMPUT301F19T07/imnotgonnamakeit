package com.example.feelslikemonday.model;

/**
 * This is a model class for every user that your follow
 * It keeps track of their username and most recent Mood event
 */
public class FolloweeMoodEvent {

    private String username;
    private MoodEvent recentMood;

    public FolloweeMoodEvent(String username, MoodEvent recentMood) {
        this.username = username;
        this.recentMood = recentMood;
    }

    public MoodEvent getRecentMood() {
        return recentMood;
    }

    public void setRecentMood(MoodEvent recentMood) {
        this.recentMood = recentMood;
    }

    public String getUsername() {return username; }

    public void setUsername(String username) { this.username = username; }

}

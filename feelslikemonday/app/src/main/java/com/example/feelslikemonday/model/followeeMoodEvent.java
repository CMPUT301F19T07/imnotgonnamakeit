package com.example.feelslikemonday.model;

public class followeeMoodEvent {

    private String username;
    private MoodEvent recentMood;

    public followeeMoodEvent(String username, MoodEvent recentMood) {
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

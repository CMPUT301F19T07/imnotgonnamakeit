package com.example.feelslikemonday.model;

/**
 * This is a model class for every user that your follow
 * It keeps track of their username and most recent Mood event
 */
public class FolloweeMoodEvent {

    private String username;
    private MoodEvent recentMood;

    /**
     * This is a class that keeps track of a followee mood event
     *
     * @param username   This is a candidate username
     * @param recentMood This is the recent mood event of candidate user
     */
    public FolloweeMoodEvent(String username, MoodEvent recentMood) {
        this.username = username;
        this.recentMood = recentMood;
    }

    /**
     * this returns the user's recent mood
     *
     * @return return the user's recent mood event
     */
    public MoodEvent getRecentMood() {
        return recentMood;
    }

    /**
     * this sets the user's recent mood event
     *
     * @param recentMood This is the recent mood event of candidate user
     */
    public void setRecentMood(MoodEvent recentMood) {
        this.recentMood = recentMood;
    }

    /**
     * this returns the username of candidate user
     *
     * @return returns the string of the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * this sets the username of candidate user
     *
     * @param username This is a candidate username
     */
    public void setUsername(String username) {
        this.username = username;
    }

}

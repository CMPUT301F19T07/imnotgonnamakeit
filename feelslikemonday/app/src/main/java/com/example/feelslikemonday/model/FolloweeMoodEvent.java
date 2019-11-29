package com.example.feelslikemonday.model;

/**
 * This is a model class that keeps track of the most recent mood event of a user.
 * It has username attribute which represents the user account we want the most recent mood event of
 * It has a recentMood attribute which represents the most recent mood event of the user
 * It is used in FollowingFragment by a candidate user to keep track
 * of the most recent mood event of each user it is following
 */
public class FolloweeMoodEvent {

    private String username;
    private MoodEvent recentMood;

    /**
     * This method creates an instance of FolloweeMood event that contains a username
     * and the most recent mood event associated with that user
     * @param username
     * This is a username of a candidate user
     * @param recentMood
     * This is the most recent mood event of the user associated with username
     */
    public FolloweeMoodEvent(String username, MoodEvent recentMood) {
        this.username = username;
        this.recentMood = recentMood;
    }

    /**
     * This method returns recentMood, which is the most recent mood event of the user
     * @return
     * Return a MoodEvent that is the most recent mood event of the user
     */
    public MoodEvent getRecentMood() {
        return recentMood;
    }

    /**
     * This method sets recentMood, which is the user's most recent mood event
     * @param recentMood
     * This is a candidate MoodEvent that we want to set as the most recent MoodEvent of the user
     */
    public void setRecentMood(MoodEvent recentMood) {
        this.recentMood = recentMood;
    }

    /**
     * This returns the username of the user
     * @return
     * Return a string that is the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * This sets the username of the user
     * @param username
     * This is a candidate username that we want to set as the username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

}

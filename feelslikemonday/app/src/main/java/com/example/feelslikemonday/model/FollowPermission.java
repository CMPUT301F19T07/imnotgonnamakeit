package com.example.feelslikemonday.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as a "table" to retrieve permissions that folowees have granted the user
 */

public class FollowPermission {
    private String followerUsername;
    private List<String> followeeUsernames;

    //Empty Constructor for Firestore deserialization
    public FollowPermission() {
    }

    /**
     * This is a class that keeps track of a follow permssion
     *
     * @param followerUsername This is a username of follower
     */
    public FollowPermission(String followerUsername) {
        this.followeeUsernames = new ArrayList<>();
        this.followerUsername = followerUsername;
    }

    /**
     * this return the follower username, user itself
     *
     * @return return the username of follower
     */
    public String getFollowerUsername() {
        return followerUsername;
    }

    /**
     * this returns a list of followers' usernames
     *
     * @return return a list of followers' usernames
     */
    public List<String> getFolloweeUsernames() {
        return followeeUsernames;
    }
}

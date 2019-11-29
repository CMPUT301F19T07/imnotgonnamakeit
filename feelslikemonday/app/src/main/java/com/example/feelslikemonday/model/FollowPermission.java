package com.example.feelslikemonday.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a model class that contains a username of a candidate user and all of the usernames
 * of users that have permission to follow this candidate user
 * It has a followerUsername attribute which is the username of a candidate user
 * It has a followeeUsernames attribute which is a list of usernames of users that have permission
 * to follow the user with the username followerUsername
 */

public class FollowPermission {
    private String followerUsername;
    private List<String> followeeUsernames;

    /**
     * This empty constructor allows Firebase to deserialize an object
     */
    public FollowPermission() {
    }

    /**
     * This method creates an instance of FollowPermission event that contains a username of the
     * user requesting permission and the list of all the usernames that this user has sent a request to
     * @param followerUsername This is a username of a candidate user
     */
    public FollowPermission(String followerUsername) {
        this.followeeUsernames = new ArrayList<>();
        this.followerUsername = followerUsername;
    }

    /**
     * This method returns the username of the user sending follow requests to other users
     * @return
     * Return a string which is the username of the user sending follow requests to other users
     */
    public String getFollowerUsername() {
        return followerUsername;
    }

    /**
     * This returns a list of usernames of users who have received a request from followerUsername
     * @return
     * Return a list of usernames of users who have received a request from followerUsername
     */
    public List<String> getFolloweeUsernames() {
        return followeeUsernames;
    }
}

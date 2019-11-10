package com.example.feelslikemonday.model;

import java.util.ArrayList;
import java.util.List;

/*
*This class acts as a "table" to retrieve permissions that folowees have granted the user
*/

public class FollowPermission {
    private String followerUsername;
    private List<String> followeeUsernames;

    //Empty Constructor for Firestore deserialization
    public FollowPermission(){}


    public FollowPermission(String followerUsername){
        this.followeeUsernames = new ArrayList<>();
        this.followerUsername = followerUsername;
    }

    /**
     * this return the follower username, user itself
     * @return
     *   the username of the app
     */
    public String getFollowerUsername(){
        return followerUsername;
    }

    /**
     * this return the followeeMoodEvent usernames
     * @return
     *    the followees' username that user have approved
     */
    public List<String> getFolloweeUsernames(){
        return followeeUsernames;
    }
}

package com.example.feelslikemonday.model;

import java.util.ArrayList;
import java.util.List;

public class FollowPermission {
    private String followerUsername;
    private List<String> followeeUsernames;

    //Empty Constructor for Firestore deserialization
    public FollowPermission(){}

    public FollowPermission(String followerUsername){
        this.followeeUsernames = new ArrayList<>();
        this.followerUsername = followerUsername;
    }

    public String getFollowerUsername(){
        return followerUsername;
    }

    public List<String> getFolloweeUsernames(){
        return followeeUsernames;
    }
}

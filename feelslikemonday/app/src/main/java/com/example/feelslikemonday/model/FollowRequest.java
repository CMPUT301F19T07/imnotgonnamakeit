package com.example.feelslikemonday.model;

import java.util.List;

public class FollowRequest {
    private List<String> requesterUsernames;
    private String recipientUsername;

    //Empty Constructor for Firestore deserialization
    FollowRequest(){}

    FollowRequest(List<String> requesterUsernames,String recipientUsername){
        this.requesterUsernames = requesterUsernames;
        this.recipientUsername = recipientUsername;
    }

    public List<String> getRequesterUsernames() {
        return requesterUsernames;
    }
    public String getRecipientUsername() {
        return recipientUsername;
    }

}

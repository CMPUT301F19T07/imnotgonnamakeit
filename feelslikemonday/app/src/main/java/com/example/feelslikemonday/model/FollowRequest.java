package com.example.feelslikemonday.model;

import java.util.ArrayList;
import java.util.List;

public class FollowRequest {
    private List<String> requesterUsernames;
    private String recipientUsername;

    //Empty Constructor for Firestore deserialization
    public FollowRequest(){}

    public FollowRequest(String recipientUsername){
        this.requesterUsernames = new ArrayList<>();
        this.recipientUsername = recipientUsername;
    }

    public List<String> getRequesterUsernames() {
        return requesterUsernames;
    }
    public String getRecipientUsername() {
        return recipientUsername;
    }

}
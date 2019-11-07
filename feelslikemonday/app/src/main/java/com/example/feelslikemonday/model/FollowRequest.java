package com.example.feelslikemonday.model;

import java.util.ArrayList;
import java.util.List;

/*
*This class acts as a "table" to retrieve requests that have been sent to the user
*/

public class FollowRequest {
    private List<String> requesterUsernames;
    private String recipientUsername;

    //Empty Constructor for Firestore deserialization
    public FollowRequest(){}

    public FollowRequest(String recipientUsername){
        this.requesterUsernames = new ArrayList<>();
        this.recipientUsername = recipientUsername;
    }

    /**
     * this return a list of requester username given the recipient username
     * @return
     *    a list of requester username
     */
    public List<String> getRequesterUsernames() {
        return requesterUsernames;
    }

    /**
     * this return the recipient username, the user itself
     * @return
     *    return the recipient username
     */
    public String getRecipientUsername() {
        return recipientUsername;
    }

}

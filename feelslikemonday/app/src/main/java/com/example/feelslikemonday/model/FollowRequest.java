package com.example.feelslikemonday.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as a "table" to retrieve requests that have been sent to the user
 * Follow requests keep track of the person requesting to follow another user and for the person receiving the request
 * Sending requests can be reached from the main activity using the navigation drawer by clicking send requests
 * Viewing and managing requests can be reached from the main activity using the navigation drawer by clicking Follower requests
 */

public class FollowRequest {
    private List<String> requesterUsernames;
    private String recipientUsername;

    //Empty Constructor for Firestore deserialization
    public FollowRequest() {
    }

    /**
     * This is a class that keeps track of a follow request
     *
     * @param recipientUsername This is a recipient username
     */
    public FollowRequest(String recipientUsername) {
        this.requesterUsernames = new ArrayList<>();
        this.recipientUsername = recipientUsername;
    }

    /**
     * this returns a list of requester usernames
     *
     * @return return a list of requester usernames
     */
    public List<String> getRequesterUsernames() {
        return requesterUsernames;
    }

    /**
     * this returns the recipient username, the user itself
     *
     * @return return the recipient username
     */
    public String getRecipientUsername() {
        return recipientUsername;
    }

}

package com.example.feelslikemonday.DAO;

import com.example.feelslikemonday.model.FollowRequest;

/**
 * This interface contains a callback method for the follow request DAO
 */
public interface FollowRequestCallback {
    void onCallback(FollowRequest followRequest);
}

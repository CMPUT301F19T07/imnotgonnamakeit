package com.example.feelslikemonday.DAO;

import com.example.feelslikemonday.model.FollowPermission;

/**
 * This interface contains a callback method for the follow permission DAO
 * Used in the FollowPermissionDAO when working with queried FollowPermission information
 */
public interface FollowPermissionCallback {
    void onCallback(FollowPermission followPermission);
}

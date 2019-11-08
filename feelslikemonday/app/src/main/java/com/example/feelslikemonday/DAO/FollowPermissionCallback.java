package com.example.feelslikemonday.DAO;

import com.example.feelslikemonday.model.FollowPermission;

/**
 * This interface contains a callback method for the follow permission DAO
 */
public interface FollowPermissionCallback {
    void onCallback(FollowPermission followPermission);
}

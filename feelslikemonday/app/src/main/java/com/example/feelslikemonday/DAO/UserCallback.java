package com.example.feelslikemonday.DAO;

import com.example.feelslikemonday.model.User;

/**
 * This interface contains a general callback method for DAO
 * Source from https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
 */
public interface UserCallback {
    void onCallback(User user);
}
//commit
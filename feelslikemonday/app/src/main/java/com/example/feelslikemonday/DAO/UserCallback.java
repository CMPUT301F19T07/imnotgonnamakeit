package com.example.feelslikemonday.DAO;

import com.example.feelslikemonday.model.User;

/**
 * Callback object. Must be used in-order to work with data from the DAO.
 * Source from https://stackoverflow.com/questions/47847694/how-to-return-datasnapshot-value-as-a-result-of-a-method
 */
public interface UserCallback {
    void onCallback(User user);
}

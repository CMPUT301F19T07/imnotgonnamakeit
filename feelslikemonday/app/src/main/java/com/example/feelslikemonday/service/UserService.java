package com.example.feelslikemonday.service;

import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.model.User;

public class UserService {

    private static final UserService instance = new UserService();

    public UserService(){}
    public static UserService getInstance(){return instance;}

    // a work in progress-
    // different implementation - onSuccess and onFailure do different things in login and signup
    // doesn't currently use this to validate whether user exists
    public boolean userExists(String username){
        UserDAO userDao = UserDAO.getInstance();
        userDao.get(username, new UserCallback() {
            @Override
            public void onCallback(User user) {
                // this can't return void if it's being called to check whether user exists
                // shouldn't usercallback return a user?
            }
        }, new VoidCallback() {
            @Override
            public void onCallback() {
                // this can't return void
            }
        });
        return false;
    }

}

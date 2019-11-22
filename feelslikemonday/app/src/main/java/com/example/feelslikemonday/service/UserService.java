package com.example.feelslikemonday.service;

/*
 *This class checks if a user exists
 */

import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.model.User;

public class UserService {

    private static UserDAO userDAO = UserDAO.getInstance();

    /**
     * This checks if a user exists
     * @param username
     * This is a candidate username
     */
    public static void signupUserExists(String username){
        userDAO.get(username, new UserCallback() {
            @Override
            public void onCallback(User user) {
                // SUCCESS
            }
        }, new VoidCallback() {
            @Override
            public void onCallback() {
                // FAILURE
            }
        });
    }
    /**
     * This checks empty field
     * @param username
     * This is a candidate username
     * @param password
     * This is a user's password
     */
    public static boolean checkEmptyField(String username, String password){
        if (username.length() == 0 ||password.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

}

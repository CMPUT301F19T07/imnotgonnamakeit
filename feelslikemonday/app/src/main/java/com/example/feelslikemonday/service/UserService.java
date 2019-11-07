package com.example.feelslikemonday.service;

/*
 *This class checks if a user exists
 */

import android.widget.Toast;

import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.model.User;
import com.example.feelslikemonday.ui.login.SignupActivity;

public class UserService {

    static UserDAO userDAO = UserDAO.getInstance();

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

    public static boolean checkEmptyField(String username, String password){
        if (username.length() == 0 ||password.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

}

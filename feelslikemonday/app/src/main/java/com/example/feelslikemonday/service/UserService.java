package com.example.feelslikemonday.service;

/*
 *This class checks if a user exists, and runs logic for other user related things
 */

public class UserService {

    public static boolean checkEmptyField(String username, String password){
        return username.length() == 0 || password.length() == 0;
        }

    }


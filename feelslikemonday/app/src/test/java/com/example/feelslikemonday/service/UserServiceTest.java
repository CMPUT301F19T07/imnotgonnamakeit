package com.example.feelslikemonday.service;

import com.example.feelslikemonday.model.User;
import com.example.feelslikemonday.service.UserService;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {
    private String username = "usertestusername";
    private String passsword = "12345";
    private String emptyusername = "";
    private String emptypassword = "";

    @Test
    public void testEmptyField(){
        assertFalse(UserService.checkEmptyField(username,passsword));
        assertTrue(UserService.checkEmptyField(emptyusername, passsword));
        assertTrue(UserService.checkEmptyField(username, emptypassword));
        assertTrue(UserService.checkEmptyField(emptyusername, emptypassword));
    }

}

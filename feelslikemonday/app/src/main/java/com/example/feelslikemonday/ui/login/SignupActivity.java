package com.example.feelslikemonday.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.feelslikemonday.DAO.BooleanCallback;
import com.example.feelslikemonday.DAO.FollowPermissionDAO;
import com.example.feelslikemonday.DAO.FollowRequestDAO;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.FollowPermission;
import com.example.feelslikemonday.model.FollowRequest;
import com.example.feelslikemonday.model.User;

/**
 * This class creates and shows the signup page to the user
 * It allows the user to create a new user account with a unique username, password,
 * empty list of followers, and empty list of users requesting to follow this user
 * This page can be accessed by pressing signup from the login page
 * When a password and unique username is entered and confirm is pressed the user is
 * sent to the home page
 * The user goes back to the login page by pressing cancel
 */
public class SignupActivity extends AppCompatActivity {

    private EditText signupUsername;
    private EditText signupPassword;
    private String username;
    private User user;
    private SharedPreferences pref;
    private UserDAO userDAO = UserDAO.getInstance();
    public static final String PREFS_NAME = "user_preferences";
    public static final String USERNAME_KEY = "username_key";

    /**
     * This method creates the signup page and shows a signup title, a textbox for
     * entering the username and password, a "confirm" button to signup, and a "cancel" button
     * to return to the login page
     * @param savedInstanceState This is a default parameter for onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupUsername = findViewById(R.id.signup_username_edit);
        signupPassword = findViewById(R.id.signup_password_edit);
    }

    /**
     * This method clears username and password fields when the user returns to the signup page
     */
    @Override
    protected void onResume(){
        super.onResume();
        //Clear fields
        signupUsername.setText("");
        signupPassword.setText("");
    }

    /**
     * This method closes the signup screen when the user presses cancel and sends the user back to the login screen
     * @param view This keeps track of which view calls attemptLogin
     */
    public void cancelSignup(View view) {
        finish();
    }

    /**
     * This method allows the user to create a new user account with a unique username, password,
     * empty list of followers, and empty list of users requesting to follow this user
     * This user account is only created when the user inputs a unique username
     * @param view This keeps track of which view calls attemptLogin
     */
    public void confirmSignup(View view) {
        // Create user, follow request, then follower permission in firebase before going to main screen
        username = signupUsername.getText().toString();
        String password = signupPassword.getText().toString();
        if (username.length() == 0 || password.length() == 0) {
            Toast.makeText(SignupActivity.this, "Missing username or password", Toast.LENGTH_LONG).show();
        } else {
            user = new User(username, password);
            userDAO.checkIfExists(username, new BooleanCallback() {
                @Override
                public void onCallback(Boolean doesExist) {
                    if(doesExist){
                        Toast.makeText(SignupActivity.this, "This user already exists", Toast.LENGTH_LONG).show();
                    }
                    else{
                        // User doesn't exist in the database, so create a new user
                        // Save user to shared preferences
                        // 0 in second argument indicates file holding username can only be accessed by calling the application
                        pref = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(USERNAME_KEY, signupUsername.getText().toString());
                        editor.apply();
                        userDAO.createOrUpdate(user, new VoidCallback() {
                            @Override
                            public void onCallback() {
                                // Create followrequest in firebase
                                FollowRequestDAO followRequestDao = FollowRequestDAO.getInstance();
                                FollowRequest followRequest = new FollowRequest(username);
                                followRequestDao.createOrUpdate(username, followRequest, new VoidCallback() {
                                    @Override
                                    public void onCallback() {
                                        // Create followerpermission in firebase
                                        FollowPermissionDAO followPermissionDao = FollowPermissionDAO.getInstance();
                                        FollowPermission followPermission = new FollowPermission(username);
                                        followPermissionDao.createOrUpdate(username, followPermission, new VoidCallback() {
                                            @Override
                                            public void onCallback() {
                                                Intent myIntent = new Intent(SignupActivity.this, MainActivity.class);
                                                startActivity(myIntent);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                }
            }, new VoidCallback() {
                @Override
                public void onCallback() {
                    Toast.makeText(SignupActivity.this, "An error occurred in Firebase", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

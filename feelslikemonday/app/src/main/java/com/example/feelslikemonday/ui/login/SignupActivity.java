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

/*User Preferences designed according to tutorial on:
https://www.journaldev.com/9412/android-shared-preferences-example-tutorial
*/

/**
 * This class is responsible for signing up new user
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
     * This initializes SignupActivity
     * @param savedInstanceState
     * This is a previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupUsername = findViewById(R.id.signup_username_edit);
        signupPassword = findViewById(R.id.signup_password_edit);
    }

    /**
     * This clears username and password fields at OnResume stage
     */
    @Override
    protected void onResume(){
        super.onResume();
        //Clear fields
        signupUsername.setText("");
        signupPassword.setText("");
    }

    /**
     * This finishes SignupActivity when user presses cancel
     * @param view
     * This is a view returned by onCreate()
     */
    public void cancelSignup(View view) {
        finish();
    }

    /**
     * This checks if the new username is unique and creates a user with follow requests and
     * follow permissions if the username is unique
     * @param view
     * This is a view returned by onCreate()
     */
    public void confirmSignup(View view) {
        // Create user, follow request, then follower permission in firebase before going to main screen
        username = signupUsername.getText().toString().trim().toLowerCase();
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

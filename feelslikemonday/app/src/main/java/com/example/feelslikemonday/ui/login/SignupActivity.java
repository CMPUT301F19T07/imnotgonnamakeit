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

public class SignupActivity extends AppCompatActivity {

    private EditText signupUsername;
    private EditText signupPassword;
    private String username;
    private String password;
    private User user;
    private SharedPreferences pref;
    private UserDAO userDAO = UserDAO.getInstance();
    public static final String PREFS_NAME = "user_preferences";
    public static final String USERNAME_KEY = "username_key";

    public void cancelSignup(View view) {
        finish();
    }

    public void confirmSignup(View view) {
        // Create user, follow request, then follower permission in firebase before going to main screen
        username = signupUsername.getText().toString();
        password = signupPassword.getText().toString();
        if (username.length() == 0 || password.length() == 0) {
            Toast.makeText(SignupActivity.this, "Error: Empty Field", Toast.LENGTH_LONG).show();
        } else {
            user = new User(username, password);
            userDAO.checkIfExists(username, new BooleanCallback() {
                @Override
                public void onCallback(Boolean doesExist) {
                    if(doesExist){
                        Toast.makeText(SignupActivity.this, "Error: This user already exists", Toast.LENGTH_LONG).show();
                    }
                    else{
                        // User doesn't exist in the database, so create a new user
                        // Save user to shared preferences
                        // 0 in second argument indicates file holding username can only be accessed by calling the application
                        pref = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(USERNAME_KEY, signupUsername.getText().toString());
                        editor.commit();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupUsername = findViewById(R.id.signupUsernameEdit);
        signupPassword = findViewById(R.id.signupPasswordEdit);
    }
}

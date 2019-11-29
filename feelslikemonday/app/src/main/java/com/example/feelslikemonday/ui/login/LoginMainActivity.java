package com.example.feelslikemonday.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.User;

/**
 * This class creates and shows the login page to the user
 * It allows the user to enter an existing username and its corresponding password to login
 * When the app is first installed, this is the starting page
 * This page can be accessed by logging out or by pressing cancel on the signup page
 * When an existing username and corresponding password are entered and the user presses login,
 * they are sent to the home page, MainActivity
 * This is the first screen that is started when the app is opened
 */
public class LoginMainActivity extends AppCompatActivity {
    private EditText loginUsername;
    private EditText loginPassword;
    private String username;
    private String password;
    private SharedPreferences pref;
    private UserDAO userDAO = UserDAO.getInstance();
    public static final String PREFS_NAME = "user_preferences";
    public static final String USERNAME_KEY = "username_key";

    /**
     * This method creates the login page and shows a login title, a textbox for
     * entering the username and password, a button to login, and red "sign up" text to go to
     * the signup page
     * If the user closes the app without logging out, this method changes the app to the last
     * opened screen by the user instead of displaying the login screen
     * @param savedInstanceState This is a default parameter for onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        loginUsername = findViewById(R.id.login_username_edit);
        loginPassword = findViewById(R.id.login_password_edit);
        String loggedInUsername = getApplicationContext().getSharedPreferences(PREFS_NAME, 0).getString(USERNAME_KEY,null);
        //if user is already logged in
        if (loggedInUsername != null) {
            Intent myIntent = new Intent(LoginMainActivity.this, MainActivity.class);
            startActivity(myIntent);
        }
    }

    /**
     * This method clears username and password fields when the user goes back to the login page
     */
    @Override
    protected void onResume() {
        super.onResume();
        //Clear fields
        loginUsername.setText("");
        loginPassword.setText("");
    }

    /**
     * This method allows the user to login and go to the home screen
     * It then checks if the username and password entered by user matches the username and
     * password of a user in Firestore
     * It displays error messages if the user is missing input in the username or password field,
     * if the user inputs an incorrect password, or if the user attempts to login into a non-existent user
     * If the user inputs an existing username and the correct password, the user is sent to the home screen
     * @param view This keeps track of which view calls attemptLogin
     */
    public void attemptLogin(View view) {
        username = loginUsername.getText().toString();
        password = loginPassword.getText().toString();
        if (username.length() == 0 || password.length() == 0) {
            Toast.makeText(LoginMainActivity.this, "Missing username or password", Toast.LENGTH_LONG).show();
        } else {
            userDAO.get(username, new UserCallback() {
                @Override
                public void onCallback(User user) {
                    if (user.getPassword().equals(password)) {
                        pref = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(USERNAME_KEY, username);
                        editor.apply();
                        Intent myIntent = new Intent(LoginMainActivity.this, MainActivity.class);
                        startActivity(myIntent);
                    } else {
                        Toast.makeText(LoginMainActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                    }
                }
            }, new VoidCallback() {
                @Override
                public void onCallback() {
                    Toast.makeText(LoginMainActivity.this, "User does not exist", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * This sends the app to the signup page when the user wants to sign up
     * @param view This keeps track of which view calls goToSignupProcess
     */
    public void goToSignupProcess(View view) {
        Intent myIntent = new Intent(this, SignupActivity.class);
        this.startActivity(myIntent);
    }
}

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
 * This class is responsible for verifying username and passward
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
     * This initializes LoginMainActivity
     * @param savedInstanceState
     * This is a previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        loginUsername = findViewById(R.id.loginUsernameEdit);
        loginPassword = findViewById(R.id.loginPasswordEdit);
        String loggedInUsername = getApplicationContext().getSharedPreferences(PREFS_NAME, 0).getString(USERNAME_KEY,null);
        //if user is already logged in
        if (loggedInUsername != null){
            Intent myIntent = new Intent(LoginMainActivity.this, MainActivity.class);
            startActivity(myIntent);
        }
    }

    /**
     * This clears Username and Passward fields at OnResume stage
     */
    @Override
    protected void onResume(){
        super.onResume();
        //Clear fields
        loginUsername.setText("");
        loginPassword.setText("");
    }

    /**
     * This checks username and password when user attempts to log in
     * @param view
     * This is a view returned by onCreate()
     */
    public void attemptLogin(View view){
        username = loginUsername.getText().toString();
        password = loginPassword.getText().toString();
        if (username.length() == 0 || password.length() == 0) {
            Toast.makeText(LoginMainActivity.this, "Error: Missing input", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(LoginMainActivity.this, "Error: User does not exist", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * This goes to SignupActivity when user want to sign up
     * @param view
     * This is a view returned by onCreate()
     */
    public void goToSignupProcess(View view){
        Intent myIntent = new Intent(this, SignupActivity.class);
        this.startActivity(myIntent);
    }
}

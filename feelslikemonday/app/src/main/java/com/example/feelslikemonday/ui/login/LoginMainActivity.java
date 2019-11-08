package com.example.feelslikemonday.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.User;
import com.example.feelslikemonday.service.UserService;

/*Responsible for verifying username and password*/

public class LoginMainActivity extends AppCompatActivity {

    EditText loginUsername;
    EditText loginPassword;
    String username;
    String password;
    SharedPreferences pref;
    UserDAO userDAO = UserDAO.getInstance();

    public static final String PREFS_NAME = "user_preferences";
    public static final String USERNAME_KEY = "username_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        loginUsername = findViewById(R.id.loginUsernameEdit);
        loginPassword = findViewById(R.id.loginPasswordEdit);
    }

    public void attemptLogin(View view){
        username = loginUsername.getText().toString();
        password = loginPassword.getText().toString();
        if (UserService.checkEmptyField(username,password)) {
            Toast.makeText(LoginMainActivity.this, "Please enter username or password", Toast.LENGTH_LONG).show();
        } else {
            userDAO.get(username, new UserCallback() {
                @Override
                public void onCallback(User user) {
                    if (user.getPassword().equals(password)) {
                        pref = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(USERNAME_KEY, username);
                        editor.commit();
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

    public void goToSignupProcess(View view){
        Intent myIntent = new Intent(this, SignupActivity.class);
        this.startActivity(myIntent);
    }
}

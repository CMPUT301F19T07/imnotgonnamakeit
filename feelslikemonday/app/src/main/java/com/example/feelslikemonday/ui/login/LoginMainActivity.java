package com.example.feelslikemonday.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.R;

import static com.example.feelslikemonday.ui.login.SignupActivity.USERNAME_KEY;

public class LoginMainActivity extends AppCompatActivity {
    EditText loginUsername;
    SharedPreferences pref;
    public static String userNameShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
    }

    public void attemptLogin(View view){
        loginUsername = findViewById(R.id.editText);
        pref = getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SignupActivity.USERNAME_KEY, loginUsername.getText().toString());
        userNameShared = loginUsername.getText().toString();
        editor.commit();

        String temp;
        temp = pref.getString(SignupActivity.USERNAME_KEY,null);
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }

    public void goToSignupProcess(View view){
        Intent myIntent = new Intent(this, SignupActivity.class);
        this.startActivity(myIntent);
    }
}

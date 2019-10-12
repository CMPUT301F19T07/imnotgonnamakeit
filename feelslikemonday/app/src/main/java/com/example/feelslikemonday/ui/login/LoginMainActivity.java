package com.example.feelslikemonday.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.R;

public class LoginMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
    }

    public void attemptLogin(View view){
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }

    public void goToSignupProcess(View view){
        Intent myIntent = new Intent(this, SignupActivity.class);
        this.startActivity(myIntent);
    }
}

package com.example.feelslikemonday.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.R;

public class SignupActivity extends AppCompatActivity {

    public void cancelSignup(View view) {
        finish();

    }

    public void confirmSignup(View view) {
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

    }


}

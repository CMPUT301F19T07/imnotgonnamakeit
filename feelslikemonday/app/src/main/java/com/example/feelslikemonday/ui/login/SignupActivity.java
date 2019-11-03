package com.example.feelslikemonday.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.feelslikemonday.MainActivity;
import com.example.feelslikemonday.R;

/*User Preferences designed according to tutorial on:
https://www.journaldev.com/9412/android-shared-preferences-example-tutorial
 */

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

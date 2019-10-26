package com.example.feelslikemonday.ui.moods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.feelslikemonday.R;

import java.util.ArrayList;
import java.util.List;

public class addNewMoodActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_mood);

        fillSpinners();

    }

    public void Save(View view) {

        Log.v("button", "save pressed");

    }



    private void fillSpinners() {
        List<String> moodSpinner =  new ArrayList<>();
        moodSpinner.add("mood1");
        moodSpinner.add("mood2");
        moodSpinner.add("mood3");
        moodSpinner.add("mood4");
        moodSpinner.add("mood5");
        moodSpinner.add("mood6");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, moodSpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner moodSpinnerSpinner = findViewById(R.id.mood_spinner);
        moodSpinnerSpinner.setAdapter(adapter);

        List<String> socialSpinner =  new ArrayList<>();
        socialSpinner.add("social1");
        socialSpinner.add("social2");
        socialSpinner.add("social3");
        socialSpinner.add("social4");
        socialSpinner.add("social5");
        socialSpinner.add("social6");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, socialSpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner socialSpinnerSpinner = findViewById(R.id.social_spinner);
        socialSpinnerSpinner.setAdapter(adapter1);
    }


}

package com.example.feelslikemonday.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.feelslikemonday.R;

/*Responsible for showing user's current mood */
public class DisplayCurrentMood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_current_mood);
        
        Intent intent = getIntent();

        TextView View_followee = findViewById(R.id.followeeUsername);
        TextView View_date = findViewById(R.id.textViewDate);
        TextView View_time = findViewById(R.id.textViewTime);
        TextView View_emotionalState = findViewById(R.id.textViewEmotionalState);
        TextView View_reason = findViewById(R.id.textViewReason);
        TextView View_socialSituation = findViewById(R.id.textViewsocialSituation);
        TextView View_moodType = findViewById(R.id.textViewMoodType);

        View_followee.setText(intent.getStringExtra ("followeeUsername"));
        View_date.setText(intent.getStringExtra ("myDate"));
        View_time.setText(intent.getStringExtra ("mytime"));
        View_emotionalState.setText(intent.getStringExtra ("emotionalState"));
        View_reason.setText(intent.getStringExtra ("reason"));
        View_socialSituation.setText(intent.getStringExtra ("socialSituation"));
        View_moodType.setText(intent.getStringExtra ("moodType"));

        Button button = (Button) findViewById(R.id.button_back);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                finish();
            }
        });
    }
}

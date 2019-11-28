package com.example.feelslikemonday.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.feelslikemonday.R;

/**
 * This class is responsible for showing user's current mood
 */
public class DisplayCurrentMood extends AppCompatActivity {
    /**
     * This initializes DisplayCurrentMood activity
     * @param savedInstanceState
     * This is a previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_current_mood);
        
        Intent intent = getIntent();

        ImageView imageView = findViewById(R.id.mood_photo);
        TextView followeeTextView = findViewById(R.id.followeeUsername);
        TextView dateTextView = findViewById(R.id.textViewDate);
        TextView timeTextView = findViewById(R.id.textViewTime);
        TextView emotionalStateTextView = findViewById(R.id.textViewEmotionalState);
        TextView reasonTextView = findViewById(R.id.textViewReason);
        TextView socialSituationTextView = findViewById(R.id.textViewsocialSituation);
        TextView moodTypeTextView = findViewById(R.id.textViewMoodType);

        byte[] imageByteArr = intent.getByteArrayExtra("image");
        if(imageByteArr != null){
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArr, 0, imageByteArr.length);
            imageView.setImageBitmap(imageBitmap);
        }
        followeeTextView.setText(intent.getStringExtra ("followeeUsername"));
        dateTextView.setText(intent.getStringExtra ("myDate"));
        timeTextView.setText(intent.getStringExtra ("mytime"));
        emotionalStateTextView.setText(intent.getStringExtra ("emotionalState"));
        reasonTextView.setText(intent.getStringExtra ("reason"));
        socialSituationTextView.setText(intent.getStringExtra ("socialSituation"));
        moodTypeTextView.setText(intent.getStringExtra ("moodType"));

        Button button = (Button) findViewById(R.id.buttonBack);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                finish();
            }
        });
    }
}

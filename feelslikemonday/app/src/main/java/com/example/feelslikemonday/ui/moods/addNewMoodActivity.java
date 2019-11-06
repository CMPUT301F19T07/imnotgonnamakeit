package com.example.feelslikemonday.ui.moods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.MoodEvent;
import com.example.feelslikemonday.model.MoodType;
import com.example.feelslikemonday.model.User;
import com.example.feelslikemonday.ui.login.LoginMainActivity;
import com.example.feelslikemonday.ui.login.SignupActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class addNewMoodActivity extends AppCompatActivity {

    private Button attachPhotoButton;
    private Button cancelButton;
    private Button saveButton;
    private Spinner moodSpiner;
    private Spinner socialSituationSpinner;
    private EditText reason;
    private User currentUser;
    private int moodState = 0; //if this variable =0 it means you're addind a new mood, it 1 you're editing the current mood
    private int moodIndex = 0;
    private String moodDate;
    private String moodTime;
    private SharedPreferences pref;
    private String myUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_mood);
        cancelButton = findViewById(R.id.mood_cancel);
        attachPhotoButton = findViewById(R.id.mood_attach_photo);
        saveButton = findViewById(R.id.button11);
        reason = findViewById(R.id.editText8);
        moodSpiner = findViewById(R.id.mood_spinner);
        socialSituationSpinner = findViewById(R.id.social_spinner);

        // still need to read the image and location - left it for later
        fillSpinners();
        attachPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addNewMoodActivity.this, attachPhotoActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        moodState = 0;
        if (intent != null) {
            moodState = intent.getIntExtra ("state",0);
            moodIndex = 0; //this variable is for sorting
            if (moodState == 1) {
                moodTime = intent.getStringExtra ("mytime"); // get current time
                moodDate = intent.getStringExtra ("myDate"); // get current date
                reason.setText(intent.getStringExtra ("reason")); // get  reason
                socialSituationSpinner.setSelection(intent.getIntExtra ("socialSituation",0)); //get social and set it to social spinner
                moodSpiner.setSelection(intent.getIntExtra ("moodTypeName",0)); // get mood and set it within the spinner
              //String state = intent.getStringExtra ("emotionalState");
                moodIndex = intent.getIntExtra ("stateIndex",0); // index of mood that we need to edit
            }
        }

        pref = getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY,null);
        // used to connect with the current user logged in --> needs to be changed with user preference once login stuff is done
        UserDAO userDAO = new UserDAO();
        userDAO.get(myUserID, new UserCallback() {
            @Override
            public void onCallback(User user) {
                currentUser = user;
            }
        }, new VoidCallback() {
            @Override
            public void onCallback() {
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // read the current user mood list
                List<MoodEvent> moodHistoryTempTemp = currentUser.getMoodHistory();

                if (moodState == 0) {
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat stf = new SimpleDateFormat("kk:mm");
                    moodDate = sdf.format(date).toString();
                    moodTime = stf.format(date).toString();
                }

                String reasonChoice = reason.getText().toString();
                String emotionState = "No emotion";
                String social = MoodEvent.SOCIAL_SITUATIONS.get(socialSituationSpinner.getSelectedItemPosition());
                MoodType myMoodType;
                MoodEvent myMood;

                myMoodType = new MoodType(MoodEvent.MOOD_TYPES.get(moodSpiner.getSelectedItemPosition()).getName(),MoodEvent.MOOD_TYPES.get(moodSpiner.getSelectedItemPosition()).getEmoji());
                myMood = new MoodEvent(moodDate,moodTime, emotionState, reasonChoice,  myMoodType, social);


                if (moodState == 0) {
                    // add new mood at the top, location 0, this way the mood list is always in reverse chronological order
                    moodHistoryTempTemp.add(moodIndex, myMood);
                }
                else{
                    // edit the existing mood
                    moodHistoryTempTemp.set(moodIndex, myMood);
                }
                // update the user object
                UserDAO userAdo = new UserDAO();
                userAdo.createOrUpdate(currentUser,new VoidCallback(){
                    @Override
                    public void onCallback() {
                    }
                });
                finish();
            }
        });
    }


    public void Save(View view) {

        Log.v("button", "save pressed");
        }


// List<MoodType> MOOD_TYPES, List<String>  SOCIAL_SITUATIONS
    private void fillSpinners( ) {

        List<String> moodSpinner =  new ArrayList<>();

        for (int i = 0; i < MoodEvent.MOOD_TYPES.size(); i++) {
            moodSpinner.add(MoodEvent.MOOD_TYPES.get(i).getEmoji()+ " ( "+MoodEvent.MOOD_TYPES.get(i).getName()+" )");
        }

        List<String> socialSpinner =  new ArrayList<>();

        for (int i = 0; i < MoodEvent.SOCIAL_SITUATIONS.size(); i++) {
            socialSpinner.add(MoodEvent.SOCIAL_SITUATIONS.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, moodSpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner moodSpinnerSpinner = findViewById(R.id.mood_spinner);
        moodSpinnerSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, socialSpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner socialSpinnerSpinner = findViewById(R.id.social_spinner);
        socialSpinnerSpinner.setAdapter(adapter1);
    }
}


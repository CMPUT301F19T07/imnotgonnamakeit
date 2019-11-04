package com.example.feelslikemonday.ui.moods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class addNewMoodActivity extends AppCompatActivity {

    private Button attachPhotoButton;
    private Button cancelButton;
    private Button saveButton;
    private Spinner moodSpiner;
    private Spinner socialSpiner;
    private EditText reason;
    private User currentUser;
    private int modeState = 0; //0 add new mood, 1 edit current mood
    private int modeIndex = 0; // record location 0 for new entries as latest
    private String moodDate;
    private String moodTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_mood);
        cancelButton = findViewById(R.id.mood_cancel);
        attachPhotoButton = findViewById(R.id.mood_attach_photo);
        saveButton = findViewById(R.id.button11);
        reason = findViewById(R.id.editText8);
        moodSpiner = findViewById(R.id.mood_spinner);
        socialSpiner = findViewById(R.id.social_spinner);

        // still need to read the image and location for later

        fillSpinners();
        attachPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addNewMoodActivity.this, attachPhotoActivity.class);
                startActivity(intent);
            }
        });

        // if this requeste based on Edit call
        Intent intent = getIntent();
        modeState = 0;
        if (intent != null)
        {
            modeState = intent.getIntExtra ("state",0);
            modeIndex = 0;
            if (modeState == 1)
            {
                 // 1 edit current mood this does not add a new mood
                moodTime = intent.getStringExtra ("mytime"); // get the time as we will not change it
                moodDate = intent.getStringExtra ("myDate"); // get the date as we will not change it
                reason.setText(intent.getStringExtra ("reason")); // get the reason that we may need to change
                socialSpiner.setSelection(intent.getIntExtra ("socialSituation",0)); //get the location of social and set it to social spinet
                moodSpiner.setSelection(intent.getIntExtra ("moodTypeName",0)); // get the mood and set it within the spiner
//                String state = intent.getStringExtra ("emotionalState"); // for later
                modeIndex  = intent.getIntExtra ("stateIndex",0); // index of mood that we need to edit

            }
        }

        // uset to connect with needs to be changed User.myTempUserName
        UserDAO userDAO = new UserDAO();
        userDAO.get(User.myTempUserName, new UserCallback() {
            @Override
            public void onCallback(User user) {
               // current user the is object for the login user
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

                if (modeState == 0)
                {
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat stf = new SimpleDateFormat("kk:mm");
                    moodDate = sdf.format(date).toString();
                    moodTime = stf.format(date).toString();
                }// 1 edit current mood

                // set current date and time
                String reasonChoice = reason.getText().toString();
                String emotionState = "No emotion";
                String social = MoodEvent.SOCIAL_SITUATIONS.get(socialSpiner.getSelectedItemPosition());
                MoodType myType;
                MoodEvent mymood;
                // prepare the object of the mood
                myType = new MoodType(MoodEvent.MOOD_TYPES.get(moodSpiner.getSelectedItemPosition()).getName(),MoodEvent.MOOD_TYPES.get(moodSpiner.getSelectedItemPosition()).getEmoji());
                // prepare the mood object
                mymood = new MoodEvent(moodDate,moodTime, emotionState, reasonChoice,  myType, social);

                // add the new mood object at location 0, ie most recent one
                if (modeState == 0) {
                    // add new mood at the top, location 0
                    moodHistoryTempTemp.add(modeIndex, mymood);
                }
                else{
                    // edit the existing mood
                    moodHistoryTempTemp.set(modeIndex, mymood);
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


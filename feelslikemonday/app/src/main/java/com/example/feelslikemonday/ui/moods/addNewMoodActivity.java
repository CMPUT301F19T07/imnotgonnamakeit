package com.example.feelslikemonday.ui.moods;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
  //  private MoodHistory myMoodList ;
    private List<MoodType> MOOD_TYPES;
    private List<String> SOCIAL_SITUATIONS;
    // still need to declare for image and location
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String COLLECTION_NAME = "users";

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

        MOOD_TYPES = Arrays.asList(
                new MoodType("Anger","\uD83D\uDE20"),
                new MoodType("Disgust","\uD83E\uDD2E"),
                new MoodType("Fear","\uD83D\uDE31"),
                new MoodType("Happiness","☺️"),
                new MoodType("Sadness","\uD83D\uDE22"),
                new MoodType("Surprise","\uD83D\uDE32")
        );

        SOCIAL_SITUATIONS = Arrays.asList(
                "Alone",
                "With one person",
                "With two to several people",
                "With a crowd"
        );

        fillSpinners(MOOD_TYPES, SOCIAL_SITUATIONS);

        attachPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addNewMoodActivity.this, attachPhotoActivity.class);
                startActivity(intent);
            }
        });

/*
        String username = "testRehab3";
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User currentUser =   documentSnapshot.toObject(User.class);
                int x = 1;

            }
        });

        */
        // from here
/*
        String username = "rehab";
        UserDAO userDAO = new UserDAO();
        userDAO.get(username, new UserCallback() {
            @Override
            public void onCallback(User user) {
                String x = user.getUsername();
            }
        }, new VoidCallback() {
            @Override
            public void onCallback() {
            }
        });
 */

        UserDAO userDAO_t;
        userDAO_t = new UserDAO();

        userDAO_t.get("testRehab3", new UserCallback() {
            @Override
            public void onCallback(User user) {
                String myReturnValue = user.getUsername() + " " + user.getPassword();
            }
        }, new VoidCallback() {
            @Override
            public void onCallback() {
            }
        });






        /*
        DocumentReference user = db.collection(COLLECTION_NAME).document(username);
        user.get().addOnCompleteListener(new OnCompleteListener < DocumentSnapshot > () {
            @Override
            public void onComplete(@NonNull Task< DocumentSnapshot > task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    User currentUser = doc.toObject(User.class);


                    int x1 = 1;
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

        // to here



         */

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // I should read the existing data for user.

    /*
                MoodType myType;

                MoodEvent mymood;
                // prepare the object of the mood
                myType = new MoodType(MOOD_TYPES.get(moodSpiner.getSelectedItemPosition()).getName(),MOOD_TYPES.get(moodSpiner.getSelectedItemPosition()).getEmoji());
                // prepare the object for the even

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat stf = new SimpleDateFormat("hhmm");
                String dateString = sdf.format(date).toString();
                String timeString = stf.format(date).toString();
                String reasonChoice = reason.getText().toString();
                String emotionState = "No emotion";
                String sooial = SOCIAL_SITUATIONS.get(socialSpiner.getSelectedItemPosition());

                mymood = new MoodEvent(dateString,timeString, emotionState, reasonChoice,  myType, sooial);
                currentUser = new User("rehab1","rehab2","Rehab3"); // this should be replaced with GetUser Data

     */




            }
        });

    }


    public void Save(View view) {

        Log.v("button", "save pressed");
        }



    private void fillSpinners(List<MoodType> MOOD_TYPES, List<String>  SOCIAL_SITUATIONS ) {



        List<String> moodSpinner =  new ArrayList<>();

        for (int i = 0; i < MOOD_TYPES.size(); i++) {
            moodSpinner.add(MOOD_TYPES.get(i).getEmoji()+ " ( "+MOOD_TYPES.get(i).getName()+" )");
        }

        List<String> socialSpinner =  new ArrayList<>();

        for (int i = 0; i < SOCIAL_SITUATIONS.size(); i++) {
            socialSpinner.add(SOCIAL_SITUATIONS.get(i));
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


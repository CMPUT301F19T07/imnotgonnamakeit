package com.example.feelslikemonday.ui.moods;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.MoodEvent;
import com.example.feelslikemonday.model.MoodType;
import com.example.feelslikemonday.model.User;
import com.example.feelslikemonday.ui.login.SignupActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

/*Responsible for editing or adding a mood event*/

public class AddNewMoodActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private Spinner moodSpiner;
    private Spinner socialSituationSpinner;
    private Switch locationSwitch;
    private boolean saveLocation = true;
    private EditText reason;
    public User currentUser;
    private int moodState = 0; //if this variable =0 it means you're adding a new mood, it 1 you're editing the current mood
    private int moodIndex = 0;
    private String moodDate;
    private String moodTime;
    private SharedPreferences pref;
    public List<MoodEvent> moodHistory;
    private String myUserID;
    private MoodType myMoodType;
    private MoodEvent myMood;
    private static final int maxSpacesForReason = 2;
    private LocationManager locationManager;
    private String currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_mood);
        reason = findViewById(R.id.editText8);
        moodSpiner = findViewById(R.id.mood_spinner);
        socialSituationSpinner = findViewById(R.id.social_spinner);
        locationSwitch = findViewById(R.id.location_switch);

        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveLocation = isChecked;
            }
        });

        fillSpinners();
        addExistingForEdit();
        checkLocationPermission();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        setupDAO();
    }

    private void addExistingForEdit() {
        Intent intent = getIntent();
        moodState = 0;
        if (intent != null) {
            moodState = intent.getIntExtra("state", 0);
            moodIndex = 0; //this variable is for sorting
            if (moodState == 1) {
                moodTime = intent.getStringExtra("mytime"); // get current time
                moodDate = intent.getStringExtra("myDate"); // get current date
                reason.setText(intent.getStringExtra("reason")); // get  reason
                socialSituationSpinner.setSelection(intent.getIntExtra("socialSituation", 0)); //get social and set it to social spinner
                moodSpiner.setSelection(intent.getIntExtra("moodTypeName", 0)); // get mood and set it within the spinner
                //String state = intent.getStringExtra ("emotionalState");
                moodIndex = intent.getIntExtra("stateIndex", 0); // index of mood that we need to edit
            }
        }
        pref = getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY, null);
    }

    private void setupDAO() {
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
    }
    public void cancelButton(View view) {
        finish();
    }

    public void photoButton(View view) {
        Intent intent = new Intent(AddNewMoodActivity.this, AttachPhotoActivity.class);
        startActivity(intent);
    }

    public void Save(View view) {
        moodHistory = currentUser.getMoodHistory();
        myMood = getMoodDetails();
        addMoodToList(myMood);
        updateUserWithNewMood(currentUser);
    }

    public MoodEvent getMoodDetails() {

        // check that reason is max 3 words
        String reasonChoice = reason.getText().toString().trim();
        int count = reasonChoice.length() - reasonChoice.replaceAll(" ", "").length();
        if (count > maxSpacesForReason) {
            Toast toast = Toast.makeText(getApplicationContext(), "Reason cannot be over 3 words", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            reason.requestFocus();
        } else {

            // read the current user mood list

            if (moodState == 0) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat stf = new SimpleDateFormat("kk:mm");
                moodDate = sdf.format(date).toString();
                moodTime = stf.format(date).toString();
            }

            String emotionState = "No emotion"; //if we do not use this attribute, remove
            String social = MoodEvent.SOCIAL_SITUATIONS.get(socialSituationSpinner.getSelectedItemPosition());

            myMoodType = new MoodType(MoodEvent.MOOD_TYPES.get(moodSpiner.getSelectedItemPosition()).getName(), MoodEvent.MOOD_TYPES.get(moodSpiner.getSelectedItemPosition()).getEmoji());

            if (saveLocation) {
                currentLocation = getLocation();
            } else {
                currentLocation = NULL;
            }
            myMood = new MoodEvent(moodDate, moodTime, emotionState, reasonChoice, myMoodType, social, currentLocation);

            finish();
        }  // end of else
        return myMood;

    }

    public void addMoodToList(MoodEvent myMood) {
        if (moodState == 0) {
            // add new mood at the top, location 0, this way the mood list is always in reverse chronological order
            moodHistory.add(moodIndex, myMood);
        } else {
            // edit the existing mood
            moodHistory.set(moodIndex, myMood);
        }
    }

    public void updateUserWithNewMood(User currentUser) {
        // update the user object
        UserDAO userAdo = new UserDAO();
        userAdo.createOrUpdate(currentUser, new VoidCallback() {
            @Override
            public void onCallback() {
            }
        });
    }

    // List<MoodType> MOOD_TYPES, List<String>  SOCIAL_SITUATIONS
    private void fillSpinners() {

        List<String> moodSpinner = new ArrayList<>();

        for (int i = 0; i < MoodEvent.MOOD_TYPES.size(); i++) {
            moodSpinner.add(MoodEvent.MOOD_TYPES.get(i).getEmoji() + " ( " + MoodEvent.MOOD_TYPES.get(i).getName() + " )");
        }

        List<String> socialSpinner = new ArrayList<>();

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


    private String getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String Long = location.getLongitude() + "";
        String Lat = location.getLatitude() + "";

        return Long + " " + Lat;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("need lcoation")
                        .setMessage("gib location pls")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(AddNewMoodActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                    }
                } else {
                    //permissions not given
                    Toast.makeText(AddNewMoodActivity.this, "This app requires location permissions to work", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            return;
        }
    }
}




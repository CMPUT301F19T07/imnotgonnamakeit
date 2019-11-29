package com.example.feelslikemonday.ui.moods;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
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
import com.google.firebase.firestore.Blob;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

/**
 * This class is responsible for editing or adding a mood event
 */

public class AddNewMoodActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private Spinner moodSpiner;
    private Spinner socialSituationSpinner;
    private Switch locationSwitch;
    private boolean saveLocation = true;
    private EditText reason;
    private ImageView imageView;

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
    private TextView count;
    private byte[] moodBitmapByteArray;


    /**
     * This initializes AddNewMoodActivity
     *
     * @param savedInstanceState This is a previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_mood);
        imageView = findViewById(R.id.myphoto);
        reason = findViewById(R.id.editText8);
        moodSpiner = findViewById(R.id.mood_spinner);
        socialSituationSpinner = findViewById(R.id.social_spinner);
        locationSwitch = findViewById(R.id.location_switch);
        count = findViewById(R.id.count);

        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveLocation = isChecked;
            }
        });

        fillSpinners();
        addExistingForEdit();

        // checkLocationPermission();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        setupDAO();

        String s = reason.getText().toString();
        int num = s.length();
        count.setText("" + num);

        /**
         *This function of commentEditText is to tracking and updating the number of characters
         * in the commentEditText box. A small number will update at the right bottom of the box.
         */
        //https://www.youtube.com/watch?v=sk3GWcbgijI
        reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = reason.getText().toString();
                int num = s.length();
                count.setText("" + num);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * This edits the existing mood event.
     * It is accessed by a user going to the home page mood history and swiping on a mood event and clicking on "Edit"
     * This will take a user from the MainActivity and Home Fragment to the AddNewMoodActivity
     * The user can then click cancel or save  to go back to the home page
     */
    private void addExistingForEdit() {
        Intent intent = getIntent();
        moodState = 0;
        if (intent != null) {
            moodState = intent.getIntExtra("state", 0);
            moodIndex = 0; //this variable is for sorting
            if (moodState == 1) {
                byte[] imageByteArr = intent.getByteArrayExtra("image");
                if (imageByteArr != null) {
                    moodBitmapByteArray = imageByteArr;
                    Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArr, 0, imageByteArr.length);
                    imageView.setImageBitmap(imageBitmap);
                }
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

    /**
     * This sets up UserDAO
     * used to connect with the current user logged in
     */
    private void setupDAO() {
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

    /**
     * This finishes AddNewMoodActivity when user presses cancel
     * Takes you back to the home page
     */
    public void cancelButton(View view) {
        finish();
    }

    /**
     * This goes to AttachPhotoActivity when user click photoButton
     */
    public void photoButton(View view) {
        Intent intent = new Intent(AddNewMoodActivity.this, AttachPhotoActivity.class);
        startActivityForResult(intent, AttachPhotoActivity.REQUEST_CODE);
    }

    /**
     * Method currently used to get photos from the photo activity
     *
     * @param requestCode This is the integer request code originally supplied to startActivityForResult()
     * @param resultCode  This is the integer result code returned by the child activity through its setResult()
     * @param data        This is the result data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(resultCode, resultCode, data);
        if (requestCode == AttachPhotoActivity.REQUEST_CODE && resultCode == AttachPhotoActivity.RES_OK && data != null) {
            //get binary from activity result and set the image view to it.
            byte[] img = data.getByteArrayExtra(AttachPhotoActivity.BITMAP_BYTE_ARRAY_EXTRA);
            moodBitmapByteArray = img;
            byteArrayToBitmap(img);
        }
    }

    public Bitmap byteArrayToBitmap(byte[] input) {
        Bitmap bmp = BitmapFactory.decodeByteArray(input, 0, input.length);
        ImageView imageView = findViewById(R.id.myphoto);
        imageView.setImageBitmap(bmp);
        return bmp;
    }

    /**
     * This saves the current mood event when a user wants to add a mood or edit a mood
     * This will ensure that it is saved in firebase
     */
    public void Save(View view) {
        if (saveLocation) {

            if (checkLocationPermission()) {
                moodHistory = currentUser.getMoodHistory();
                myMood = getMoodDetails();
                addMoodToList(myMood);
                updateUserWithNewMood(currentUser);
            }
        } else {
            moodHistory = currentUser.getMoodHistory();
            myMood = getMoodDetails();
            addMoodToList(myMood);
            updateUserWithNewMood(currentUser);
        }
    }

    /**
     * This gets the details of the current mood event
     * This includes the mood, reason, social state, location
     * For the reason, this method also tests that the reaosn is either maximum 20 characters long and is maximum 3 words long
     * @return return the mood event
     */
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
            if (moodState == 0) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat stf = new SimpleDateFormat("kk:mm");
                moodDate = sdf.format(date);
                moodTime = stf.format(date);
            }

            String emotionState = "No emotion"; //if we do not use this attribute, remove
            String social = MoodEvent.SOCIAL_SITUATIONS.get(socialSituationSpinner.getSelectedItemPosition());

            myMoodType = new MoodType(MoodEvent.MOOD_TYPES.get(moodSpiner.getSelectedItemPosition()).getName(), MoodEvent.MOOD_TYPES.get(moodSpiner.getSelectedItemPosition()).getEmoji());
            Blob moodBlob = null;
            if (moodBitmapByteArray != null) {
                moodBlob = Blob.fromBytes(moodBitmapByteArray);
            }
            if (saveLocation) {
                currentLocation = getLocation();
            } else {
                currentLocation = NULL;
            }
            myMood = new MoodEvent(moodDate, moodTime, emotionState, reasonChoice, myMoodType, social, currentLocation, moodBlob);

            finish();
        }  // end of else
        return myMood;

    }

    /**
     * This adds mood event to the moodHistory
     *
     * @param myMood This is the current mood event
     */
    public void addMoodToList(MoodEvent myMood) {
        if (moodState == 0) {
            // add new mood at the top, location 0, this way the mood list is always in reverse chronological order
            moodHistory.add(moodIndex, myMood);
        } else {
            // edit the existing mood
            moodHistory.set(moodIndex, myMood);
        }
    }

    /**
     * This adds mood event to the moodHistory
     *
     * @param currentUser This is the current user
     */
    public void updateUserWithNewMood(User currentUser) {
        // update the user object
        UserDAO userAdo = new UserDAO();
        userAdo.createOrUpdate(currentUser, new VoidCallback() {
            @Override
            public void onCallback() {
            }
        });
    }


    /**
     * This is the drop down we use for adding and editing moods in your mood history in the home page
     * The two spinners/drop downs are for mood types and social situations
     * The user can choose any option displayed
     */
    private void fillSpinners() {

        List<String> moodSpinner = new ArrayList<>();

        for (int i = 0; i < MoodEvent.MOOD_TYPES.size(); i++) {
            moodSpinner.add(MoodEvent.MOOD_TYPES.get(i).getEmoji() + " " + MoodEvent.MOOD_TYPES.get(i).getName());
        }

        List<String> socialSpinner = new ArrayList<>();

        for (int i = 0; i < MoodEvent.SOCIAL_SITUATIONS.size(); i++) {
            socialSpinner.add(MoodEvent.SOCIAL_SITUATIONS.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.my_spinner, moodSpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner moodSpinnerSpinner = findViewById(R.id.mood_spinner);
        moodSpinnerSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                this, R.layout.my_spinner, socialSpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner socialSpinnerSpinner = findViewById(R.id.social_spinner);
        socialSpinnerSpinner.setAdapter(adapter1);
    }

    /**
     * This receives notifications from the LocationManager when the location has changed
     *
     */
    private LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // Previously mock location is cleared.
            // getLastKnownLocation(LocationManager.GPS_PROVIDER); will not return mock location.
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    /**
     * This gets the location of the current mood event
     *
     * @return return the string consisting of longitude and latitude
     */
    @SuppressWarnings({"MissingPermission"})
    private String getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location;

        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 2000, 10, mListener);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        String Long = location.getLongitude() + "";
        String Lat = location.getLatitude() + "";
        return Long + " " + Lat;

    }

    /**
     * This checks if the user permits location
     * When the app is first installed, the user will a popup message asking  if the app can use their location
     * User can allow this or not allow their location to be saved
     * @return return boolean result
     */
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
                        .setTitle("Location permissions needed")
                        .setMessage("This app requires location permissions to save your current location.")
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
            if (isLocationEnabled(this)) {
                return true;
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Location Services are currently disabled")
                        .setMessage("This app requires Location Services to be enabled to save your current location.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                            }
                        })
                        .create()
                        .show();
            }
            return false;
        }
    }

    /**
     * This callbacks for the result from requesting permissions.
     *
     * @param requestCode  This is the request code passed in.
     * @param permissions  This is the requested permissions
     * @param grantResults This is the grant results for the corresponding permissions
     */
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
                    Toast.makeText(AddNewMoodActivity.this, "Location permissions required to save mood location", Toast.LENGTH_LONG).show();
                }
            };
        }
    }

    /**
     * This checks if Location Services are enabled
     *
     * @return return boolean result
     */
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}




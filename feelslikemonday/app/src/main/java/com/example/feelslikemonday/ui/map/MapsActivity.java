package com.example.feelslikemonday.ui.map;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.MoodEvent;
import com.example.feelslikemonday.model.User;
import com.example.feelslikemonday.ui.login.SignupActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

/**
 * This class is responsible for creating user's moods map. It takes the list of moods for the logged in user and puts pins according to the
 * location and mood. Open this activity from the action settings menu.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private User currentUser;
    private List<MoodEvent> myEmotionList = new ArrayList<>();
    private List<MoodEvent> myCurrentMoodList;
    private LatLng currentLocation;
    private String moodIcon;

    /**
     * Creates the map activity based on the google maps api. Map fragment is created and inflated here.
     * Does the logic on waiting on map to be ready before continuing.
     * @param savedInstanceState This is a previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    /**
     * This uses UserDAO to get a list of the user's mood events, then call placeMarkers()
     * to place makers at the google map when the google map is ready.
     * @param googleMap This is a google map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        SharedPreferences pref;
        String myUserID;
        mMap = googleMap;
        pref = getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY, null);

        UserDAO userDAO = new UserDAO();
        userDAO.get(myUserID, new UserCallback() {
            @Override
            public void onCallback(User user) {
                currentUser = user;
                myEmotionList.clear();
                myCurrentMoodList = currentUser.getMoodHistory();
                myEmotionList.addAll(myCurrentMoodList);
                placeMarkers();
            }
        }, new VoidCallback() {
            @Override
            public void onCallback() {
            }
        });
    }

    /**
     * This gets all the mood events' locations and mood types by using MoodEvent's methods
     * and places markers corresponding to mood events at the google map
     */
    private void placeMarkers() {
        for (int i = 0; i < myEmotionList.size(); i++) {
            String markerLocation = myEmotionList.get(i).getLocation();
            if (markerLocation != null) {
                switch (myEmotionList.get(i).getMoodType().getName()) {
                    case "Anger":
                        moodIcon = "anger.png";
                        break;
                    case "Disgust":
                        moodIcon = "disgust.png";
                        break;
                    case "Fear":
                        moodIcon = "fear.png";
                        break;
                    case "Happiness":
                        moodIcon = "happiness.png";
                        break;
                    case "Sadness":
                        moodIcon = "sadness.png";
                        break;
                    case "Surprise":
                        moodIcon = "surprise.png";
                        break;
                }
                if (markerLocation.equals(NULL)) {
                    currentLocation = null;
                    continue;
                }
                String[] latLongSplit = markerLocation.split(" ");
                currentLocation = new LatLng(Double.valueOf(latLongSplit[1]), Double.valueOf(latLongSplit[0]));
                mMap.addMarker(new MarkerOptions().position(currentLocation).icon(BitmapDescriptorFactory.
                        fromAsset(moodIcon)).title(myEmotionList.get(i).getMoodType().getName()).snippet(myEmotionList.get(i).getDate()));
            }
        }
        if (currentLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        }
    }

}

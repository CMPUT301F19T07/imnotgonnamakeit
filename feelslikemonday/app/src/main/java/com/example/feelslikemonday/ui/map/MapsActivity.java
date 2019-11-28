package com.example.feelslikemonday.ui.map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

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
 * This class is responsible for creating user's moods map
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private User currentUser;
    private ArrayList<MoodEvent> myEmotionList = new ArrayList<>();
    private List<MoodEvent> myCurrentMoodList;
    private LatLng currentLocation;
    private String moodIcon;

    /**
     * This initializes MapActivity
     * @param savedInstanceState
     * This is a previous saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    /*
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    /**
     * This places users' mood event markers on googleMap
     * @param googleMap
     * This is a google map
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
     * This places different emoji icons according to the mood event
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

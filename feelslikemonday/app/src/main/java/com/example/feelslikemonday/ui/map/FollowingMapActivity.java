package com.example.feelslikemonday.ui.map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.feelslikemonday.DAO.FollowPermissionCallback;
import com.example.feelslikemonday.DAO.FollowPermissionDAO;
import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.FollowPermission;
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


/**
 * This class is responsible to show the user's followees' recent mood map
 */
public class FollowingMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private User currentUser;
    private List<MoodEvent> followeeCurrentMoodList = new ArrayList<>();
    private String markerLocation;
    private FollowPermission currentFolloPermission;
    private List<String> followeeList = new ArrayList<>();
    private int i;
    private String followeeName;
    private MoodEvent recentMoodEvent;
    private String moodIcon;

    /**
     * This initializes FollowingMapActivity
     * @param savedInstanceState
     * This is a saved instance state
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    /**
     * This calls when map is ready to be used
     * @param googleMap
     * This is a non-null instance of a GoogleMap associated with the MapFragment or MapView that defines the callback.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        SharedPreferences pref;
        String myUserID;
        mMap = googleMap;
        pref = getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY, null);
        FollowPermissionDAO followPermissionDAO = new FollowPermissionDAO();
        followPermissionDAO.get(myUserID, new FollowPermissionCallback() {
            @Override
            public void onCallback(FollowPermission followPermission) {
                currentFolloPermission = followPermission;
                followeeList = currentFolloPermission.getFolloweeUsernames();
                if (followeeList.size() > 0) {
                    UserDAO userDAO = new UserDAO();
                    for (i = 0; i < followeeList.size(); i++)
                        userDAO.get(followeeList.get(i), new UserCallback() {
                            @Override
                            public void onCallback(User user) {
                                currentUser = user;
                                followeeCurrentMoodList = currentUser.getMoodHistory();
                                followeeName = currentUser.getUsername();
                                if (followeeCurrentMoodList.size() > 0) {
                                    recentMoodEvent = followeeCurrentMoodList.get(0);
                                    markerLocation = recentMoodEvent.getLocation();
                                    if (markerLocation != null) {
                                        placeMarkers();
                                    }
                                }
                            }
                        }, new VoidCallback() {
                            @Override
                            public void onCallback() {
                            }
                        });
                }
            }

        }, new VoidCallback() {
            @Override
            public void onCallback() {
            }
        });

    }

    /**
     * This places the markers on google map according to the mood event
     */
    private void placeMarkers() {
        LatLng currentLocation;
        markerLocation = recentMoodEvent.getLocation();
        switch (recentMoodEvent.getMoodType().getName()) {
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
        String[] latLongSplit = markerLocation.split(" ");
        currentLocation = new LatLng(Double.valueOf(latLongSplit[1]), Double.valueOf(latLongSplit[0]));
        mMap.addMarker(new MarkerOptions().position(currentLocation).icon(BitmapDescriptorFactory.fromAsset(moodIcon)).title(followeeName).snippet(recentMoodEvent.getMoodType().getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }

}
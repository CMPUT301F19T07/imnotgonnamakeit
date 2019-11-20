package com.example.feelslikemonday.ui.map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.feelslikemonday.DAO.UserCallback;
import com.example.feelslikemonday.DAO.UserDAO;
import com.example.feelslikemonday.DAO.VoidCallback;
import com.example.feelslikemonday.R;
import com.example.feelslikemonday.model.FolloweeMoodEvent;
import com.example.feelslikemonday.model.MoodEvent;
import com.example.feelslikemonday.model.User;
import com.example.feelslikemonday.ui.login.SignupActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class FollowingMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SharedPreferences pref;
    private String myUserID;
    private User currentUser;
    private FolloweeMoodEvent followeeMoodEvent;
    private ArrayList<MoodEvent> followingEmotionList = new ArrayList<>();
    private List<MoodEvent> myCurrentMoodList;
    private LatLng currentLocation;
    private BitmapDescriptor markerType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        pref = getApplicationContext().getSharedPreferences(SignupActivity.PREFS_NAME, 0);
        myUserID = pref.getString(SignupActivity.USERNAME_KEY, null);

        UserDAO userDAO = new UserDAO();
        userDAO.get(myUserID, new UserCallback() {
            @Override
            public void onCallback(User user) {
                currentUser = user;
                followingEmotionList.clear();
                for (int i = 0; i < myCurrentMoodList.size(); i++) {
                    followingEmotionList.add(myCurrentMoodList.get(i));
                }
                placeMarkers();
            }
        }, new VoidCallback() {
            @Override
            public void onCallback() {
                Log.v("succc", "succ");
            }
        });
    }

    private void placeMarkers() {
        for (int i = 0; i < followingEmotionList.size(); i++) {

            String markerLocation = followingEmotionList.get(i).getLocation();
            switch (followingEmotionList.get(i).getMoodType().getName()) {
                case "Anger":
                    markerType = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                    break;
                case "Disgust":
                    markerType = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    break;
                case "Fear":
                    markerType = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                    break;
                case "Happiness":
                    markerType = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
                    break;
                case "Sadness":
                    markerType = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                    break;
                case "Surprise":
                    markerType = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                    break;
            }
            if (markerLocation.equals(NULL)) {
                currentLocation = null;
                continue;
            }
            String[] latLongSplit = markerLocation.split(" ");
            currentLocation = new LatLng(Double.valueOf(latLongSplit[1]), Double.valueOf(latLongSplit[0]));
            mMap.addMarker(new MarkerOptions().position(currentLocation).icon(markerType));
        }
        if (currentLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        }
    }

}